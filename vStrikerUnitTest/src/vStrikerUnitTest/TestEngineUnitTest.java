package vStrikerUnitTest;

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

public class TestEngineUnitTest {

	@Test
	public void test() throws Exception {

		// TestFileGeneration();
		// TestFileGeneration2();
		// CreateTestPlan();
		// ValidateS3();
		// TestS3();
		TestS3xxxd();
		TestS3xxux();
		TestS3xxud();
		TestS3xrxx();
		TestS3xrxd();
		TestS3xrux();
		TestS3xrud();
		TestS3cxxx();
		TestS3cxxd();
		TestS3cxux();
		TestS3cxud();
		TestS3crxx();
		TestS3crxd();
		TestS3crux();
		TestS3crud();
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
		String username = "user045";
		String password = "vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg";
		String proxy = "http://object.vipronline.com";
		String bucket = "api-test";

		Engine tengine = new VEngine();
		try {
			tengine.validateS3Connection(username, password, proxy, null);
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3xxxd");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3xxxd");
		}
	}

	public void TestS3xxux() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xxux");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3xxux");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3xxux");
		}
	}

	public void TestS3xxud() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xxud");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3xxud");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3xxud");
		}
	}

	public void TestS3xrxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xrxx");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3xrxx");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3xrxx");
		}
	}

	public void TestS3xrxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xrxd");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3xrxd");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3xrxd");
		}
	}

	public void TestS3xrux() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xrux");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3xrux");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3xrux");
		}
	}

	public void TestS3xrud() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3xrud");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3xrud");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3xrud");
		}
	}
	
	public void TestS3cxxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3cxxx");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3cxxx");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3cxxx");
		}
	}

	public void TestS3cxxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3cxxd");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3cxxd");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3cxxd");
		}
	}

	public void TestS3cxux() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3cxux");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3cxux");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3cxux");
		}
	}

	public void TestS3cxud() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3cxud");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3cxud");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3cxud");
		}
	}

	public void TestS3crxx() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3crxx");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3crxx");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3crxx");
		}
	}

	public void TestS3crxd() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3crxd");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3crxd");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3crxd");
		}
	}

	public void TestS3crux() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3crux");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3crux");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3crux");
		}
	}
	
	public void TestS3crud() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3crud");
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
				tengine.runS3Tests(ep, testconfig, api);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Total test time: "
					+ (System.nanoTime() - starttime) / 1000000 + "ms");
			System.out.println("TestEngineUnitTest: Done TestS3crud");
		} else {
			System.out.println("TestEngineUnitTest: Failed TestS3crud");
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
			testconfig.setTestConfigName("S3Tests");
			testconfig.setNumberOfOperations(8);
			testconfig.setNumberOfThreads(4);
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
			api = ApiBiz.ApiSelect((long) 6);
			// Api api = new Api();
			String username = "user045";
			String password = "vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg";
			String proxy = "http://object.vipronline.com";
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
