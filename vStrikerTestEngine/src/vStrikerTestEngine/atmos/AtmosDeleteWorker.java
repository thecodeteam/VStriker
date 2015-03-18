package vStrikerTestEngine.atmos;

import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;

import vStrikerEntities.Api;
import vStrikerEntities.ExecutionReportData;

import com.emc.vipr.atmos.atmosapi;

//@author Sanjeev Chauhan

public class AtmosDeleteWorker implements Callable<ExecutionReportData> {
	
	private String objectLocation;
	private Api api;
	ExecutionReportData reportData = new ExecutionReportData();
	
	/*
	 * Default Constructor
	 */
	public AtmosDeleteWorker(){
	}
	
	/*
	 * Constructor
	 */
	public AtmosDeleteWorker(String objectLocation, Api api) {
		this.api = api;
		this.objectLocation = objectLocation;
	}
	
	public ExecutionReportData call() throws Exception {

		reportData.setDataKey("Atmos");
		reportData.setCrudValue("Delete");
		try {

			long deltime=atmosapi.DeleteObject(api.getSubtenant(), api.getSecretKey(),
					api.getUrl(), FilenameUtils.getName(objectLocation), null);

			reportData.setThreadValue(Thread.currentThread().getName());
			reportData.setDataValue(Long.toString((deltime) / 1000000));
		} catch (Exception e) {
			reportData.setDataValue(e.getMessage());
		}
		return reportData;
	}
}
