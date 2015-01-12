package vStrikerTestEngine;

import java.util.UUID;

import vStrikerTestUtilities.vLogger;

import com.emc.vipr.s3.s3api;

//@author Sanjeev Chauhan

public class VEngine implements Engine {

	public boolean validateS3Connection(String user, String key, String url,
			String namespace) {
		vLogger.LogInfo("In vTestEngine validateS3Connection");
        /*
         * For testing purposes
         * user = user045
         * url = http://object.vipronline.com
         * secret key = vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg
         */
		if (user == null || key == null || url == null) {
			System.out.println("Please ensure user, key and url are valid");
			return false;
		}
		if (namespace == null || namespace.length() == 0) {
			namespace = null;
		}
        vLogger.LogInfo("In vTestEngine validateS3Connection");
        String TEST_BUCKET = "vstrikertest" + UUID.randomUUID().toString();
        System.out.println("Test Bucket name is: " + TEST_BUCKET);
        try {
            s3api.CreateBucket(user, key, url, namespace, TEST_BUCKET);
            s3api.DeleteBuckets(user, key, url, namespace, TEST_BUCKET);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println("Unable to create Bucket: " + e.toString());
            return false;
        }
        System.out.println("Test Bucket created and deleted successfully");
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
