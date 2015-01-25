package vStrikerUnitTest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import vStrikerEntities.Api;
import vStrikerEntities.TestConfiguration;
import vStrikerTestEngine.Engine;
import vStrikerTestEngine.VEngine;
import vStrikerTestUtilities.Utilites;
import vStrikerTestUtilities.vLogger;

public class TestEngineUnitTest {

	@Test
	public  void test() throws Exception {

		//TestFileGeneration();
		//TestFileGeneration2();
		//CreateTestPlan();
		//ValidateS3();
		TestS3();
	}
	
	public void TestFileGeneration()
	{
		String testConfName="testme";
		int objSize=1024*1024;
		int numfiles=4;
		List<String> list;
		try {
			list = Utilites.generateTestFileList(testConfName, objSize, numfiles);
			for(String s :list)
				System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//return list;

	}
	
	public void TestFileGeneration2()
	{
		String testConfName="testme";
		int objSize=1024*1024;
		int numfiles=4;
		List<String> list;
		try {
			list = Utilites.generateFiles(testConfName, objSize, numfiles);
			for(String s :list)
				System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		//return list;
	}
	
	public void ValidateS3() {
		String username="user045";
		String password="vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg";
		String proxy="http://object.vipronline.com";
		String bucket="api-test";
		
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
	
	public void TestS3() {
		vLogger.LogInfo("TestEngineUnitTest:TestS3");
		TestConfiguration testconfig = new TestConfiguration();
		testconfig.setTestConfigName("S3Tests");
		testconfig.setNumberOfOperations(100);
		testconfig.setNumberOfThreads(4);
		testconfig.setObjectSize(1024*1024);

		String username="user045";
		String password="vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg";
		String proxy="http://object.vipronline.com";
		String bucket="api-test";
		
		Api api = new Api();
		api.setSubtenant(username);
		api.setSecretKey(password);
		api.setUrl(proxy);
		api.setBucket(bucket);
		
		Engine tengine = new VEngine();
		long starttime = System.nanoTime();
		// CRUD = 0000 - 1111 
		// 0001
		System.out.println("Starting s3xxxd");
		s3xxxd(tengine, testconfig, api);
		System.out.println("Done s3xxxd");
		// 0010
		System.out.println("Starting s3xxux");
		s3xxux(tengine, testconfig, api);
		System.out.println("Done s3xxux");
		// 0011
		System.out.println("Starting s3xxud");
		s3xxud(tengine, testconfig, api);
		System.out.println("Done s3xxud");
		// 0100
		System.out.println("Starting s3xrxx");
		s3xrxx(tengine, testconfig, api);
		System.out.println("Done s3xrxx");
		// 0101
		System.out.println("Starting s3xrxd");
		s3xrxd(tengine, testconfig, api);
		System.out.println("Done s3xrxd");
		// 0110
		System.out.println("Starting s3xrux");
		s3xrux(tengine, testconfig, api);
		System.out.println("Done s3xrux");
		// 0111
		System.out.println("Starting s3xrud");
		s3xrud(tengine, testconfig, api);
		System.out.println("Done s3xrud");
		// 1000
		System.out.println("Starting s3cxxx");
		s3cxxx(tengine, testconfig, api);
		System.out.println("Done s3cxxx");
		// 1001
		System.out.println("Starting s3cxxd");
		s3cxxd(tengine, testconfig, api);
		System.out.println("Done s3cxxd");
		// 1010
		System.out.println("Starting s3cxux");
		s3cxux(tengine, testconfig, api);
		System.out.println("Done s3cxux");
		// 1011
		System.out.println("Starting s3cxud");
		s3cxud(tengine, testconfig, api);
		System.out.println("Done s3cxud");
		// 1100
		System.out.println("Starting s3crxx");
		s3crxx(tengine, testconfig, api);
		System.out.println("Done s3crxx");
		// 1101
		System.out.println("Starting s3crxd");
		s3crxd(tengine, testconfig, api);
		System.out.println("Done s3crxd");
		// 1110
		System.out.println("Starting s3crux");
		s3crux(tengine, testconfig, api);
		System.out.println("Done s3crux");
		// 1111
		System.out.println("Starting s3crud");
		s3crud(tengine, testconfig, api);
		System.out.println("Done s3crud");
		System.out.println("Total test time: " + (System.nanoTime() - starttime)/1000000 + "ms");
	}
	
	private void s3xxxd(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setDeleteOperation(true);
		tc.setDeletePercent(100);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3xxux(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setUpdateOperation(true);
		tc.setUpdatePercent(100);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void s3xxud(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setUpdateOperation(true);
		tc.setDeleteOperation(true);
		int percent[] = createRandomArray(2);
		tc.setUpdatePercent(percent[0]);
		tc.setDeletePercent(percent[1]-percent[0]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3xrxx(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setReadOperation(true);
		tc.setCreatePercent(0);
		tc.setReadPercent(100);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3xrxd(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setReadOperation(true);
		tc.setDeleteOperation(true);
		int percent[] = createRandomArray(2);
		tc.setReadPercent(percent[0]);
		tc.setDeletePercent(percent[1]-percent[0]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3xrux(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setReadOperation(true);
		tc.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		tc.setReadPercent(percent[0]);
		tc.setUpdatePercent(percent[1]-percent[0]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3xrud(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setReadOperation(true);
		tc.setUpdateOperation(true);
		tc.setDeleteOperation(true);
		int percent[] = createRandomArray(3);
		tc.setReadPercent(percent[0]);
		tc.setUpdatePercent(percent[1]-percent[0]);
		tc.setDeletePercent(percent[2]-percent[1]);	
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3cxxx(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setCreateOperation(true);
		tc.setCreatePercent(100);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3cxxd(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setCreateOperation(true);
		tc.setDeleteOperation(true);	
		int percent[] = createRandomArray(2);
		tc.setCreatePercent(percent[0]);
		tc.setDeletePercent(percent[1]-percent[0]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3cxux(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setCreateOperation(true);
		tc.setUpdateOperation(true);
		int percent[] = createRandomArray(2);
		tc.setCreatePercent(percent[0]);
		tc.setUpdatePercent(percent[1]-percent[0]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3cxud(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setCreateOperation(true);
		tc.setUpdateOperation(true);
		tc.setDeleteOperation(true);
		int percent[] = createRandomArray(3);
		tc.setCreatePercent(percent[0]);
		tc.setUpdatePercent(percent[1]-percent[0]);
		tc.setDeletePercent(percent[2]-percent[1]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3crxx(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setCreateOperation(true);
		tc.setReadOperation(true);
		int percent[] = createRandomArray(2);
		tc.setCreatePercent(percent[0]);
		tc.setReadPercent(percent[1]-percent[0]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3crxd(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setCreateOperation(true);
		tc.setReadOperation(true);
		tc.setDeleteOperation(true);
		int percent[] = createRandomArray(3);
		tc.setCreatePercent(percent[0]);
		tc.setReadPercent(percent[1]-percent[0]);
		tc.setDeletePercent(percent[2]-percent[1]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3crux(Engine tengine, TestConfiguration tc, Api api) {
		resetTC(tc);
		tc.setCreateOperation(true);
		tc.setReadOperation(true);
		tc.setUpdateOperation(true);
		int percent[] = createRandomArray(3);
		tc.setCreatePercent(percent[0]);
		tc.setReadPercent(percent[1]-percent[0]);
		tc.setUpdatePercent(percent[2]-percent[1]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3crud(Engine tengine, TestConfiguration tc, Api api) {
		tc.setCreateOperation(true);
		tc.setReadOperation(true);
		tc.setUpdateOperation(true);
		tc.setDeleteOperation(true);
		int percent[] = createRandomArray(4);
		tc.setCreatePercent(percent[0]);
		tc.setReadPercent(percent[1]-percent[0]);
		tc.setUpdatePercent(percent[2]-percent[1]);
		tc.setDeletePercent(percent[3]-percent[2]);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int [] createRandomArray(int size) {
		Random random = new Random();
		int rparray[] = new int[size];
		for (int i=0; i < size-1; i++) {
			rparray[i] = random.nextInt(100);
		}
		rparray[size-1] = 100;
		Arrays.sort(rparray);
		//System.out.println(Arrays.toString(rparray));
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