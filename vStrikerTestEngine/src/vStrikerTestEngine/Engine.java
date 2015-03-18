package vStrikerTestEngine;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.ExecutionReport;
import vStrikerEntities.TestConfiguration;

/*
 * @author Sanjeev Chauhan
 */
public interface Engine {
	boolean validateS3Connection(String user, String key, String url,
			String namespace);

	boolean validateSwiftConnection(String user, String key, String url,
			String namespace);

	boolean validateAtmosConnection(String user, String key, String url,
			String namespace);

	vStrikerTestUtilities.TestResult runS3Tests(ExecutionPlan ep, TestConfiguration testconfig,
			Api api,ExecutionReport report) throws Exception;

	vStrikerTestUtilities.TestResult runSwiftTests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api,ExecutionReport report) throws Exception;

	vStrikerTestUtilities.TestResult runAtmosTests(ExecutionPlan ep,
			TestConfiguration testconfig, Api api,ExecutionReport report) throws Exception;

	ExecutionReport runTests(ExecutionPlan plan) throws Exception;

}
