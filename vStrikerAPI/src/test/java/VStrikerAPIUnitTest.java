import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import com.emc.atmos.api.AtmosApi;
import org.junit.Test;
import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import com.emc.atmos.api.ObjectId;
import java.text.SimpleDateFormat;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.Date;

import com.emc.vipr.s3.*;
import com.emc.vipr.swift.*;
import com.emc.vipr.atmos.atmosapi;

import com.emc.vipr.services.s3.ViPRS3Client;

public class VStrikerAPIUnitTest {

	public static String username="wuser1@sanity.local";
	public static String atmosusername="7b359e4d039540008ea4fbf4d20cd591/wuser1@sanity.local";
	public static String password="UR89jTVo4GMYJNmHfYIhJQwF7vBPzA1P/dP48bHH";
	public static String s3proxy="http://10.247.188.225:10101";
	public static String swiftproxy="http://10.247.188.225:10501";
	public static String atmosproxy="http://10.247.188.225:10301";

	public static String s3bucket="test-s3";
	public static String swiftbucket="test-swift";
	public static String atmosbucket="test-atmos";
	public static String filelocation="F:\\vstriker\\ResultFiles\\Dashboard-Nov.xlsx";
	public static String fileName ="Dashboard-"+((new SimpleDateFormat("_yyyyMMdd_hhmmss")).format(new Date()))+".xlsx";


	@Test
	public  void test() throws Exception {
		System.out.println("Hello Test");
		TestS3API();
		TestS3CreateObject();

		TestSwiftGetClient();
		TestSwiftCreateObject();

		TestAtmosGetClient();
		TestAtmosCreateObject();
	}


	public static void TestS3API() throws Exception
	{
		ViPRS3Client conn =s3api.getS3Client(username,password,s3proxy,null);
		System.out.println("Unit Test S3 API " +conn.S3_SERVICE_NAME);
	}

	public static void TestS3ReadBucket() throws Exception
	{
		ObjectListing list =s3api.ReadBucket(username,password,s3proxy,null,s3bucket);
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
				FileInputStream fis = new FileInputStream(filelocation);
				s3api.CreateObject(username,password,s3proxy,null,s3bucket,fileName, fis);
			System.out.println("Finished loading S3 Test file");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void TestSwiftGetClient() throws Exception
	{
		
		Account conn =swiftapi.ViPRSwiftClient(username,password,swiftproxy);
		System.out.println("Unit Test SWIFT API " +conn.toString());
	}

	public static void TestSwiftCreateObject() throws Exception
	{
		try
		{
				FileInputStream fis = new FileInputStream(filelocation);
				swiftapi.CreateObject(username,password,swiftproxy,swiftbucket,fileName, fis);
				System.out.println("Finished loading Swift Test file");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void TestAtmosGetClient() throws Exception
	{

		AtmosApi conn =atmosapi.getAtmosApi(atmosusername, password, atmosproxy);
		System.out.println("Unit Test Atmos API " +conn.toString());
	}

	public static void TestAtmosCreateObject() throws Exception
	{
		try
		{
			File fis = new File(filelocation);
			ObjectId id =atmosapi.CreateObject(atmosusername,password,atmosproxy,fileName, fis,atmosbucket);
			System.out.println("Finished loading Atmos Test file"+id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


}
