package vStrikerTestEngine.atmos;

import java.io.FileInputStream;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionReportData;

import com.emc.vipr.atmos.atmosapi;

//@author Sanjeev Chauhan

public class AtmosCreateWorker implements Callable<ExecutionReportData> {
	
	private String objectLocation;
	private Api api;
	ExecutionReportData reportData = new ExecutionReportData();
	
	/*
	 * Default Constructor
	 */
	public AtmosCreateWorker(){
	}
	
	/*
	 * Constructor
	 */
	public AtmosCreateWorker(String objectLocation, Api api) {
		this.api = api;
		this.objectLocation = objectLocation;
	}
	
	public ExecutionReportData call() throws Exception {
		long startTime = System.nanoTime();
		atmosapi.CreateObject(api.getSubtenant(), api.getSecretKey(),
				api.getUrl(), FilenameUtils.getName(objectLocation),
				new FileInputStream(objectLocation), null);
		long endTime = System.nanoTime();
		System.out.println("Atmos CreateObject execution time: " + (endTime - startTime));
		reportData.setDataKey("Atmos");
		reportData.setThreadValue(Thread.currentThread().getName());
		reportData.setCrudValue("Create");
		reportData.setDataValue(Long.toString((endTime-startTime)/1000000));
		return reportData;
	}

}
