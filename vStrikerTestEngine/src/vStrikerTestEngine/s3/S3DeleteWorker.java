package vStrikerTestEngine.s3;

import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionReportData;

import com.emc.vipr.s3.s3api;

//@author Sanjeev Chauhan

public class S3DeleteWorker implements Callable<ExecutionReportData> {
	private String objectLocation;
	private Api api;
	ExecutionReportData reportData = new ExecutionReportData();

	public S3DeleteWorker(String objectLocation, Api api) {
		this.objectLocation = objectLocation;
		this.api = api;
	}

	@Override
	public ExecutionReportData call() throws Exception {
		// Create object in S3
		long startTime = System.nanoTime();
		s3api.DeleteObject(api.getSubtenant(), api.getSecretKey(),
				api.getUrl(), null, api.getBucket(),
				FilenameUtils.getName(objectLocation));
		long endTime = System.nanoTime();
		// System.out.println("DeleteObject execution time: " + (endTime -
		// startTime));
		reportData.setDataKey("S3");
		reportData.setThreadValue(Thread.currentThread().getName());
		reportData.setCrudValue("Delete");
		reportData.setDataValue(Long.toString((endTime - startTime) / 1000000));
		return reportData;
	}

}
