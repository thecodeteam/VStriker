package  com.emccode.vstriker.controller;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import vStrikerEntities.Account;
import vStrikerEntities.ExecutionReportData;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class ResultsChartController {
	@FXML
	private Button btnBack;
	private VStriker vStriker;
	@FXML private TabPane tabCharts;
	@FXML private Tab tabCreate;
	@FXML private Tab tabUpdate;
	@FXML private Tab tabDelete;
	@FXML private Tab tabRead;
	
	@FXML private LineChart<String, Number> chartCreate;
	@FXML private LineChart<String, Number> chartUpdate;
	@FXML private LineChart<String, Number> chartDelete;
	@FXML private LineChart<String, Number> chartRead;
	
	List<String> apiSeries;
	
     private List<vStrikerEntities.ExecutionReportData> execRptData;
     private List<String> apiList;
     private List<String> apiDataList;
	// Constructor
	public ResultsChartController() {
	}
	
	// Set the main application
	public void setVStrikerApp(VStriker vStriker, List<vStrikerEntities.ExecutionReportData>  rptData) {
		this.vStriker = vStriker;
		this.execRptData=rptData;
		this.apiList=vStrikerBizModel.ExecutionReportDataBiz.ExecutionReportDataGetApiList(this.execRptData);
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In ConfigurationController initialize");
		tabCharts.getSelectionModel().selectedItemProperty().addListener(
			    new ChangeListener<Tab>() {
			        @Override
			        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
			           if(t1.getId().equals("tabCreate"))
			           {
			        	   ProcesCreateChart();
			           }
			           
			           if(t1.getId().equals("tabUpdate"))
			           {
			        	   ProcessUpdateChart();
			           }
			      
			           if(t1.getId().equals("tabDelete"))
			           {
			        	   ProcessDeleteChart();
			           }
			          
			         
			           if(t1.getId().equals("tabRead"))
			           {
			        	   ProcessReadChart();
			           }
			          
			           
			        }
			    }
			);
	}

	
	private void ProcesChart(String crud,LineChart<String, Number> chart)
	{
	
	   
		 chart.setTitle(crud+" Operation Summary");
		 //chart.setCreateSymbols(false);                                
	        
	        
		for(String api:apiList)
		{
			apiDataList=vStrikerBizModel.ExecutionReportDataBiz.ExecutionReportDataGetApiCrudList(execRptData, api, crud);
			@SuppressWarnings("rawtypes")
			XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();	
			series.setName(api);
			long x=0;
			long y=0;
			for(String d:apiDataList)
			{
				x++;
				try
				{
				y=Long.parseLong(d);
				series.getData().add(new XYChart.Data<String, Number>(""+x,y));
				}
				catch(Exception e)
				{
					System.out.println(d);
					e.printStackTrace();
				}
				
				
			}
			chart.getData().add(series);
			
		}
	}
	private void ProcesCreateChart()
	{
		 this.ProcesChart("CREATE", chartCreate);
	}
	private void ProcessUpdateChart()
	{
		 this.ProcesChart("UPDATE", chartUpdate);
	}
	private void ProcessDeleteChart()
	{
		 this.ProcesChart("DELETE", chartDelete);
	}
	private void ProcessReadChart()
	{
		 this.ProcesChart("READ", chartRead);
	}

	
	
	
		@FXML
	public void btnBackResultClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showHome();
	}

}
