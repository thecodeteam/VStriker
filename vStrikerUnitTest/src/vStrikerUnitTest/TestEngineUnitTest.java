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
		ValidateS3();
		//TestS3();
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
		testconfig.setNumberOfOperations(1);
		testconfig.setNumberOfThreads(1);
		testconfig.setObjectSize(100);
		//testconfig.setCreateOperation(true);
		testconfig.setReadOperation(true);
		//testconfig.setUpdateOperation(true);
		//testconfig.setDeleteOperation(true);
		testconfig.setCreatePercent(25);
		testconfig.setReadPercent(25);
		testconfig.setUpdatePercent(25);
		testconfig.setDeletePercent(25);
		
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
		try {
			tengine.runS3Tests(testconfig, api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
