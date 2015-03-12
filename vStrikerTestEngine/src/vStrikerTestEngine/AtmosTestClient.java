package vStrikerTestEngine;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.io.File;
import org.apache.commons.io.FilenameUtils;

import vStrikerBizModel.ExecutionReportBiz;
import vStrikerBizModel.ExecutionReportDataBiz;
import vStrikerEntities.Api;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.ExecutionReport;
import vStrikerEntities.ExecutionReportData;
import vStrikerEntities.TestConfiguration;
import vStrikerTestEngine.atmos.AtmosCreateWorker;
import vStrikerTestEngine.atmos.AtmosDeleteWorker;
import vStrikerTestEngine.atmos.AtmosReadWorker;
import vStrikerTestEngine.atmos.AtmosUpdateWorker;
import vStrikerTestUtilities.Utilites;
import vStrikerTestUtilities.vLogger;

import com.emc.vipr.atmos.atmosapi;

//@author Sanjeev Chauhan

public class AtmosTestClient {

	public boolean validateConnnection(String user, String key, String url,
			String namespace) {
		System.out.println("In AtmosTestClient validateConnnection");
		vLogger.LogInfo("In Atmos TestClient validateConnection");
		if (user == null || key == null || url == null) {
			vLogger.LogError("Please ensure user, key and url are valid");
			return false;
		}
		vLogger.LogInfo("user, key and url are:" + user + " " + key + " " + url);
		if (namespace == null || namespace.length() == 0) {
			namespace = null;
		}
		try {
			atmosapi.getAtmosApi(user, key, url);
			vLogger.LogInfo("Get Api worked");
			List<String> listofObjects = Utilites.generateFiles(
					"validationTest", 1000, 1);
			vLogger.LogInfo(listofObjects.get(0));
			atmosapi.CreateObject(user, key, url,
					FilenameUtils.getName(listofObjects.get(0)),
					new File(listofObjects.get(0)), "contentType");
			vLogger.LogInfo("Create object done");
			atmosapi.DeleteObject(user, key, url,
					FilenameUtils.getName(listofObjects.get(0)), "oid");
			vLogger.LogInfo("Delete object done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			vLogger.LogError("Validation failed: " + e.toString());
			return false;
		}
		vLogger.LogInfo("Validation successfully");
		return true;
	}

	public ExecutionReport runTests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api) throws Exception {
		vLogger.LogInfo("In AtmosTestClient runTests");

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
		// Create list of AtmosCreateWorkers
		for (int i = 0; i < createOps; i++) {
			Callable<ExecutionReportData> atmoscreateworker = new AtmosCreateWorker(
					listofObjects.get(i), api);
			createworkersList.add(atmoscreateworker);
		}
		// Create read workers
		List<Callable<ExecutionReportData>> readworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of AtmosReadWorkers
		for (int i = 0; i < readOps; i++) {
			Callable<ExecutionReportData> atmosreadworker = new AtmosReadWorker(
					listofObjects.get(i), api);
			readworkersList.add(atmosreadworker);
		}
		// Create update workers
		List<Callable<ExecutionReportData>> updateworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of AtmosUpdateWorkers
		for (int i = 0; i < updateOps; i++) {
			Callable<ExecutionReportData> atmosupdateworker = new AtmosUpdateWorker(
					listofObjects.get(i), api);
			updateworkersList.add(atmosupdateworker);
		}
		// Create delete workers
		List<Callable<ExecutionReportData>> deleteworkersList = new ArrayList<Callable<ExecutionReportData>>();
		// Create list of AtmosDeleteWorkers
		for (int i = 0; i < deleteOps; i++) {
			Callable<ExecutionReportData> atmosdeleteworker = new AtmosDeleteWorker(
					listofObjects.get(i), api);
			deleteworkersList.add(atmosdeleteworker);
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
				Callable<ExecutionReportData> atmoscreateworker = new AtmosCreateWorker(
						listofObjects.get(i), api);
				pretestList.add(atmoscreateworker);
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
				erd.setDataKey("Atmos");
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
				erd.setDataKey("Atmos");
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
				erd.setDataKey("Atmos");
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
				erd.setDataKey("Atmos");
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

		// Post-test cleanup
		if (!testconfig.getDeleteOperation() || (deleteOps < createOps)
				|| (deleteOps < updateOps) || (deleteOps < readOps)) {
			long postteststarttime = System.nanoTime();
			int maxOps = Math.max(Math.max(createOps, updateOps), readOps);
			int posttestdeletes = maxOps - deleteOps;
			ExecutorService posttestexecutor = Executors.newWorkStealingPool();
			List<Callable<ExecutionReportData>> posttestList = new ArrayList<Callable<ExecutionReportData>>();

			for (int i = deleteOps; i < maxOps; i++) {
				Callable<ExecutionReportData> atmosdeleteworker = new AtmosDeleteWorker(
						listofObjects.get(i), api);
				posttestList.add(atmosdeleteworker);
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
		ExecutionReport report = new ExecutionReport();
		report.setExecutionName(LocalDateTime.now().toString());
		// report.setExecutionReportId(1);
		report.setExecutionPlan(ep);
		ExecutionReportBiz.ExecutionReportCreate(report);

		// Save the ExecutionReportData objects in the database
		for (ExecutionReportData e : list) {
			e.setExecutionReport(report);
			ExecutionReportDataBiz.ExecutionReportDataCreate(e);
			System.out.println("Id of the data object saved: "
					+ e.getExecutionReportDataId());
		}
		System.out.println("Id of the summary object saved: "
				+ report.getExecutionReportId());
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

		System.out.println("Total test time: " + totaltime / 1000000 + "ms");

		// Populate the report object
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

		report.setAvgLatencyPerCrudOperation(Long.toString(((createTime
				+ readTime + updateTime + deleteTime) / 1000000)
				/ testconfig.getNumberOfOperations()));
		report.setNumberRequestSec((int) (testconfig.getNumberOfOperations()
				/ (createTime + readTime + updateTime + deleteTime) / 1000000000));
		if (list.size() > 0) {

			if ((testconfig.getCreateOperation() && createOps != 0)
					|| (testconfig.getReadOperation() && readOps != 0)
					|| (testconfig.getUpdateOperation() && updateOps != 0)) {
				long maxValue = 0, minValue = Long.parseLong(list.get(1)
						.getDataValue());
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
				System.out.println(maxValue + " ms - max value");
				System.out.println(minValue + " ms - min value");
				System.out.println(testconfig.getObjectSize() + " object size");
				report.setMaxThroughput(((long) testconfig.getObjectSize() * 1000)
						/ minValue + " bytes per second");
				report.setMinThroughput(((long) testconfig.getObjectSize() * 1000)
						/ maxValue + " bytes per second");
				System.out.println(((long) testconfig.getObjectSize() * 1000)
						/ minValue + " max bytes per second");
				System.out.println(((long) testconfig.getObjectSize() * 1000)
						/ maxValue + " min bytes per second");
			} else {
				report.setMaxThroughput("0");
				report.setMinThroughput("0");
			}
		}
		ExecutionReportBiz.ExecutionReportUpdate(report);
		return report;
	}

}
