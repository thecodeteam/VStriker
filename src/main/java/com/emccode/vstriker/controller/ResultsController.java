package com.emccode.vstriker.controller;

import java.security.Timestamp;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

import vStrikerEntities.Account;
import vStrikerEntities.ConfigurationTemplate;
import vStrikerEntities.ExecutionPlan;
import vStrikerEntities.ExecutionReport;
import vStrikerEntities.ExecutionReportData;
import vStrikerEntities.TestConfiguration;
import vStrikerTestEngine.Engine;
import vStrikerTestEngine.VEngine;

import com.emccode.vstriker.VStriker;
import com.emccode.vstriker.model.TestInfo;

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

	@FXML
	private TitledPane panExecuateResult;
	@FXML
	private HBox hboxProgress;
	@FXML
	private ComboBox<vStrikerEntities.Account> ddAccounts;
	@FXML
	private ComboBox<TestInfo> ddTestList;
	@FXML
	private Button btnRun;
	@FXML
	private ProgressBar progressbarTest;
	@FXML
	private Label lblfinished;

	@FXML
	private TableView<vStrikerEntities.ExecutionReportData> listTestResult;
	@FXML
	private TableColumn<vStrikerEntities.ExecutionReportData, String> apiCol;
	@FXML
	private TableColumn<vStrikerEntities.ExecutionReportData, String> crudCol;
	@FXML
	private TableColumn<vStrikerEntities.ExecutionReportData, String> valueCol;

	private final ObservableList<TestInfo> testlist = FXCollections
			.observableArrayList();
	private final ObservableList<vStrikerEntities.Account> accountlist = FXCollections
			.observableArrayList();

	private VStriker vStriker;
	private Timeline timeline;
	private Timeline timeRunEngine;
	private ExecutionPlan exePlan = new ExecutionPlan();
	private ExecutionReport exeReport = new ExecutionReport();
	private List<ExecutionReportData> data;
	private String selectedTest = "";
	private String selectedAccount = "";

	double count = 0;

	// Constructor
	public ResultsController() {
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;

	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In Result initialize");

	}

	@FXML
	public void btnChartClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showCharts(data, selectedAccount, selectedTest);
	}

	@FXML
	public void btnExportClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
			Date curDate = new Date();
			String strDate = sdf.format(curDate);
			String filename = "Execuation_report_"+ strDate + ".csv";
			System.out.println(filename);
			vStrikerTestUtilities.Utilites.exportResultToFile(filename,
					exeReport.getExecutionReportId());

			vStriker.postStatus("Result is exported to file "+filename);
			/*JOptionPane.showConfirmDialog(null,
					"Export is finished successfully!", "VStriker",
					JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void btnHomeClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");

		System.out.println(paneRslt.getParent().getParent().getId());
		TabPane main = (TabPane) paneRslt.getParent().getParent();
		SingleSelectionModel<Tab> tb = main.getSelectionModel();
		tb.select(0);
	}

	@FXML
	public void onExecuteRun(ActionEvent event) {
		try {
			if ((ddTestList.getValue() != null)
					&& (ddAccounts.getValue() != null)) {
				System.out.println("Account selected: " + ddAccounts.getValue().toString());
				System.out.println("Test selected: " + ddTestList.getValue().toString());
				btnRun.setDisable(true);
				lblfinished.setText("");
				vStriker.postStatus("Test is started....");
				//hboxProgress.setVisible(true);
				//progressbarTest.setProgress(0);
				// Progress Bar
				count = 0;
				timeRunEngine = new Timeline(new KeyFrame(
						Duration.millis(2000), ae -> RunEngine()));
				timeRunEngine.play();

				/*
				* We need to fix this part to implement better way to update the progress.
				* Maybe using Task or Platform
				* */
				/*timeline = new Timeline(new KeyFrame(Duration.millis(100),
						ae -> CheckProgress()));
				timeline.setCycleCount(Animation.INDEFINITE);
				timeline.play();*/
			} else {

				JOptionPane.showConfirmDialog(null,
						"Both Account and Test are required!", "VStriker",
						JOptionPane.CLOSED_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void CheckProgress() {
		if (lblfinished.getText().equals("Completed!")) {
			progressbarTest.setProgress(1);
			this.btnRun.setVisible(true);
			timeline.stop();
			timeRunEngine.stop();
			/*
			JOptionPane.showConfirmDialog(null, "Test executed successfully!",
					"VStriker", JOptionPane.CLOSED_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
					*/
			DisplayResult();

		} else {
			if (count < 200) {
				count++;
				progressbarTest.setProgress(count / 200);

			} else
				count = 150;
		}

	}

	public void RunEngine() {

		try {
			// Save Plan
			exePlan.setAccount(ddAccounts.getValue());

			TestInfo t = ddTestList.getValue();
			this.selectedTest = t.getName();
			this.selectedAccount = ddAccounts.getValue().getName();
			if (t.getIsTemplate()) {
				ConfigurationTemplate cfg = vStrikerBizModel.ConfigurationTemplateBiz
						.ConfigurationTemplateSelect(t.geTestID());
				exePlan.setConfigurationTemplate(cfg);
			} else {
				TestConfiguration test = vStrikerBizModel.TestConfigurationBiz
						.TestConfigurationSelect(t.geTestID());
				exePlan.setTestConfiguration(test);
			}

			vStrikerBizModel.ExecutionPlanBiz.ExecutionPlanCreate(exePlan);

			// run Engine


			Engine excEngine = new VEngine();
			exeReport = excEngine.runTests(exePlan);
			// Thread.sleep(5000);

			//lblfinished.setText("Completed!");
			vStriker.postStatus("Test is completed..");
			btnRun.setDisable(false);
			DisplayResult();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void DisplayResult() {
		try {
//			timeline.stop();

			this.panExecuateResult.setVisible(true);

			exeReport = vStrikerBizModel.ExecutionReportBiz
					.ExecutionReportSelect(exeReport.getExecutionReportId());

			data = exeReport.getExecutionReportData();

			apiCol.setCellValueFactory(new PropertyValueFactory<ExecutionReportData, String>(
					"dataKey"));
			valueCol.setCellValueFactory(new PropertyValueFactory<ExecutionReportData, String>(
					"dataValue"));
			crudCol.setCellValueFactory(new PropertyValueFactory<ExecutionReportData, String>(
					"crudValue"));

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
		this.btnRun.setVisible(true);
	}

	private void LoadAccountsList() {

		this.ddAccounts.getItems().clear();
		accountlist.clear();

		try {
			List<Account> acct = vStrikerBizModel.AccountBiz.AccountSelectAll();
			for (Account t : acct) {
				accountlist.add(t);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!accountlist.isEmpty()) {
			this.ddAccounts.getItems().addAll(accountlist);
		}

	}

	public void LoadTestsList() {

		this.ddTestList.getItems().clear();
		testlist.clear();

		try {
			List<ConfigurationTemplate> template = vStrikerBizModel.ConfigurationTemplateBiz
					.ConfigurationTemplateSelectAll();
			for (ConfigurationTemplate t : template) {
				testlist.add(new TestInfo(t.getConfTempName(), t
						.getConfigurationTemplateId(), true));

			}

			List<TestConfiguration> test = vStrikerBizModel.TestConfigurationBiz
					.ConfigurationTestSelectAll();
			for (TestConfiguration t : test) {
				testlist.add(new TestInfo(t.getTestConfigName(), t
						.getTestconfigurationId(), false));

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!testlist.isEmpty()) {
			this.ddTestList.getItems().addAll(testlist);
		}
	}

}
