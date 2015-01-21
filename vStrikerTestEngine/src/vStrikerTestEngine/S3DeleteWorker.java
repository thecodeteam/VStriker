package vStrikerTestEngine;

import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionReportData;

import com.emc.vipr.s3.s3api;

//@author Sanjeev Chauhan

public class S3DeleteWorker implements Callable<ExecutionReportData> {
	private String objectLocation;
	private Api api;

	public S3DeleteWorker(String objectLocation, Api api) {
		this.objectLocation = objectLocation;
		this.api = api;
	}

	public ExecutionReportData call() throws Exception {
		System.out.println("S3DeleteWorker.call()");
		System.out.println("ObjectLocation: " + objectLocation);
		System.out.println("Thread: " + Thread.currentThread().getName());

		// Execution_report_data object
		ExecutionReportData reportData = new ExecutionReportData();

		// Create object in S3
		long startTime = System.nanoTime();
		s3api.DeleteObject(api.getSubtenant(), api.getSecretKey(),
				api.getUrl(), null, api.getBucket(), FilenameUtils.getName(objectLocation));
		long endTime = System.nanoTime();
		System.out.println("DeleteObject execution time: " + (endTime - startTime));
		reportData.setDataKey(Thread.currentThread().getName());
		reportData.setDataValue(Long.toString(endTime-startTime));
		return reportData;
	}

}
