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

import vStrikerTestEngine.swift.SwiftCreateWorker;
import vStrikerTestEngine.swift.SwiftDeleteWorker;
import vStrikerTestEngine.swift.SwiftReadWorker;
import vStrikerTestEngine.swift.SwiftUpdateWorker;
import vStrikerTestUtilities.Utilites;
import vStrikerTestUtilities.*;

import com.emc.vipr.swift.swiftapi;

//@author Sanjeev Chauhan

public class SwiftTestClient {

	public boolean validateConnnection(String user, String key, String url,
			String bucket) {
		System.out.println("In vTestEngine validateSwiftConnnection");
		if (user == null || key == null || url == null) {
			System.out.println("Please ensure user, key and url are valid");
			return false;
		}
		System.out.println("user, key and url are:" + user + " " + key + " "
				+ url);
		String	namespace = null;

		vLogger.LogInfo("In vTestEngine validate Swift Connection");
		String TEST_BUCKET = bucket;

		System.out.println("Test Bucket name is: " + TEST_BUCKET);
		try {
			swiftapi.ViPRSwiftClient(user, key, url);
			System.out.println("Get Client worked");
			List<String> listofObjects = Utilites.generateFiles(
					"validationTest", 1000, 1);
			System.out.println(listofObjects.get(0));
			swiftapi.CreateObject(user, key, url, TEST_BUCKET,
					FilenameUtils.getName(listofObjects.get(0)),
					new FileInputStream(listofObjects.get(0)));
			System.out.println("Create object done");
			swiftapi.DeleteObject(user, key, url, TEST_BUCKET,
					FilenameUtils.getName(listofObjects.get(0)));
			System.out.println("Delete object done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Validation failed: " + e.toString());
			return false;
		}
		System.out.println("Validation successfully");
		return true;
	}

	public TestResult runTests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api,ExecutionReport report) throws Exception {
		vLogger.LogInfo("In SwiftTestClient runTests");

		TestResult testResult = new TestResult();

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
		// Create list of SwiftCreateWorkers
		for (int i = 0; i < createOps; i++) {
			Callable<ExecutionReportData> swiftcreateworker = new SwiftCreateWorker(
					listofObjects.get(i), api);
			createworkersList.add(swiftcreateworker);
		}
		// Create read workers
		List<Callable<ExecutionReportData>> readworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of SwiftReadWorkers
		for (int i = 0; i < readOps; i++) {
			Callable<ExecutionReportData> swiftreadworker = new SwiftReadWorker(
					listofObjects.get(i), api);
			readworkersList.add(swiftreadworker);
		}
		// Create update workers
		List<Callable<ExecutionReportData>> updateworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of SwiftUpdateWorkers
		for (int i = 0; i < updateOps; i++) {
			Callable<ExecutionReportData> swiftupdateworker = new SwiftUpdateWorker(
					listofObjects.get(i), api);
			updateworkersList.add(swiftupdateworker);
		}
		// Create delete workers
		List<Callable<ExecutionReportData>> deleteworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of SwiftDeleteWorkers
		for (int i = 0; i < deleteOps; i++) {
			Callable<ExecutionReportData> swiftdeleteworker = new SwiftDeleteWorker(
					listofObjects.get(i), api);
			deleteworkersList.add(swiftdeleteworker);
		}

		// Pre-test
		if (!testconfig.getCreateOperation() || (deleteOps > createOps)
				|| (updateOps > createOps) || (readOps > createOps)) {
			long preteststarttime = System.nanoTime();
			int mostOps = Math.max(Math.max(deleteOps, updateOps), readOps);
			int pretestcreates = mostOps - createOps;
			ExecutorService testprepexecutor = Executors.newWorkStealingPool();
			List<Callable<ExecutionReportData>> pretestList = new ArrayList<Callable<ExecutionReportData>>();

			for (int i = createOps; i < mostOps; i++) {
				Callable<ExecutionReportData> swiftcreateworker = new SwiftCreateWorker(
						listofObjects.get(i), api);
				pretestList.add(swiftcreateworker);
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
		System.out.println("totaltime start : " + totaltime);
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
				erd.setDataKey("Swift");
				erd.setCrudValue("Create");
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
				erd.setDataKey("Swift");
				erd.setCrudValue("Read");
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
				erd.setDataKey("Swift");
				erd.setCrudValue("Update");
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
				erd.setDataKey("Swift");
				erd.setCrudValue("Delete");
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
		totaltime = System.nanoTime() - totaltime;
		System.out.println("totaltime end : " + totaltime / 1000000 + "ms");

		System.out.println("Post-test cleanup:");
		// Post-test cleanup
		if (!testconfig.getDeleteOperation() || (deleteOps < createOps)
				|| (deleteOps < updateOps) || (deleteOps < readOps)) {
			long postteststarttime = System.nanoTime();
			int maxOps = Math.max(Math.max(createOps, updateOps), readOps);
			int posttestdeletes = maxOps - deleteOps;
			ExecutorService posttestexecutor = Executors.newWorkStealingPool();
			List<Callable<ExecutionReportData>> posttestList = new ArrayList<Callable<ExecutionReportData>>();

			for (int i = deleteOps; i < maxOps; i++) {
				Callable<ExecutionReportData> swiftdeleteworker = new SwiftDeleteWorker(
						listofObjects.get(i), api);
				posttestList.add(swiftdeleteworker);
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
			System.out.println("Swift Id of the data object saved: "
					+ e.getExecutionReportDataId());
		}
		System.out.println("Swift Id of the summary object saved: "
				+ report.getExecutionReportId());

		// Calculate summary numbers for the report
		if (testconfig.getCreateOperation()) {
			System.out.println("Creating objects take " + createTime / 1000000
					+ "ms with " + testconfig.getNumberOfThreads() + " threads");
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

		System.out.println("Total test time: " + totaltime / 1000000 + "ms");
		long maxValue = 0, minValue = 0;
		if (list.size() > 0) {

			if ((testconfig.getCreateOperation() && createOps != 0)
					|| (testconfig.getReadOperation() && readOps != 0)
					|| (testconfig.getUpdateOperation() && updateOps != 0)) {
			try{
					minValue = Long.parseLong(list.get(1)
						.getDataValue());
			}catch(Exception e)
			{}
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
				}catch(Exception e)
				{}

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
