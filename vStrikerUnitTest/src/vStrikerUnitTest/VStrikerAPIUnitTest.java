package vStrikerUnitTest;
import org.junit.Test;

import vStrikerTestUtilities.vLogger;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.emc.vipr.s3.s3api;
import com.emc.vipr.services.s3.ViPRS3Client;

public class VStrikerAPIUnitTest {

	@Test
	public  void test() throws Exception {
		TestS3API();
		//TestS3ReadBucket();
		
	}
	
	public static void TestS3API() throws Exception
	{
		
		String username="root";
		String password="eEH7s4MBMNcFNZTzPW1FdRmVeWmr7GmfsiOvlUUg";
		String proxy="http://10.247.134.77:9020";
		
		ViPRS3Client conn =s3api.getS3Client(username,password,proxy,"ns");
		
		vLogger.LogInfo("Unit Test S3 API " +conn.S3_SERVICE_NAME);
    	
	}

	public static void TestS3ReadBucket() throws Exception
	{
		
		String username="root";
		String password="eEH7s4MBMNcFNZTzPW1FdRmVeWmr7GmfsiOvlUUg";
		String proxy="http://10.247.134.77:9020";
		String bucket="mediaboard-magdy";
		ObjectListing list =s3api.ReadBucket(username,password,proxy,"ns",bucket);
		   for (S3ObjectSummary objectSummary : 
			   list.getObjectSummaries()) {
			   vLogger.LogInfo("Unit Test S3 API Bucket Read - " + objectSummary.getKey() + "  " +
                       "(size = " + objectSummary.getSize() + 
                       ")");
		   }
	}

}
