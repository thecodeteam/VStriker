package vStrikerTestEngine.atmos;

import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionReportData;

import com.emc.vipr.atmos.atmosapi;

//@author Sanjeev Chauhan

public class AtmosReadWorker implements Callable<ExecutionReportData> {
	private String objectLocation;
	private Api api;
	ExecutionReportData reportData = new ExecutionReportData();
	
	/*
	 * Default Constructor
	 */
	public AtmosReadWorker(){
	}
	
	/*
	 * Constructor
	 */
	public AtmosReadWorker(String objectLocation, Api api) {
		this.api = api;
		this.objectLocation = objectLocation;
	}
	
	public ExecutionReportData call() throws Exception {

		reportData.setDataKey("Atmos");
		reportData.setCrudValue("Read");
		try {
			long startTime = System.nanoTime();
			atmosapi.ReadStringObject(api.getSubtenant(), api.getSecretKey(),
					api.getUrl(), FilenameUtils.getName(objectLocation), null);
			long endTime = System.nanoTime();
			reportData.setThreadValue(Thread.currentThread().getName());
			reportData.setDataValue(Long.toString((endTime - startTime) / 1000000));
		} catch (Exception e) {
			reportData.setDataValue(e.getMessage());
		}

	return reportData;
	}
}
