package vStrikerTestEngine.atmos;

import java.io.FileInputStream;
import java.util.concurrent.Callable;
import java.io.File;
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
		reportData.setDataKey("Atmos");
		reportData.setCrudValue("Create");
		try {
		long startTime = System.nanoTime();
		atmosapi.CreateObject(api.getSubtenant(), api.getSecretKey(),
				api.getUrl(), FilenameUtils.getName(objectLocation),
				new File(objectLocation), api.getBucket());
		long endTime = System.nanoTime();
		reportData.setThreadValue(Thread.currentThread().getName());
		reportData.setDataValue(Long.toString((endTime-startTime)/1000000));

		} catch (Exception e) {
			reportData.setDataValue(e.getMessage());
		}

		return reportData;
	}

}
