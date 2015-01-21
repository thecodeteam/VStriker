package vStrikerUnitTest;
import java.io.IOException;
import java.util.List;

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
		testconfig.setNumberOfOperations(40);
		testconfig.setNumberOfThreads(4);
		testconfig.setObjectSize(100);

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
		s3xxxd(tengine, testconfig, api);
		// 0010
		s3xxux(tengine, testconfig, api);
		// 1111
		s3crud(tengine, testconfig, api);
		System.out.println("Total test time: " + (System.nanoTime() - starttime)/1000000 + "ms");
	}
	
	private void s3xxxd(Engine tengine, TestConfiguration tc, Api api) {
		tc.setCreateOperation(false);
		tc.setReadOperation(false);
		tc.setUpdateOperation(false);
		tc.setDeleteOperation(true);
		tc.setCreatePercent(0);
		tc.setReadPercent(0);
		tc.setUpdatePercent(0);
		tc.setDeletePercent(100);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void s3xxux(Engine tengine, TestConfiguration tc, Api api) {
		tc.setCreateOperation(false);
		tc.setReadOperation(false);
		tc.setUpdateOperation(true);
		tc.setDeleteOperation(false);
		tc.setCreatePercent(0);
		tc.setReadPercent(0);
		tc.setUpdatePercent(100);
		tc.setDeletePercent(0);
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
		tc.setCreatePercent(25);
		tc.setReadPercent(25);
		tc.setUpdatePercent(25);
		tc.setDeletePercent(25);
		try {
			tengine.runS3Tests(tc, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}