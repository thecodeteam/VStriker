package  com.emccode.vstriker.controller;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
	@FXML private LineChart<String, Number> chartAvg;
	
	
	@FXML private Label testName;
	@FXML private Label accountName;
	
	List<String> apiSeries;
	
     private List<vStrikerEntities.ExecutionReportData> execRptData;
     private List<String> apiList;
     private List<String> apiDataList;
 	private String  selectedTest="";
 	private String selectedAccount="";
	// Constructor
	public ResultsChartController() {
	}
	
	// Set the main application
	public void setVStrikerApp(VStriker vStriker, List<vStrikerEntities.ExecutionReportData>  rptData, String selectedAccount,String selectTest) {
		this.vStriker = vStriker;
		this.execRptData=rptData;
		this.apiList=vStrikerBizModel.ExecutionReportDataBiz.ExecutionReportDataGetApiList(this.execRptData);
		this.selectedTest=selectTest;
		this.selectedAccount=selectedAccount;
		
		this.testName.setText(this.selectedTest);
		this.accountName.setText(selectedAccount);
		 ProcessAvgChart();
		
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

			           if(t1.getId().equals("tabAvg"))
			           {
			        	   ProcessAvgChart();
			           }
			          
			           
			        }
			    }
			);
	}

	private XYChart.Series<String, Number> getSeriers(String crud,String api)
	{
		@SuppressWarnings("rawtypes")
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();	
		try
		{
		apiDataList=vStrikerBizModel.ExecutionReportDataBiz.ExecutionReportDataGetApiCrudList(execRptData, api, crud);
		
		
		series.setName(api+"-"+crud);
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return series;
		
	}
	
	private void ProcessAvgChart()
	{
		
		this.chartAvg.setTitle("Avg Operation Summary");
		chartAvg.getData().clear();
		chartAvg.getData().add(getSeriers("CREATE","S3"));
		chartAvg.getData().add(getSeriers("UPDATE","S3"));
		chartAvg.getData().add(getSeriers("DELETE","S3"));
		chartAvg.getData().add(getSeriers("READ","S3"));
		
	}
	private void ProcesChart(String crud,LineChart<String, Number> chart)
	{
	
	   
		 chart.setTitle(crud+" Operation Summary");
		 chart.getData().clear();
		
	        
	        
		for(String api:apiList)
		{

			chart.getData().add(getSeriers(crud,api));
			
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
