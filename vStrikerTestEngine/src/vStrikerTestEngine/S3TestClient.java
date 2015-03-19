package vStrikerTestEngine;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;

import vStrikerBizModel.ExecutionReportBiz;
import vStrikerBizModel.ExecutionReportDataBiz;
import vStrikerEntities.Api;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.ExecutionReport;
import vStrikerEntities.ExecutionReportData;
import vStrikerEntities.TestConfiguration;
import vStrikerTestEngine.s3.S3CreateWorker;
import vStrikerTestEngine.s3.S3DeleteWorker;
import vStrikerTestEngine.s3.S3ReadWorker;
import vStrikerTestEngine.s3.S3UpdateWorker;
import vStrikerTestUtilities.Utilites;
import vStrikerTestUtilities.*;

import com.emc.vipr.s3.s3api;

//@author Sanjeev Chauhan

public class S3TestClient {

	public boolean validateConnection(String user, String key, String url,
			String bucket) {
		vLogger.LogInfo("In S3TestClient validateConnection");
		if (user == null || key == null || url == null) {
			vLogger.LogError("Please ensure user, key and url are valid");
			return false;
		}
		vLogger.LogInfo("user, key and url are:" + user + " " + key + " " + url);

		String TEST_BUCKET =bucket;
		String namespace=null;
		vLogger.LogInfo("Test Bucket name is: " + TEST_BUCKET);
		try {
			s3api.getS3Client(user, key, url, namespace);
			vLogger.LogInfo("Get Client worked");
			s3api.CreateBucket(user, key, url, namespace, TEST_BUCKET);
			vLogger.LogInfo("Create bucket done");
			List<String> listofObjects = Utilites.generateFiles(
					"validationTest", 1000, 1);
			vLogger.LogInfo(listofObjects.get(0));
			s3api.CreateObject(user, key, url, namespace, TEST_BUCKET,
					FilenameUtils.getName(listofObjects.get(0)),
					new FileInputStream(listofObjects.get(0)));
			vLogger.LogInfo("Create object done");
			s3api.DeleteObject(user, key, url, namespace, TEST_BUCKET,
					FilenameUtils.getName(listofObjects.get(0)));
			vLogger.LogInfo("Delete object done");
			s3api.DeleteBuckets(user, key, url, namespace, TEST_BUCKET);
			vLogger.LogInfo("Delete bucket done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			vLogger.LogError("Validation failed: " + e.toString());
			return false;
		}
		vLogger.LogInfo("Validation successfully");
		return true;
	}

	public TestResult runTests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api,ExecutionReport report) throws Exception {
		TestResult testResult = new TestResult();

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

		if (!testconfig.getCreateOperation() || (deleteOps > createOps)
				|| (updateOps > createOps) || (readOps > createOps)) {
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
		long totaltime = System.nanoTime();
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
		System.out.println("Create operations completed ");

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
		System.out.println("Read operations completed ");

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
		System.out.println("Update operations completed ");

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
		System.out.println("Delete operations completed ");

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (Exception e) {
			System.out.println("Tests did not finish in time: " + e.toString());
		}
		totaltime = System.nanoTime() - totaltime;
		System.out.println("totaltime end : " + totaltime / 1000000 + "ms");

		// Post-test cleanup
		if (!testconfig.getDeleteOperation() || (deleteOps < createOps)
				|| (deleteOps < updateOps) || (deleteOps < readOps)) {
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

		// Create the summary report

		// Save the ExecutionReportData objects in the database
		for (ExecutionReportData e : list) {
			e.setExecutionReport(report);
			ExecutionReportDataBiz.ExecutionReportDataCreate(e);
			System.out.println("S3 Id of the data object saved: " +
			 e.getExecutionReportDataId());
		}
		System.out.println("S3 Id of the summary object saved: "
				+ report.getExecutionReportId());
		// Calculate summary numbers for the report
		if (testconfig.getCreateOperation()) {
			System.out.println("Creating " + createOps + " objects take "
					+ createTime / 1000000 + "ms with "
					+ testconfig.getNumberOfThreads() + " threads");
		}
		if (testconfig.getReadOperation()) {
			System.out.println("Reading " + readOps + " objects take "
					+ readTime / 1000000 + "ms with "
					+ testconfig.getNumberOfThreads() + " threads");
		}
		if (testconfig.getUpdateOperation()) {
			System.out.println("Updating " + updateOps + " objects take "
					+ updateTime / 1000000 + "ms with "
					+ testconfig.getNumberOfThreads() + " threads");
		}
		if (testconfig.getDeleteOperation()) {
			System.out.println("Deleting " + deleteOps + " objects take "
					+ deleteTime / 1000000 + "ms with "
					+ testconfig.getNumberOfThreads() + " threads");
		}

		System.out.println("Total test time: " + totaltime / 1000000 + "ms");
		long maxValue = 0, minValue = 0;
		if (list.size() > 0) {

			if ((testconfig.getCreateOperation() && createOps != 0)
					|| (testconfig.getReadOperation() && readOps != 0)
					|| (testconfig.getUpdateOperation() && updateOps != 0)) {
				try {
					minValue = Long.parseLong(list.get(1)
							.getDataValue());
				} catch (Exception e) {
				}

				try {
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
				} catch (Exception e) {
				}

			}
		}





		// Populate the report object
		testResult.setCreateTime(createTime);
		testResult.setReadTime(readTime);
		testResult.setDeleteTime(deleteTime);
		testResult.setUpdateTime(updateTime);
		testResult.setTotalTime(totaltime);
		testResult.setMax(maxValue);
		testResult.setMin(minValue);
		return testResult;


	}
}
