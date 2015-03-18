package vStrikerTestEngine.swift;

import java.io.FileInputStream;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionReportData;

import com.emc.vipr.swift.swiftapi;

//@author Sanjeev Chauhan

public class SwiftCreateWorker implements Callable<ExecutionReportData> {
	
	private String objectLocation;
	private Api api;
	ExecutionReportData reportData = new ExecutionReportData();
	
	/*
	 * Default Constructor
	 */
	public SwiftCreateWorker(){
	}
	
	/*
	 * Constructor
	 */
	public SwiftCreateWorker(String objectLocation, Api api) {
		this.api = api;
		this.objectLocation = objectLocation;
	}
	
	public ExecutionReportData call() throws Exception {
		reportData.setDataKey("Swift");
		reportData.setCrudValue("Create");
		try{
		long startTime = System.nanoTime();
		swiftapi.CreateObject(api.getSubtenant(), api.getSecretKey(),
				api.getUrl(), api.getBucket(), FilenameUtils.getName(objectLocation),
				new FileInputStream(objectLocation));
		long endTime = System.nanoTime();
		reportData.setThreadValue(Thread.currentThread().getName());
		reportData.setDataValue(Long.toString((endTime-startTime)/1000000));
	} catch (Exception e) {
		reportData.setDataValue(e.getMessage());
	}
		return reportData;
	}
}
