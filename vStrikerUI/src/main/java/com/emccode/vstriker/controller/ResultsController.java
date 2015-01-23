package  com.emccode.vstriker.controller;

import java.util.List;

import javax.swing.JOptionPane;

import vStrikerTestEngine.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Duration;
import vStrikerEntities.*;
import javafx.concurrent.Task;
import javafx.event.EventHandler;

import com.emccode.vstriker.VStriker;
import com.emccode.vstriker.model.TestInfo;

import vStrikerTestUtilities.*;

//@author Sanjeev Chauhan

public class ResultsController {
	@FXML
	private Button btnChart;
	@FXML
	private Button btnExport;
	@FXML
	private Button btnHome;
	@FXML
	private AnchorPane paneRslt;

	@FXML private TitledPane panExecuateResult;
	@FXML private HBox hboxProgress;
	@FXML private ComboBox<vStrikerEntities.Account> ddAccounts;
	@FXML private ComboBox<TestInfo> ddTestList;
	@FXML private Button btnRun;
	@FXML private ProgressBar progressbarTest;
	@FXML private Label lblfinished;

	
	@FXML private TableView<vStrikerEntities.ExecutionReportData> listTestResult;
	@FXML private TableColumn<vStrikerEntities.ExecutionReportData, String> apiCol;
	@FXML private TableColumn<vStrikerEntities.ExecutionReportData, String> crudCol;
	@FXML private TableColumn<vStrikerEntities.ExecutionReportData, String> valueCol;
	
	private Task runWorker;
	private final ObservableList<TestInfo> testlist = FXCollections.observableArrayList();
	private final ObservableList<vStrikerEntities.Account> accountlist = FXCollections.observableArrayList();
	
	private VStriker vStriker;
	private Timeline timeline;
	private Timeline timeRunEngine;
	private ExecutionPlan exePlan = new ExecutionPlan();
	private ExecutionReport exeReport = new ExecutionReport();
	
	
	double count=0;

	// Constructor
	public ResultsController() {
	}
	
	// Set the main application
	public void setVStrikerApp(VStriker vStrikert) {
		//this.vStriker = vStriker;

	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In Result initialize");
		
	}

		@FXML
	public void btnChartClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		//vStriker.showHome();
	}
		@FXML
	public void btnExportClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		
		try {
			String filename="Execuation_report_"+exeReport.getExecutionReportId()+".csv";
			vStrikerTestUtilities.Utilites.exportResultToFile(filename, exeReport.getExecutionReportId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		@FXML
	public void btnHomeClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");

		System.out.println(paneRslt.getParent().getParent().getId());
		TabPane main = (TabPane)paneRslt.getParent().getParent();
		SingleSelectionModel<Tab> tb = main.getSelectionModel();
		tb.select(0);
	}
	
		@FXML
	public void onExecuteRun(ActionEvent event)
	{
			try
			{
				        hboxProgress.setVisible(true);
		                btnRun.setDisable(true);
		                progressbarTest.setProgress(0);
		                lblfinished.setText("");
// Progress Bar
		                progressbarTest.setProgress(0);

		                timeRunEngine =  new Timeline(new KeyFrame(
		                        Duration.millis(1000),
		                        ae -> RunEngine()));
		                timeRunEngine.setCycleCount(Animation.INDEFINITE);
		                timeRunEngine.play();
		                
		                timeline = new Timeline(new KeyFrame(
		                        Duration.millis(100),
		                        ae -> CheckProgress()));
		                timeline.setCycleCount(Animation.INDEFINITE);
		                timeline.play();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}
		public void CheckProgress()
		{
			if(lblfinished.getText().equals("Completed!"))
			{
				progressbarTest.setProgress(1);
				timeline.stop();
				JOptionPane.showConfirmDialog(null, "Test executed successfully!",  "VStriker",
    				    JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
				 DisplayResult();
				
			}
			else
			{
				if(count<200)
				{
					count++;
					progressbarTest.setProgress(count/200);
				}
				else count=150;
			}
				
			
		}

		public void RunEngine()
		{

			try
			{
			 // Save Plan
				exePlan.setAccount(ddAccounts.getValue());
				
				TestInfo t = ddTestList.getValue(); 
				if(t.getIsTemplate())
				{
					ConfigurationTemplate cfg = vStrikerBizModel.ConfigurationTemplateBiz.ConfigurationTemplateSelect(t.geTestID());
					exePlan.setConfigurationTemplate(cfg);
				}
				else
				{
					TestConfiguration test = vStrikerBizModel.TestConfigurationBiz.TestConfigurationSelect(t.geTestID());
					exePlan.setTestConfiguration(test);
				}
				
				vStrikerBizModel.ExecutionPlanBiz.ExecutionPlanCreate(exePlan);
				
			// run Engine
				
				//Engine excEngine = new VEngine();
				//exeReport = excEngine.runTests(exePlan);
				Thread.sleep(5000);
				exeReport =vStrikerBizModel.ExecutionReportBiz.ExecutionReportSelect(1);
				lblfinished.setText("Completed!");
					
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

		public void DisplayResult()
		{
		 try
		 {
			 this.panExecuateResult.setVisible(true);
			 List<ExecutionReportData> data = exeReport.getExecutionReportData();
			apiCol.setCellValueFactory(new PropertyValueFactory<ExecutionReportData, String>("dataKey"));
			valueCol.setCellValueFactory(new PropertyValueFactory<ExecutionReportData, String>("dataValue"));
			crudCol.setCellValueFactory(new PropertyValueFactory<ExecutionReportData, String>("crudValue"));
			
			listTestResult.getItems().addAll(data);
			 
			 
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
		}
		 
		public void LoadLists() {
			System.out.println("Load List initialize");
			hboxProgress.setVisible(false);
			panExecuateResult.setVisible(false);
			
			this.LoadAccountsList();
			this.LoadTestsList();
			
		}
		
		private void LoadAccountsList() {
			

			this.ddAccounts.getItems().clear();
			accountlist.clear();
			
				try {
					List<Account> acct=vStrikerBizModel.AccountBiz.AccountSelectAll();
					for(Account t :acct)
					{
						accountlist.add( t);
					}


				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

				if(!accountlist.isEmpty())
				{
				 this.ddAccounts.getItems().addAll(accountlist);
			   }
			
		}

		
		public void LoadTestsList()
		{

			this.ddTestList.getItems().clear();
			testlist.clear();
			
				try {
					List<ConfigurationTemplate> template=vStrikerBizModel.ConfigurationTemplateBiz.ConfigurationTemplateSelectAll();
					for(ConfigurationTemplate t :template)
					{
						testlist.add( new TestInfo(t.getConfTempName(), t.getConfigurationTemplateId(), true));
						
					}

					List<TestConfiguration> test=vStrikerBizModel.TestConfigurationBiz.ConfigurationTestSelectAll();
					for(TestConfiguration t :test)
					{
						testlist.add( new TestInfo(t.getTestConfigName(), t.getTestconfigurationId(), false));
						
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

				if(!testlist.isEmpty())
				{
				 this.ddTestList.getItems().addAll(testlist);
			   }
		}

}

