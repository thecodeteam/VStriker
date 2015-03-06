import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;


import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import com.emc.vipr.s3.*;
import com.emc.vipr.swift.*;

import com.emc.vipr.services.s3.ViPRS3Client;

public class VStrikerAPIUnitTest {

	@Test
	public  void test() throws Exception {
		//TestS3API();
		//TestS3ReadBucket();
		//TestS3CreateObject();
		System.out.println("Hello Test");
		TestSwiftGetClient();
		TestSwiftCreateObject();
	}
	
	public static void TestS3API() throws Exception
	{
		
		String username="wuser1@sanity.local";
		String password="BRuu4J/pKJP9pAXCDvKNF+WozM+/4bJsMOuVC83F";
		String proxy="http://10.243.188.178:10101";
		
		ViPRS3Client conn =s3api.getS3Client(username,password,proxy,null);
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

	public static void TestSwiftGetClient() throws Exception
	{
		
		String username="wuser1@sanity.local";
		String password="uHJop3/sQpIcmBi1XaVYilrDKjBiXrycPcSTkuxr";
		String proxy="http://10.243.188.178:10501";
		
		Account conn =swiftapi.ViPRSwiftClient(username,password,proxy);

		System.out.println("Unit Test SWIFT API " +conn.toString());
	}

	public static void TestSwiftCreateObject() throws Exception
	{
try
{
		String username="wuser1@sanity.local";
		String password="uHJop3/sQpIcmBi1XaVYilrDKjBiXrycPcSTkuxr";
		String proxy="http://10.243.188.178:10501";
		String bucket="test";
		FileInputStream fis = new FileInputStream("C:\\EMC\\emccode\\vstriker\\ResultFiles\\Test Execytuib.csv");
 
		swiftapi.CreateObject(username,password,proxy,bucket,"Test Execytuib.csv", fis);
		
}
catch(Exception e)
{
	e.printStackTrace();
}

	}


}
