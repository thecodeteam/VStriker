package vStrikerTestEngine;

import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import vStrikerEntities.Account;
import vStrikerEntities.Api;
import vStrikerEntities.ApiSelected;
import vStrikerEntities.ConfigurationTemplate;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.ExecutionReport;
import vStrikerEntities.TestConfiguration;
import vStrikerTestUtilities.*;
import vStrikerBizModel.*;
//@author Sanjeev Chauhan

public class VEngine implements Engine {

	@Override
	public vStrikerTestUtilities.TestResult runSwiftTests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api,ExecutionReport report) throws Exception {
		vLogger.LogInfo("In vTestEngine runSwiftTests");
		return (new SwiftTestClient()).runTests(ep, testconfig, api,report);
	}

	@Override
	public vStrikerTestUtilities.TestResult runS3Tests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api,ExecutionReport report) throws Exception {
		vLogger.LogInfo("In vTestEngine runS3Tests");
		return (new S3TestClient()).runTests(ep, testconfig, api,report);
	}

	@Override
	public vStrikerTestUtilities.TestResult runAtmosTests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api,ExecutionReport report) throws Exception {
		vLogger.LogInfo("In vTestEngine runAtmosTests");
		return  (new AtmosTestClient()).runTests(ep, testconfig, api,report);
	}

	@Override
	public boolean validateSwiftConnection(String user, String key, String url,
			String namespace) {
		if (user == null || key == null || url == null) {
			System.out.println("Please ensure user, key and url are valid");
			return false;
		}
		System.out.println("user, key and url are:" + user + " " + key + " "
				+ url);
		if (namespace == null || namespace.length() == 0) {
			namespace = null;
		}
		return new SwiftTestClient().validateConnnection(user, key, url,
				namespace);
	}

	@Override
	public boolean validateS3Connection(String user, String key, String url,
			String namespace) {
		System.out.println("In vTestEngine validateS3Connnection");
		if (user == null || key == null || url == null) {
			System.out.println("Please ensure user, key and url are valid");
			return false;
		}
		System.out.println("user, key and url are:" + user + " " + key + " "
				+ url);
		if (namespace == null || namespace.length() == 0) {
			namespace = null;
		}
		return new S3TestClient().validateConnection(user, key, url, namespace);
	}

	@Override
	public boolean validateAtmosConnection(String user, String key, String url,
			String namespace) {
		System.out.println("In vTestEngine validateAtmosConnnection");
		if (user == null || key == null || url == null) {
			System.out.println("Please ensure user, key and url are valid");
			return false;
		}
		System.out.println("user, key and url are:" + user + " " + key + " "
				+ url);
		if (namespace == null || namespace.length() == 0) {
			namespace = null;
		}
		return new AtmosTestClient().validateConnnection(user, key, url,
				namespace);
	}

	@Override
	public ExecutionReport runTests(ExecutionPlan plan) throws Exception {

		ExecutionReport report = new ExecutionReport();
		report.setExecutionDate(new Date());
		report.setExecutionName(plan.getAccount().getName());
		report.setExecutionPlan(plan);
		ExecutionReportBiz.ExecutionReportCreate(report);

		try {
			ConfigurationTemplate cfgtemp = plan.getConfigurationTemplate();
			TestConfiguration testconfig = plan.getTestConfiguration();
			Account acct = plan.getAccount();

			List<Api> apilist = acct.getApis();
			List<ApiSelected> select;
			if (cfgtemp != null) {
				select = cfgtemp.getApiSelecteds();
				// load Cfg as test so we can pass on type of entity to engine

				testconfig.setTestConfigName(cfgtemp.getConfTempName());
				testconfig.setTestConfigDescription(cfgtemp.getConfTempDescription());
				testconfig.setObjectSizeReportUnit(cfgtemp.getObjectSizeReportUnit1());
				testconfig.setNumberOfOperations(cfgtemp
						.getConfTempNumberOfOperations());
				testconfig.setNumberOfThreads(cfgtemp.getConfTempNumberOfThreads());

				testconfig.setNumberOfRetry(cfgtemp.getConfTempNumberOfRetry());
				testconfig.setObjectSize(cfgtemp.getConfTempObjectSize());

				testconfig.setCreateOperation(cfgtemp.getConfTempCreateOperation());
				testconfig.setDeleteOperation(cfgtemp.getConfTempDeleteOperation());
				testconfig.setUpdateOperation(cfgtemp.getConfTempUpdateOperation());
				testconfig.setReadOperation(cfgtemp.getConfTempReadOperation());

				testconfig.setCreatePercent(cfgtemp.getConfTempCreatePercent());
				testconfig.setUpdatePercent(cfgtemp.getConfTempUpdatePercent());
				testconfig.setDeletePercent(cfgtemp.getConfTempDeletePercent());
				testconfig.setDeletePercent(cfgtemp.getConfTempDeletePercent());

			} else {
				select = testconfig.getApiSelecteds();
			}
			System.out.println("RunTests: Api selected are " + select.size());
			int createOps = 0, readOps = 0, updateOps = 0, deleteOps = 0;
			long createTime=0, deleteTime=0, updateTime=0, readTime=0,maxValue=0,minValue=0,totalTime=0;
			TestResult s3TestResult=new TestResult();
			TestResult swiftTestResult=new TestResult();
			TestResult atmosTestResult=new TestResult();

			for (Api p : apilist) {
				for (ApiSelected s : select) {
					if (p.getApiType().getApiTypeId() == s.getApiType()
							.getApiTypeId()) {
						int x=0;
						if((p.getApiType().getApiTypeName()).equals("S3")) x=1;
						if((p.getApiType().getApiTypeName()).equals("Swift")) x=2;
						if((p.getApiType().getApiTypeName()).equals("Atmos")) x=3;

						switch (x) {
						case 1: {
							System.out.println("Run S3");
							s3TestResult = runS3Tests(plan, testconfig, p,report);


							break;
						}
						case 2: {
							System.out.println("Run Swift");
							swiftTestResult = runSwiftTests(plan, testconfig, p,report);
							break;
						}
						case 3: {
							System.out.println("Run Atmos");
							atmosTestResult = runAtmosTests(plan, testconfig, p,report);
							break;
						}
						default:
							break;
						}
					}
				}
			}

			System.out.println("Update Report Summary");
			// Update Report Summary
			// Divide the total number of operations amongst CRUD operations


			if (testconfig.getCreateOperation()) {
				createOps = (testconfig.getNumberOfOperations() * testconfig
						.getCreatePercent()) / 100;
			}
			if (testconfig.getReadOperation()) {
				readOps = (testconfig.getNumberOfOperations() * testconfig
						.getReadPercent()) / 100;
			}
			if (testconfig.getUpdateOperation()) {
				updateOps = (testconfig.getNumberOfOperations() * testconfig
						.getUpdatePercent()) / 100;
			}
			if (testconfig.getDeleteOperation()) {
				deleteOps = (testconfig.getNumberOfOperations() * testconfig
						.getDeletePercent()) / 100;
			}
			// Total volume sent = (createops + updateops) * sizeofobject
			if (testconfig.getCreateOperation() || testconfig.getUpdateOperation()) {
				report.setTotalVolumeSent(Long
						.toString(
								(createOps + updateOps)
										* (long) testconfig.getObjectSize())
						.toString());
			} else
				report.setTotalVolumeSent("0");

			// Total volume received = (readops) * sizeofobject
			if (testconfig.getReadOperation()) {
				report.setTotalVolumeReceived(Long.toString(readOps
						* (long) testconfig.getObjectSize()));
			} else
				report.setTotalVolumeReceived("0");

			createTime=s3TestResult.getCreateTime()+swiftTestResult.getCreateTime()+atmosTestResult.getCreateTime();
			updateTime=s3TestResult.getUpdateTime()+swiftTestResult.getUpdateTime()+atmosTestResult.getUpdateTime();
			readTime=s3TestResult.getReadTime()+swiftTestResult.getReadTime()+atmosTestResult.getReadTime();
			deleteTime=s3TestResult.getDeleteTime()+swiftTestResult.getDeleteTime()+atmosTestResult.getDeleteTime();
			totalTime=s3TestResult.getTotalTime()+swiftTestResult.getTotalTime()+atmosTestResult.getTotalTime();

			maxValue=s3TestResult.getMax();
			if(swiftTestResult.getMax()>maxValue)maxValue=swiftTestResult.getMax();
			if(atmosTestResult.getMax()>maxValue)maxValue=atmosTestResult.getMax();

			minValue=s3TestResult.getMin();
			if(minValue==0)
				minValue=swiftTestResult.getMin();
			else
			if(swiftTestResult.getMin()<minValue & swiftTestResult.getMin()>0)minValue=swiftTestResult.getMin();

			if(minValue==0)
				minValue=atmosTestResult.getMin();
			else
			if(atmosTestResult.getMin()<minValue & atmosTestResult.getMin()>0)minValue=atmosTestResult.getMin();


			System.out.println("s3maxValue:"+ s3TestResult.getMax());
			System.out.println("s3minValue:"+ s3TestResult.getMin());
			System.out.println("swiftmaxValue:"+ swiftTestResult.getMax());
			System.out.println("swiftminValue:"+ swiftTestResult.getMin());

			System.out.println("maxValue:"+ maxValue);
			System.out.println("minValue:"+ minValue);

			report.setAvgLatencyPerCrudOperation(Long.toString(((createTime
					+ readTime + updateTime + deleteTime) / 1000000)
					/ testconfig.getNumberOfOperations()));
			report.setNumberRequestSec((int) (testconfig.getNumberOfOperations()
					/ (createTime + readTime + updateTime + deleteTime) / 1000000000));

				report.setMaxThroughput(((long) testconfig.getObjectSize() * 1000)
						/ minValue + " bytes per second");
				report.setMinThroughput(((long) testconfig.getObjectSize() * 1000)
						/ maxValue + " bytes per second");
			report.setTotalThroughput((((createOps + updateOps) * (long) testconfig.getObjectSize()) * 1000)
					/ totalTime + " bytes per second") ;


			String name= "Account:"+plan.getAccount().getName() +"- Test:"+testconfig.getTestConfigName();
			report.setExecutionName(name);
			vStrikerBizModel.ExecutionReportBiz.ExecutionReportUpdate(report);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return report;
	}

}