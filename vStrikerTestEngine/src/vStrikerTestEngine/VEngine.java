package vStrikerTestEngine;

import java.util.List;

import vStrikerTestUtilities.vLogger;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.util.StringUtils;
import com.emc.vipr.s3.s3api;

//@author Sanjeev Chauhan

public class VEngine implements Engine {

	public boolean validateS3Connection(String user, String key, String url,
			String namespace) {
		vLogger.LogInfo("In vTestEngine validateS3Connection");
		String TEST_BUCKET = "vstrikertest";
		Boolean testbucketcheck = false;
		// List buckets, create bucket and delete bucket
		List<Bucket> vbuckets;
		try {
			vbuckets = s3api.ListBuckets(user, key, url, namespace);
			for (Bucket bucket : vbuckets) {
				System.out.println(bucket.getName() + "\t"
						+ StringUtils.fromDate(bucket.getCreationDate()));
				if (bucket.getName().equals(TEST_BUCKET))
					testbucketcheck = true;
			}
			if (!testbucketcheck) {
				s3api.CreateBucket(user, key, url, namespace, TEST_BUCKET);
				s3api.DeleteBuckets(user, key, url, namespace, TEST_BUCKET);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean validateSwiftConnnection(String user, String key, String url,
			String namespace) {
		System.out.println("In vTestEngine validateSwiftConnnection");
		return true;
		// TODO Auto-generated method stub

	}

	public boolean validateAtmosConnnection(String user, String key, String url,
			String namespace) {
		System.out.println("In vTestEngine validateAtmosConnnection");
		return true;
		// TODO Auto-generated method stub

	}

}
