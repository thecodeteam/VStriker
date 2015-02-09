package vStrikerUnitTest.TestEngine;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

	@Test
	public void test() throws Exception {

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
		String username = "user045";
		String password = "vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg";
		String proxy = "http://object.vipronline.com";
		String bucket = "api-test";

		Engine tengine = new VEngine();
		try {
			tengine.validateSwiftConnnection(username, password, proxy, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void TestSwiftxxxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxxxd");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftxxxd");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftxxxd");
		}
	}

	public void TestSwiftxxux() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxxux");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftxxux");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftxxux");
		}
	}

	public void TestSwiftxxud() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxxud");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftxxud");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftxxud");
		}
	}

	public void TestSwiftxrxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxrxx");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftxrxx");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftxrxx");
		}
	}

	public void TestSwiftxrxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxrxd");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftxrxd");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftxrxd");
		}
	}

	public void TestSwiftxrux() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxrux");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftxrux");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftxrux");
		}
	}

	public void TestSwiftxrud() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftxrud");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftxrud");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftxrud");
		}
	}

	public void TestSwiftcxxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcxxx");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftcxxx");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftcxxx");
		}
	}

	public void TestSwiftcxxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcxxd");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftcxxd");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftcxxd");
		}
	}

	public void TestSwiftcxux() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcxux");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftcxux");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftcxux");
		}
	}

	public void TestSwiftcxud() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcxud");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftcxud");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftcxud");
		}
	}

	public void TestSwiftcrxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcrxx");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftcrxx");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftcrxx");
		}
	}

	public void TestSwiftcrxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcrxd");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftcrxd");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftcrxd");
		}
	}

	public void TestSwiftcrux() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcrux");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftcrux");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftcrux");
		}
	}

	public void TestSwiftcrud() {
		vLogger.LogInfo("TestEngineUnitTest:TestSwiftcrud");
		TestConfiguration testconfig = getTestConfiguration();
		Api api = getApi();
		ExecutionPlan ep = getExecutionPlan();
		if (testconfig != null && api != null && ep != null) {
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
			System.out.println("Total run time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestSwiftcrud");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestSwiftcrud");
		}
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

	private TestConfiguration getTestConfiguration() {
		try {
			TestConfiguration testconfig = TestConfigurationBiz
					.TestConfigurationSelect(80);
			// TestConfiguration testconfig = new TestConfiguration();
			testconfig.setTestConfigName("SwiftTests");
			testconfig.setNumberOfOperations(8);

			Random rand = new Random();
			testconfig.setNumberOfThreads(rand.nextInt(8) + 1);
			testconfig.setObjectSize(1024 * 1024);
			return testconfig;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Api getApi() {
		Api api;
		try {
			api = ApiBiz.ApiSelect(6);
			// String username = "user045";
			String username = " wuser1@sanity.local";
			// String password = "vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg";
			String password = "PQ17d30xaL41DL8Uxq7DySy3ZBu5Vi8xS3ksoPP+";
			String proxy = "http:// hb-test-env-002.cloudapp.net:10501";
			String bucket = "api-test";

			api.setSubtenant(username);
			api.setSecretKey(password);
			api.setUrl(proxy);
			api.setBucket(bucket);
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
			ep = ExecutionPlanBiz.ExecutionPlanSelect(6);
			return ep;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
