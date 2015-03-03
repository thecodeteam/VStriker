package vStrikerTestEngine.swift;

import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionReportData;

import com.emc.vipr.swift.swiftapi;

//@author Sanjeev Chauhan

public class SwiftDeleteWorker implements Callable<ExecutionReportData> {
	
	private String objectLocation;
	private Api api;
	ExecutionReportData reportData = new ExecutionReportData();
	
	/*
	 * Default Constructor
	 */
	public SwiftDeleteWorker(){
	}
	
	/*
	 * Constructor
	 */
	public SwiftDeleteWorker(String objectLocation, Api api) {
		this.api = api;
		this.objectLocation = objectLocation;
	}
	
	public ExecutionReportData call() throws Exception {
		long startTime = System.nanoTime();
		swiftapi.DeleteObject(api.getSubtenant(), api.getSecretKey(),
				api.getUrl(), api.getBucket(), FilenameUtils.getName(objectLocation));
		long endTime = System.nanoTime();
		System.out.println("Swift DeleteObject execution time: " + (endTime - startTime));
		reportData.setDataKey("Swift");
		reportData.setThreadValue(Thread.currentThread().getName());
		reportData.setCrudValue("Delete");
		reportData.setDataValue(Long.toString((endTime-startTime)/1000000));
		return reportData;
	}
}
