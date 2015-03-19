package vStrikerTestEngine.atmos;

import java.io.FileInputStream;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionReportData;

import com.emc.vipr.atmos.atmosapi;

//@author Sanjeev Chauhan

public class AtmosUpdateWorker implements Callable<ExecutionReportData> {
	private String objectLocation;
	private Api api;
	ExecutionReportData reportData = new ExecutionReportData();
	
	/*
	 * Default Constructor
	 */
	public AtmosUpdateWorker(){
	}
	
	/*
	 * Constructor
	 */
	public AtmosUpdateWorker(String objectLocation, Api api) {
		this.api = api;
		this.objectLocation = objectLocation;
	}
	
	public ExecutionReportData call() throws Exception {
		reportData.setDataKey("Atmos");
		reportData.setCrudValue("Update");

		try {
			long time = atmosapi.UpdateObject(api.getSubtenant(), api.getSecretKey(),
					api.getUrl(), FilenameUtils.getName(objectLocation),
					new FileInputStream(objectLocation), null);

			reportData.setThreadValue(Thread.currentThread().getName());
			reportData.setDataValue(Long.toString(time / 1000000));
		} catch (Exception e) {
			reportData.setDataValue(e.getMessage());
		}

		return reportData;
	}
}
