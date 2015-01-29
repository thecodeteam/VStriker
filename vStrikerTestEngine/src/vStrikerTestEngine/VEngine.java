package vStrikerTestEngine;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import vStrikerBizModel.ExecutionReportDataBiz;
import vStrikerEntities.Account;
import vStrikerEntities.Api;
import vStrikerEntities.ApiSelected;
import vStrikerEntities.ConfigurationTemplate;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.ExecutionReport;
import vStrikerEntities.ExecutionReportData;
import vStrikerEntities.TestConfiguration;
import vStrikerTestUtilities.Utilites;
import vStrikerTestUtilities.vLogger;

import com.emc.vipr.s3.s3api;

//@author Sanjeev Chauhan

public class VEngine implements Engine {

	public boolean validateS3Connection(String user, String key, String url,
			String namespace) {
		vLogger.LogInfo("In vTestEngine validateS3Connection");
		if (user == null || key == null || url == null) {
			System.out.println("Please ensure user, key and url are valid");
			return false;
		}
		if (namespace == null || namespace.length() == 0) {
			namespace = null;
		}
		vLogger.LogInfo("In vTestEngine validateS3Connection");
		String TEST_BUCKET = "vstrikertest" + UUID.randomUUID().toString();
		System.out.println("Test Bucket name is: " + TEST_BUCKET);
		try {
			s3api.CreateBucket(user, key, url, namespace, TEST_BUCKET);

			List<String> listofObjects = Utilites.generateTestFileList(
					"validationTest", 1000, 1);

			s3api.CreateObject(user, key, url, namespace, TEST_BUCKET,
					listofObjects.get(0),
					new FileInputStream(listofObjects.get(0)));
			s3api.DeleteObject(user, key, url, namespace, TEST_BUCKET,
					listofObjects.get(0));
			s3api.DeleteBuckets(user, key, url, namespace, TEST_BUCKET);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Validation failed: " + e.toString());
			return false;
		}
		System.out.println("Validation successfully");
		return true;
	}

	public ExecutionReport runS3Tests(TestConfiguration testconfig, Api api,ExecutionPlan plan)
			throws Exception {
		vLogger.LogInfo("In vTestEngine runS3Tests");

		// validate arguments
		if (testconfig.getCreateOperation() == false
				&& testconfig.getReadOperation() == false
				&& testconfig.getUpdateOperation() == false
				&& testconfig.getDeleteOperation() == false) {
			throw new Exception("No operations to perform");
		}
		if (api.getSubtenant() == null || api.getSubtenant().length() == 0
				|| api.getSecretKey() == null
				|| api.getSecretKey().length() == 0 || api.getUrl() == null
				|| api.getUrl().length() == 0) {
			throw new Exception("Missing api information");
		}

		// ToDo - From TestConfiguration - get ExecutionPlan and
		// ExecutionReportId
		// Use ExecutionReportId to get the ExecutionReport object
		ExecutionReport report = new ExecutionReport();
		report.setExecutionReportId(1);

		// Divide the total number of operations amongst CRUD operations
		int createOps = 0, readOps = 0, updateOps = 0, deleteOps = 0;
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

		System.out.println("TotalOps: " + testconfig.getNumberOfOperations());
		System.out.println("createOps: " + createOps);
		System.out.println("readOps: " + readOps);
		System.out.println("updateOps: " + updateOps);
		System.out.println("deleteOps: " + deleteOps);

		// Create objects
		List<String> listofObjects = Utilites.generateFiles(
				testconfig.getTestConfigName(), testconfig.getObjectSize(),
				testconfig.getNumberOfOperations());
		// Create create workers
		List<Callable<ExecutionReportData>> createworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of S3CreateWorkers
		for (int i = 0; i < createOps; i++) {
			Callable<ExecutionReportData> s3createworker = new S3CreateWorker(
					listofObjects.get(i), api);
			createworkersList.add(s3createworker);
		}
		// Create read workers
		List<Callable<ExecutionReportData>> readworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of S3ReadWorkers
		for (int i = 0; i < readOps; i++) {
			Callable<ExecutionReportData> s3readworker = new S3ReadWorker(
					listofObjects.get(i), api);
			readworkersList.add(s3readworker);
		}
		// Create update workers
		List<Callable<ExecutionReportData>> updateworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of S3UpdateWorkers
		for (int i = 0; i < updateOps; i++) {
			Callable<ExecutionReportData> s3updateworker = new S3UpdateWorker(
					listofObjects.get(i), api);
			updateworkersList.add(s3updateworker);
		}
		// Create delete workers
		List<Callable<ExecutionReportData>> deleteworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of S3DeleteWorkers
		for (int i = 0; i < deleteOps; i++) {
			Callable<ExecutionReportData> s3deleteworker = new S3DeleteWorker(
					listofObjects.get(i), api);
			deleteworkersList.add(s3deleteworker);
		}

		// Pre-test
		/*
		 * if (!testconfig.getCreateOperation() || (deleteOps > createOps)) {
		 * for (String s : listofObjects) {
		 * s3api.CreateObject(api.getSubtenant(), api.getSecretKey(),
		 * api.getUrl(), null, api.getBucket(), FilenameUtils.getName(s), new
		 * FileInputStream(s)); System.out.println("Pre-test - Created: " + s);
		 * } }
		 */

		if (!testconfig.getCreateOperation() || (deleteOps > createOps) || (updateOps > createOps) || (readOps > createOps)) {
			long preteststarttime = System.nanoTime();
			int mostOps = Math.max(Math.max(deleteOps, updateOps), readOps);
			int pretestcreates = mostOps - createOps;
			ExecutorService testprepexecutor = Executors.newWorkStealingPool();
			List<Callable<ExecutionReportData>> pretestList = new ArrayList<Callable<ExecutionReportData>>();

			for (int i = createOps; i < mostOps; i++) {
				Callable<ExecutionReportData> s3createworker = new S3CreateWorker(
						listofObjects.get(i), api);
				pretestList.add(s3createworker);
			}
			testprepexecutor.invokeAll(pretestList);
			testprepexecutor.shutdown();
			try {
				testprepexecutor.awaitTermination(Long.MAX_VALUE,
						TimeUnit.SECONDS);
			} catch (Exception e) {
				System.out.println("PreTest tasks did not finish in time: "
						+ e.toString());
			}
			System.out.println("PreTest Created " + pretestcreates
					+ " objects in "
					+ ((System.nanoTime() - preteststarttime) / 1000000)
					+ " ms");
		}

		// The test
		long sum = 0;
		ExecutorService executor = Executors.newFixedThreadPool(testconfig
				.getNumberOfThreads());
		List<ExecutionReportData> list = new ArrayList<ExecutionReportData>();
		List<ExecutionReportData> failurelist = new ArrayList<ExecutionReportData>();
		List<Future<ExecutionReportData>> futurelist = new ArrayList<Future<ExecutionReportData>>();

		long createTime = System.nanoTime();
		futurelist = executor.invokeAll(createworkersList);
		createTime = System.nanoTime() - createTime;
		for (Future<ExecutionReportData> f : futurelist) {
			try {
				list.add(f.get());
			} catch (Exception e) {
				ExecutionReportData erd = new ExecutionReportData();
				erd.setDataKey("create");
				erd.setDataValue(e.toString());
				failurelist.add(erd);
			}
		}

		long readTime = System.nanoTime();
		futurelist = executor.invokeAll(readworkersList);
		readTime = System.nanoTime() - readTime;
		for (Future<ExecutionReportData> f : futurelist) {
			try {
				list.add(f.get());
			} catch (Exception e) {
				ExecutionReportData erd = new ExecutionReportData();
				erd.setDataKey("read");
				erd.setDataValue(e.toString());
				failurelist.add(erd);
			}
		}

		long updateTime = System.nanoTime();
		futurelist = executor.invokeAll(updateworkersList);
		updateTime = System.nanoTime() - updateTime;
		for (Future<ExecutionReportData> f : futurelist) {
			try {
				list.add(f.get());
			} catch (Exception e) {
				ExecutionReportData erd = new ExecutionReportData();
				erd.setDataKey("update");
				erd.setDataValue(e.toString());
				failurelist.add(erd);
			}
		}

		long deleteTime = System.nanoTime();
		futurelist = executor.invokeAll(deleteworkersList);
		deleteTime = System.nanoTime() - deleteTime;
		for (Future<ExecutionReportData> f : futurelist) {
			try {
				list.add(f.get());
			} catch (Exception e) {
				ExecutionReportData erd = new ExecutionReportData();
				erd.setDataKey("delete");
				erd.setDataValue(e.toString());
				failurelist.add(erd);
			}
		}

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Tests did not finish in time: " + e.toString());
		}

		// Post-test cleanup
		if (!testconfig.getDeleteOperation() || (deleteOps < createOps) || (deleteOps < updateOps) || (deleteOps < readOps)) {
			long postteststarttime = System.nanoTime();
			int maxOps = Math.max(Math.max(createOps, updateOps), readOps);
			int posttestdeletes = maxOps - deleteOps;
			ExecutorService posttestexecutor = Executors.newWorkStealingPool();
			List<Callable<ExecutionReportData>> posttestList = new ArrayList<Callable<ExecutionReportData>>();

			for (int i = deleteOps; i < maxOps; i++) {
				Callable<ExecutionReportData> s3deleteworker = new S3DeleteWorker(
						listofObjects.get(i), api);
				posttestList.add(s3deleteworker);
			}
			posttestexecutor.invokeAll(posttestList);
			posttestexecutor.shutdown();
			try {
				posttestexecutor.awaitTermination(Long.MAX_VALUE,
						TimeUnit.SECONDS);
			} catch (Exception e) {
				System.out.println("PostTest tasks did not finish in time: "
						+ e.toString());
			}
			System.out.println("PostTest deleted " + posttestdeletes
					+ " objects in "
					+ ((System.nanoTime() - postteststarttime) / 1000000)
					+ " ms");
		}

		
		
		// Save the ExecutionReportData objects in the database
		for (ExecutionReportData e : list) {
			e.setExecutionReport(report);
			ExecutionReportDataBiz.ExecutionReportDataCreate(e);
		}

		// Calculate summary numbers for the report
		if (testconfig.getCreateOperation()) {
			System.out
					.println("Creating objects take " + createTime / 1000000
							+ "ms with " + testconfig.getNumberOfThreads()
							+ " threads");
		}
		if (testconfig.getReadOperation()) {
			System.out
					.println("Reading objects take " + readTime / 1000000
							+ "ms with " + testconfig.getNumberOfThreads()
							+ " threads");
		}
		if (testconfig.getUpdateOperation()) {
			System.out
					.println("Updating objects take " + updateTime / 1000000
							+ "ms with " + testconfig.getNumberOfThreads()
							+ " threads");
		}
		if (testconfig.getDeleteOperation()) {
			System.out
					.println("Deleting objects take " + deleteTime / 1000000
							+ "ms with " + testconfig.getNumberOfThreads()
							+ " threads");
		}

		// Populate the report object
		// Total volume sent = (createops + updateops) * sizeofobject
		if (testconfig.getCreateOperation() || testconfig.getUpdateOperation()) {
			report.setTotalVolumeSent(Integer.toString(
					(createOps + updateOps) * testconfig.getObjectSize())
					.toString());
		} else
			report.setTotalVolumeSent("0");

		// Total volume received = (readops) * sizeofobject
		if (testconfig.getReadOperation()) {
		report.setTotalVolumeReceived(Integer.toString(readOps
				* testconfig.getObjectSize()));
		} else report.setTotalVolumeReceived("0");
		
		report.setAvgLatencyPerCrudOperation(Long.toString(((createTime
				+ readTime + updateTime + deleteTime) / 1000000)
				/ testconfig.getNumberOfOperations()));
		report.setNumberRequestSec((int) (testconfig.getNumberOfOperations()
				/ (createTime + readTime + updateTime + deleteTime) / 1000000000));
if(list.size()>0)
{
	
		if (testconfig.getCreateOperation() || testconfig.getReadOperation() || testconfig.getUpdateOperation()) {
			long maxValue = 0, minValue = Long
					.parseLong(list.get(1).getDataValue());
			for (ExecutionReportData erd : list) {
				if (erd.getCrudValue().contains("Create")
						|| erd.getCrudValue().contains("Update")
						|| erd.getCrudValue().contains("Read")) {
					maxValue = (Long.parseLong(erd.getDataValue()) > maxValue) ? Long
							.parseLong(erd.getDataValue()) : maxValue;
					minValue = (Long.parseLong(erd.getDataValue()) < minValue) ? Long
							.parseLong(erd.getDataValue()) : minValue;
				}
			}
			System.out.println(maxValue + " max value");
			System.out.println(minValue + " min value");
			System.out.println(testconfig.getObjectSize() + " object size");
			report.setMaxThroughput(((long) testconfig.getObjectSize() * 1000000000)
					/ minValue + " bytes per second");
			report.setMinThroughput(((long) testconfig.getObjectSize() * 1000000000)
					/ maxValue + " bytes per second");
			System.out.println(((long) testconfig.getObjectSize() * 1000000000)
					/ minValue + " max bytes per second");
			System.out.println(((long) testconfig.getObjectSize() * 1000000000)
					/ maxValue + " min bytes per second");
		} else {
			report.setMaxThroughput("0");
			report.setMinThroughput("0");
		}
}
		return report;
	}

	public boolean validateSwiftConnnection(String user, String key,
			String url, String namespace) {
		// TODO Auto-generated method stub
		System.out.println("In vTestEngine validateSwiftConnnection");
		return true;
	}

	public boolean validateAtmosConnnection(String user, String key,
			String url, String namespace) {
		// TODO Auto-generated method stub
		System.out.println("In vTestEngine validateAtmosConnnection");
		return true;
	}

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

			} else
				select = test.getApiSelecteds();

			for (Api p : apilist) {
				for (ApiSelected s : select) {
					if (p.getApiType().getApiTypeId() == s.getApiType()
							.getApiTypeId()) {
						switch (p.getApiType().getApiTypeName()) {
						case "S3": {

							rpt = runS3Tests(test, p,plan);
							System.out.println("Validated S3");

							break;
						}
						case "Swift": {
							System.out.println("Validated Swift");
							break;
						}
						case "Atmos": {
							System.out.println("Validated Atmos");
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