package vStrikerUnitTest.TestEngine;

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

//@author Sanjeev Chauhan

public class SwiftTests {

	Api api;
	TestConfiguration testconfig;
	ExecutionPlan ep;
	Properties prop;

	@Test
	public void test() throws Exception {

		// Load the properties file
		prop = new Properties();
		try {
			prop.load(new FileInputStream(
					"./src/vStrikerUnitTest/TestEngine/swiftconfig.properties"));
		} catch (Exception e) {
			System.out.println("Unable to load swiftconfig.properties");
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

		// ValidateSwift();
		// TestSwiftxxxd();
		// TestSwiftxxux();
		// TestSwiftxxud();
		// TestSwiftxrxx();
		// TestSwiftxrxd();
		// TestSwiftxrux();
		// TestSwiftxrud();
		// TestSwiftcxxx();
		// TestSwiftcxxd();
		// TestSwiftcxux();
		// TestSwiftcxud();
		// TestSwiftcrxx();
		// TestSwiftcrxd();
		// TestSwiftcrux();
		TestSwiftcrud();
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

	public void ValidateSwift() {
		Engine tengine = new VEngine();
		try {
			tengine.validateSwiftConnection(prop.getProperty("username"),
					prop.getProperty("password"), prop.getProperty("proxy"),
					null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void TestSwiftxxxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxxxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setDeleteOperation(true);
		testconfig.setDeletePercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftxxxd");
	}

	public void TestSwiftxxux() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxxux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setUpdateOperation(true);
		testconfig.setUpdatePercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftxxux");
	}

	public void TestSwiftxxud() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxxud");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setDeleteOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftxxud");
	}

	public void TestSwiftxrxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxrxx");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setReadPercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftxrxx");
	}

	public void TestSwiftxrxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxrxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setDeleteOperation(true);
		testconfig.setReadOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setReadPercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftxrxd");
	}

	public void TestSwiftxrux() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxrux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setReadPercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftxrux");
	}

	public void TestSwiftxrud() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxrud");
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
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftxrud");
	}

	public void TestSwiftcxxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcxxx");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setCreatePercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftcxxx");
	}

	public void TestSwiftcxxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcxxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setDeleteOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setCreatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftcxxd");
	}

	public void TestSwiftcxux() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcxux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setCreatePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftcxux");
	}

	public void TestSwiftcxud() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcxud");
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
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftcxud");
	}

	public void TestSwiftcrxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcrxx");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setCreateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setCreatePercent(percent[0]);
		testconfig.setReadPercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftcrxx");
	}

	public void TestSwiftcrxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcrxd");
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
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftcrxd");
	}

	public void TestSwiftcrux() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcrux");
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
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftcrux");
	}

	public void TestSwiftcrud() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcrud");
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
			tengine.runSwiftTests(ep, testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done TestSwiftcrud");
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
