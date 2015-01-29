package vStrikerUnitTest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import vStrikerTestUtilities.*;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.emc.*;
import com.emc.vipr.s3.s3api;
import com.emc.vipr.services.s3.ViPRS3Client;

public class VStrikerAPIUnitTest {

	@Test
	public  void test() throws Exception {
		//TestS3API();
		//TestS3ReadBucket();
		TestS3CreateObject();
	}
	
	public static void TestS3API() throws Exception
	{
		
		String username="wuser1@sanity.local";
		String password="BRuu4J/pKJP9pAXCDvKNF+WozM+/4bJsMOuVC83F";
		String proxy="http://10.243.188.178:10101";
		
		ViPRS3Client conn =s3api.getS3Client(username,password,proxy,null);
		
		vLogger.LogInfo("Unit Test S3 API " +conn.S3_SERVICE_NAME);
		System.out.println("Unit Test S3 API " +conn.S3_SERVICE_NAME);
	}

	public static void TestS3ReadBucket() throws Exception
	{
		
		String username="wuser1@sanity.local";
		String password="BRuu4J/pKJP9pAXCDvKNF+WozM+/4bJsMOuVC83F";
		String proxy="http://10.243.188.178:10101";
		String bucket="test";
		ObjectListing list =s3api.ReadBucket(username,password,proxy,null,bucket);
		   for (S3ObjectSummary objectSummary : 
			   list.getObjectSummaries()) {
			   vLogger.LogInfo("Unit Test S3 API Bucket Read - " + objectSummary.getKey() + "  " +
                       "(size = " + objectSummary.getSize() + 
                       ")");
			   
			   System.out.println("Unit Test S3 API Bucket Read - " + objectSummary.getKey() + "  " +
                       "(size = " + objectSummary.getSize() + 
                       ")");
		   }
	}

	public static void TestS3CreateObject() throws Exception
	{
try
{
		String username="wuser1@sanity.local";
		String password="BRuu4J/pKJP9pAXCDvKNF+WozM+/4bJsMOuVC83F";
		String proxy="http://10.243.188.178:10101";
		String bucket="test";
		FileInputStream fis = new FileInputStream("C:\\EMC\\emccode\\vstriker\\vStrikerUI\\StageFiles\\S3 Large Test_1.txt");
 
		s3api.CreateObject(username,password,proxy,null,bucket,"key S3 Large Test_1.txt", fis);
		
}
catch(Exception e)
{
	e.printStackTrace();
}

	}
}
