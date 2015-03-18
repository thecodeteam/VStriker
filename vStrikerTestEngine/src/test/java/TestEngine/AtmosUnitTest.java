package vStrikerUnitTest.TestEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.junit.Test;

import vStrikerBizModel.ApiBiz;
import vStrikerBizModel.ExecutionPlanBiz;
import vStrikerBizModel.TestConfigurationBiz;
import vStrikerEntities.*;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.TestConfiguration;
import vStrikerTestEngine.Engine;
import vStrikerTestEngine.VEngine;
import vStrikerTestUtilities.Utilites;
import vStrikerTestUtilities.vLogger;

//@author Magdy Salem

public class AtmosUnitTest {

	Api api =new Api();
	TestConfiguration testconfig = new TestConfiguration();
	ExecutionPlan ep = new ExecutionPlan();
	Properties prop;
	vStrikerEntities.ExecutionReport report = new vStrikerEntities.ExecutionReport();
	@Test
	public void test()  {

		try {
			// Load the properties file
			prop = new Properties();
			try {
				prop.load(new FileInputStream(
						"./src/test/java/TestEngine/atmosconfig.properties"));
			} catch (Exception e) {
				System.out.println("Unable to load atmosconfig.properties");
				e.printStackTrace();
				return;
			}
			testconfig = getTestConfiguration();
			api = getApi();
			ep = getExecutionPlan();
			ExecutionReport report = new vStrikerEntities.ExecutionReport();
			report.setExecutionName("atmos-unit-test");
			report.setExecutionPlan(ep);

			if (testconfig != null && api != null && ep != null) {
				System.out
						.println("Need valid testconfig, api, and execution plan to continue");
				return;
			}

			// Validateatmos();
			// Testatmosxxxd();
			// Testatmosxxux();
			// Testatmosxxud();
			// Testatmosxrxx();
			// Testatmosxrxd();
			// Testatmosxrux();
			// Testatmosxrud();
			Testatmoscxxx();
			// Testatmoscxxd();
			// Testatmoscxux();
			// Testatmoscxud();
			// Testatmoscrxx();
			// Testatmoscrxd();
			// Testatmoscrux();
			//Testatmoscrud();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private TestConfiguration getTestConfiguration() {
		try {
			long id=Long.parseLong(prop.getProperty("testconfig-id"));
			testconfig = TestConfigurationBiz.TestConfigurationSelect(id);
			//testconfig.setTestConfigName(prop.getProperty("testconfig-name"));
			//testconfig.setNumberOfOperations(Integer.parseInt(prop.getProperty("testconfig-numofops")));

			//Random rand = new Random();
			//testconfig.setNumberOfThreads(rand.nextInt(8) + 1);
			//testconfig.setObjectSize(Integer.parseInt(prop.getProperty("testconfig-objsize")));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return testconfig;
	}

	private Api getApi() {
		try {

			api = ApiBiz.ApiSelect(Long.parseLong(prop.getProperty("api-id")));
			/*api.setSubtenant(prop.getProperty("username"));
			api.setSecretKey(prop.getProperty("password"));
			api.setUrl(prop.getProperty("proxy"));
			api.setBucket(prop.getProperty("bucket"));*/
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
		int objSize = 1024 ;
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
		int objSize = 1024 ;
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

	public void Validateatmos() {
		Engine tengine = new VEngine();
		try {
			tengine.validateAtmosConnection(prop.getProperty("username"),
					prop.getProperty("password"), prop.getProperty("proxy"),
					null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Testatmosxxxd() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmosxxxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setDeleteOperation(true);
		testconfig.setDeletePercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmosxxxd");
	}

	public void Testatmosxxux() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmosxxux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setUpdateOperation(true);
		testconfig.setUpdatePercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmosxxux");
	}

	public void Testatmosxxud() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmosxxud");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setDeleteOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmosxxud");
	}

	public void Testatmosxrxx() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmosxrxx");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setReadPercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmosxrxx");
	}

	public void Testatmosxrxd() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmosxrxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setDeleteOperation(true);
		testconfig.setReadOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setReadPercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmosxrxd");
	}

	public void Testatmosxrux() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmosxrux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setReadPercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmosxrux");
	}

	public void Testatmosxrud() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmosxrud");
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
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmosxrud");
	}

	public void Testatmoscxxx() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmoscxxx");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setCreatePercent(100);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmoscxxx");
	}

	public void Testatmoscxxd() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmoscxxd");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setDeleteOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setCreatePercent(percent[0]);
		testconfig.setDeletePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmoscxxd");
	}

	public void Testatmoscxux() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmoscxux");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setCreateOperation(true);
		testconfig.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setUpdatePercent(percent[0]);
		testconfig.setCreatePercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmoscxux");
	}

	public void Testatmoscxud() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmoscxud");
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
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmoscxud");
	}

	public void Testatmoscrxx() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmoscrxx");
		Engine tengine = new VEngine();
		resetTC(testconfig);
		testconfig.setReadOperation(true);
		testconfig.setCreateOperation(true);
		int percent[] = createRandomArray(2);
		testconfig.setCreatePercent(percent[0]);
		testconfig.setReadPercent(percent[1] - percent[0]);
		long starttime = System.nanoTime();
		try {
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmoscrxx");
	}

	public void Testatmoscrxd() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmoscrxd");
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
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmoscrxd");
	}

	public void Testatmoscrux() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmoscrux");
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
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmoscrux");
	}

	public void Testatmoscrud() {
		vLogger.LogInfo("TestEngineUnitTest:Testatmoscrud");
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
			tengine.runAtmosTests(ep, testconfig, api,report);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total run time: " + (System.nanoTime() - starttime)
				/ 1000000 + "ms");
		System.out.println("TestEngineUnitTest: Done Testatmoscrud");
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
