package vStrikerUnitTest;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.apache.commons.logging.impl.Log4JLogger;

import com.amazonaws.services.s3.model.Bucket;
import com.emc.*;
import com.emc.vipr.s3.s3api;
import com.emc.vipr.services.s3.ViPRS3Client;
public class VStrikerAPIUnitTest {

	@Test
	public  void test() throws Exception {
		TestS3API();
		TestS3ListBuckets();
		
	}
	
	public static void TestS3API() throws Exception
	{
		
		String username="wuser1@sanity.local";
		String password="GdzE4oBSL6WOwAnjJ33qAPGTDulEgDZWVQzZ+YvO";
		String proxy="http://10.247.188.221:10101";
		//(String S3_ACCESS_KEY_ID,String S3_SECRET_KEY,String S3_ENDPOINT,String S3_ViPR_NAMESPACE ) 
		
		ViPRS3Client conn =s3api.getS3Client(username,password,proxy,"ns");
		
    	System.out.println( conn.S3_SERVICE_NAME);
	}

	public static void TestS3ListBuckets() throws Exception
	{
		
		String username="wuser1@sanity.local";
		String password="GdzE4oBSL6WOwAnjJ33qAPGTDulEgDZWVQzZ+YvO";
		String proxy="http://10.247.188.221:10101";
		//(String S3_ACCESS_KEY_ID,String S3_SECRET_KEY,String S3_ENDPOINT,String S3_ViPR_NAMESPACE ) 
		
		List<Bucket> list =s3api.ListBuckets(username,password,proxy,"ns");
		for(Iterator<Bucket> i = list.iterator(); i.hasNext(); ) {
			Bucket item = i.next();
		    System.out.println(item.getName());
		}
	}

}
