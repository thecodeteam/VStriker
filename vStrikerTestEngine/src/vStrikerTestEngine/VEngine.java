package vStrikerTestEngine;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
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

	public ExecutionReport runS3Tests(TestConfiguration testconfig, Api api)
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
			Callable<ExecutionReportData> s3deleteworker = new S3CreateWorker(
					listofObjects.get(i), api);
			deleteworkersList.add(s3deleteworker);
		}

		// Pre-test
		if (!testconfig.getCreateOperation() || (deleteOps > createOps)) {
			for (String s : listofObjects) {
				System.out.println("Name of file/object: " + s);
				s3api.CreateObject(api.getSubtenant(), api.getSecretKey(),
						api.getUrl(), null, api.getBucket(),
						FilenameUtils.getName(s), new FileInputStream(s));
				System.out.println("Pre-test - Created: " + s);
			}
		}

		// The test
		long sum = 0;
		ExecutorService executor = Executors.newFixedThreadPool(testconfig
				.getNumberOfThreads());
		List<ExecutionReportData> list = new ArrayList<ExecutionReportData>();

		long createTime = System.nanoTime();
		for (int i = 0; i < createOps; i++) {
			try {
				ExecutionReportData reportData = executor.submit(
						createworkersList.get(i)).get();
				list.add(reportData);
			} catch (Exception e) {
				ExecutionReportData erd = new ExecutionReportData();
				erd.setDataKey("create");
				erd.setDataValue(e.toString());
				list.add(erd);
			}
		}
		createTime = System.nanoTime() - createTime;

		long readTime = System.nanoTime();
		for (int i = 0; i < readOps; i++) {
			try {
				ExecutionReportData reportData = executor.submit(
						readworkersList.get(i)).get();
				list.add(reportData);
			} catch (Exception e) {
				ExecutionReportData erd = new ExecutionReportData();
				erd.setDataKey("read");
				erd.setDataValue(e.toString());
				list.add(erd);
			}
		}
		readTime = System.nanoTime() - readTime;

		long updateTime = System.nanoTime();
		for (int i = 0; i < updateOps; i++) {
			try {
				ExecutionReportData reportData = executor.submit(
						updateworkersList.get(i)).get();
				list.add(reportData);
			} catch (Exception e) {
				ExecutionReportData erd = new ExecutionReportData();
				erd.setDataKey("update");
				erd.setDataValue(e.toString());
				list.add(erd);
			}
		}
		updateTime = System.nanoTime() - updateTime;

		long deleteTime = System.nanoTime();
		for (int i = 0; i < deleteOps; i++) {
			try {
				ExecutionReportData reportData = executor.submit(
						deleteworkersList.get(i)).get();
				list.add(reportData);
			} catch (Exception e) {
				ExecutionReportData erd = new ExecutionReportData();
				erd.setDataKey("update");
				erd.setDataValue(e.toString());
				list.add(erd);
			}
		}
		deleteTime = System.nanoTime() - deleteTime;

		System.out.println(sum);
		executor.shutdown();

		// post-test - Delete objects created
		if (!testconfig.getDeleteOperation() || (deleteOps < createOps)) {
			for (String s : listofObjects) {
				try {
					s3api.DeleteObject(api.getSubtenant(), api.getSecretKey(),
							api.getUrl(), null, api.getBucket(),
							FilenameUtils.getName(s));
					System.out.println("Post-test - Deleted: " + s);
				} catch (Exception e) {
					System.out.println("Post test cleanup error: "
							+ e.toString());
				}
			}
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
		report.setTotalVolumeSent(Integer.toString((createOps + updateOps) * testconfig.getObjectSize()).toString());
		// Total volume received = (readops) * sizeofobject
		report.setTotalVolumeReceived(Integer.toString(readOps * testconfig.getObjectSize()));

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
}