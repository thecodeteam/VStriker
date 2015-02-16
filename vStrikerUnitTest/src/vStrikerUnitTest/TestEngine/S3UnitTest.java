package vStrikerUnitTest.TestEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.junit.Test;

import vStrikerBizModel.ApiBiz;
import vStrikerBizModel.ExecutionPlanBiz;
import vStrikerBizModel.TestConfigurationBiz;
import vStrikerEntities.Api;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.TestConfiguration;
import vStrikerTestEngine.Engine;
import vStrikerTestEngine.VEngine;
import vStrikerTestUtilities.Utilites;
import vStrikerTestUtilities.vLogger;

public class S3UnitTest {

	Api api;
	TestConfiguration testconfig;
	ExecutionPlan ep;
	Properties prop;

	@Test
	public void test() throws Exception {

		// Load the properties file
		System.out.println(new File(".").getAbsolutePath());
		prop = new Properties();
		try {
			prop.load(new FileInputStream(
					"./src/vStrikerUnitTest/TestEngine/s3config.properties"));
		} catch (Exception e) {
			System.out.println("Unable to load s3config.properties");
			e.printStackTrace();
			return;
		}
		testconfig = getTestConfiguration();
		api = getApi();
		ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
			System.out
			.println("Need valid testconfig, api, and execution plan to continue");
			return;
		}

		// TestFileGeneration();
		// TestFileGeneration2();
		// CreateTestPlan();
		// ValidateS3();
		// TestS3();
		// TestS3xxxd();
		// TestS3xxux();
		// TestS3xxud();
		// TestS3xrxx();
		// TestS3xrxd();
		// TestS3xrux();
		// TestS3xrud();
		// TestS3cxxx();
		// TestS3cxxd();
		// TestS3cxux();
		// TestS3cxud();
		// TestS3crxx();
		// TestS3crxd();
		// TestS3crux();
		TestS3crud();
	}

	private TestConfiguration getTestConfiguration() {
		try {
			TestConfiguration testconfig = TestConfigurationBiz
					.TestConfigurationSelect(Long.parseLong(prop
							.getProperty("testconfig-id")));
			testconfig.setTestConfigName(prop.getProperty("testconfig-name"));
			testconfig.setNumberOfOperations(Integer.parseInt(prop
					.getProperty("testconfig-numofops")));

			Random rand = new Random();
			testconfig.setNumberOfThreads(rand.nextInt(8) + 1);
			testconfig.setObjectSize(Integer.parseInt(prop
					.getProperty("testconfig-objsize")));
			return testconfig;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Api getApi() {
		Api api;
		try {
			api = ApiBiz.ApiSelect(Long.parseLong(prop.getProperty("api-id")));
			api.setSubtenant(prop.getProperty("username"));
			api.setSecretKey(prop.getProperty("password"));
			api.setUrl(prop.getProperty("proxy"));
			api.setBucket(prop.getProperty("bucket"));
			return api;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private ExecutionPlan getExecutionPlan() {
		ExecutionPlan ep;
		try {
			ep = ExecutionPlanBiz.ExecutionPlanSelect(Long.parseLong(prop
					.getProperty("ep-id")));
			return ep;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void TestFileGeneration() {
		String testConfName = "testme";
		int objSize = 1024 * 1024;
		int numfiles = 4;
		List<String> list;
		try {
			list = Utilites.generateTestFileList(testConfName, objSize,
					numfiles);
			for (String s : list)
				System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return list;
	}

	public void TestFileGeneration2() {
		String testConfName = "testme";
		int objSize = 1024 * 1024;
		int numfiles = 4;
		List<String> list;
		try {
			list = Utilites.generateFiles(testConfName, objSize, numfiles);
			for (String s : list)
				System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return list;
	}

	public void ValidateS3() {
		Engine tengine = new VEngine();
		try {
			tengine.validateS3Connection(prop.getProperty("username"),
					prop.getProperty("password"), prop.getProperty("proxy"),
					null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CreateTestPlan() {
		vLogger.LogInfo("TestEngineUnitTest:CreateTestPlan");
	}

	public void TestS3xxxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xxxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setDeleteOperation(true);
		testconfig.setDeletePercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3xxxd");
	}

	public void TestS3xxux() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xxux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setUpdateOperation(true);
		testconfig.setUpdatePercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3xxux");
	}

	public void TestS3xxud() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xxud");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setDeleteOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3xxud");
	}

	public void TestS3xrxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xrxx");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setReadPercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3xrxx");
	}

	public void TestS3xrxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xrxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setDeleteOperation(true);
		testconfig.setReadOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setReadPercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3xrxd");
	}

	public void TestS3xrux() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xrux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setReadPercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3xrux");
	}

	public void TestS3xrud() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xrud");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setDeleteOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(3);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		testconfig.setReadPercent(percent[2] - percent[1]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3xrud");
	}

	public void TestS3cxxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3cxxx");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setCreatePercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3cxxx");
	}

	public void TestS3cxxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3cxxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setDeleteOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setCreatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3cxxd");
	}

	public void TestS3cxux() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3cxux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setCreatePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3cxux");
	}

	public void TestS3cxud() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3cxud");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setDeleteOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(3);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		testconfig.setCreatePercent(percent[2] - percent[1]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3cxud");
	}

	public void TestS3crxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3crxx");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setCreateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setCreatePercent(percent[0]);
		testconfig.setReadPercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3crxx");
	}

	public void TestS3crxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3crxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setDeleteOperation(true);
		testconfig.setCreateOperation(true);
		int percent[] = createRandomArray(3);
		testconfig.setCreatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		testconfig.setReadPercent(percent[2] - percent[1]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3crxd");
	}

	public void TestS3crux() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3crux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setCreateOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(3);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setCreatePercent(percent[1] - percent[0]);
		testconfig.setReadPercent(percent[2] - percent[1]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3crux");
	}

	public void TestS3crud() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3crud");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setDeleteOperation(true);
		testconfig.setUpdateOperation(true);
		testconfig.setCreateOperation(true);
		int percent[] = createRandomArray(4);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		testconfig.setReadPercent(percent[2] - percent[1]);
		testconfig.setCreatePercent(percent[3] - percent[2]);
		long starttime = System.nanoTime();
		try {
			tengine.runS3Tests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestS3crud");
	}

	private int[] createRandomArray(int size) {
		Random random = new Random();
		int rparray[] = new int[size];
		for (int i = 0; i < size - 1; i++) {
			rparray[i] = random.nextInt(100);
		}
		rparray[size - 1] = 100;
		Arrays.sort(rparray);
		// System.out.println(Arrays.toString(rparray));
		return rparray;
	}

	private void resetTC(TestConfiguration tc) {
		tc.setCreateOperation(false);
		tc.setReadOperation(false);
		tc.setUpdateOperation(false);
		tc.setDeleteOperation(false);
		tc.setCreatePercent(0);
		tc.setReadPercent(0);
		tc.setUpdatePercent(0);
		tc.setDeletePercent(0);
	}

}
