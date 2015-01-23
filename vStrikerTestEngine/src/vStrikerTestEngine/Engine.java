package vStrikerTestEngine;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.ExecutionReport;
import vStrikerEntities.TestConfiguration;

/*
 * @author Sanjeev Chauhan
 */
public interface Engine {
	boolean validateS3Connection(String user, String key, String url, String namespace);
	boolean validateSwiftConnnection(String user, String key, String url, String namespace);
	boolean validateAtmosConnnection(String user, String key, String url, String namespace);
	ExecutionReport runS3Tests(TestConfiguration testconfig, Api api) throws Exception;
	
	ExecutionReport runTests(ExecutionPlan plan) throws Exception;
}
