package vStrikerTestEngine;

import java.util.List;

import vStrikerEntities.Account;
import vStrikerEntities.Api;
import vStrikerEntities.ApiSelected;
import vStrikerEntities.ConfigurationTemplate;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.ExecutionReport;
import vStrikerEntities.TestConfiguration;
import vStrikerTestUtilities.vLogger;

//@author Sanjeev Chauhan

public class VEngine implements Engine {

	@Override
	public ExecutionReport runSwiftTests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api) throws Exception {
		vLogger.LogInfo("In vTestEngine runSwiftTests");
		return (new SwiftTestClient()).runTests(ep, testconfig, api);
	}

	@Override
	public ExecutionReport runS3Tests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api) throws Exception {
		vLogger.LogInfo("In vTestEngine runS3Tests");
		return (new S3TestClient()).runTests(ep, testconfig, api);
	}

	@Override
	public ExecutionReport runAtmosTests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api) throws Exception {
		vLogger.LogInfo("In vTestEngine runAtmosTests");
		return  (new AtmosTestClient()).runTests(ep, testconfig, api);
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
		ExecutionReport rpt = new ExecutionReport();
		try {
			ConfigurationTemplate cfgtemp = plan.getConfigurationTemplate();
			TestConfiguration test = plan.getTestConfiguration();
			Account acct = plan.getAccount();

			List<Api> apilist = acct.getApis();
			List<ApiSelected> select;
			if (cfgtemp != null) {
				select = cfgtemp.getApiSelecteds();
				// load Cfg as test so we can pass on type of entity to engine

				test.setTestConfigName(cfgtemp.getConfTempName());
				test.setTestConfigDescription(cfgtemp.getConfTempDescription());
				test.setObjectSizeReportUnit(cfgtemp.getObjectSizeReportUnit1());
				test.setNumberOfOperations(cfgtemp
						.getConfTempNumberOfOperations());
				test.setNumberOfThreads(cfgtemp.getConfTempNumberOfThreads());

				test.setNumberOfRetry(cfgtemp.getConfTempNumberOfRetry());
				test.setObjectSize(cfgtemp.getConfTempObjectSize());

				test.setCreateOperation(cfgtemp.getConfTempCreateOperation());
				test.setDeleteOperation(cfgtemp.getConfTempDeleteOperation());
				test.setUpdateOperation(cfgtemp.getConfTempUpdateOperation());
				test.setReadOperation(cfgtemp.getConfTempReadOperation());

				test.setCreatePercent(cfgtemp.getConfTempCreatePercent());
				test.setUpdatePercent(cfgtemp.getConfTempUpdatePercent());
				test.setDeletePercent(cfgtemp.getConfTempDeletePercent());
				test.setDeletePercent(cfgtemp.getConfTempDeletePercent());

			} else {
				select = test.getApiSelecteds();
			}
			System.out.println("RunTests: Api selected are " + select.size());

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
							rpt = runS3Tests(plan, test, p);


							break;
						}
						case 2: {
							System.out.println("Validated Swift");
							rpt = runSwiftTests(plan, test, p);
							break;
						}
						case 3: {
							System.out.println("Validated Atmos");
							rpt = runAtmosTests(plan, test, p);
							break;
						}
						default:
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rpt;
	}

}