package com.emccode.vstriker.controller;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import vStrikerEntities.ApiSelected;
import vStrikerEntities.ApiType;
import vStrikerEntities.ConfigurationTemplate;
import vStrikerEntities.ObjectSizeReportUnit;
import vStrikerEntities.TestConfiguration;

import com.emccode.vstriker.VStriker;
import com.emccode.vstriker.model.NumericTextField;

//@author Sanjeev Chauhan

public class EditConfigurationController {

	@FXML
	private Button btnEditSave;
	@FXML
	private Button btnEditBack;
	@FXML
	private TextField txtTestName;
	@FXML
	private TextArea txtTestDesc;
	@FXML
	private NumericTextField txtObjSize;
	@FXML
	private NumericTextField txtNumOps;
	@FXML
	private NumericTextField txtNumTh;
	@FXML
	private NumericTextField txtNumofRetires;
	@FXML
	private ComboBox<ObjectSizeReportUnit> ddObjUnit;
	@FXML
	private CheckBox cbRandom;
	@FXML
	private CheckBox cbObjCreate;
	@FXML
	private NumericTextField txtPrctCreate;
	@FXML
	private CheckBox cbObjRead;
	@FXML
	private NumericTextField txtPrctRead;
	@FXML
	private CheckBox cbObjUpdate;
	@FXML
	private NumericTextField txtPrctUpdate;
	@FXML
	private CheckBox cbObjDelete;
	@FXML
	private NumericTextField txtPrctDelete;
	@FXML
	private Pane paneRandom;
	@FXML
	private NumericTextField txtMinObjSize;
	@FXML
	private NumericTextField txtMaxObjSize;
	@FXML
	private NumericTextField txtPrctSmallSize;
	@FXML
	private NumericTextField txtPrctMedSize;
	@FXML
	private NumericTextField txtPrctLargeSize;
	@FXML
	private CheckBox cbS3Http;
	@FXML
	private CheckBox cbS3Https;
	@FXML
	private CheckBox cbSwiftHttp;
	@FXML
	private CheckBox cbAtmosHttp;
	@FXML
	private CheckBox cbAtmosHttps;
	@FXML
	private CheckBox cbSwiftHttps;
	@FXML
	private Label lblTotal;
	private TestConfiguration testcfg;
	private ConfigurationTemplate cfgtemp;
	private VStriker vStriker;
	private long iOrginalID = -1;

	// Constructor
	public EditConfigurationController() {
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;
		this.LoadObjectUnitReport();
		ClearForm();
	}

	public void seTestEntity(vStrikerEntities.ConfigurationTemplate cfg,
			vStrikerEntities.TestConfiguration test, long l) {
		try {

			this.ClearForm();
			if (test != null || cfg != null) {
				this.testcfg = test;
				this.cfgtemp = cfg;
				this.LoadTestConfiguration();
			}
			this.iOrginalID = l;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void AddTestListener(NumericTextField txt) {
		txt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {

				onCheckTotal();
			}
		});

	}

	public boolean ShowErrorMessage(String msg) {

		// JOptionPane.showConfirmDialog(null, msg, "VStriker",
		// JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
		vStriker.postStatus(msg);
		return false;
	}

	public boolean ValidateRecord() {
		boolean isOK = true;

		if (txtTestName.getText().equals(""))
			isOK = ShowErrorMessage("Test Name is Required!");

		if (this.txtTestDesc.getText().equals(""))
			isOK = ShowErrorMessage("Test Desc is Required!");

		if (this.ddObjUnit.getValue() == null)
			isOK = ShowErrorMessage("Obj Report Unit is Required!");

		if (this.txtNumOps.getText().equals(""))
			isOK = ShowErrorMessage("Num of Opeations is Required!");
		if (this.txtNumTh.getText().equals(""))
			isOK = ShowErrorMessage("Num of Threads is Required!");
		if (this.txtNumofRetires.getText().equals(""))
			isOK = ShowErrorMessage("Num of Retires is Required!");
		if (this.txtObjSize.getText().equals(""))
			isOK = ShowErrorMessage("Test Obj Size  is Required!");
		if (!this.lblTotal.getText().equals("100%"))
			isOK = ShowErrorMessage("Total must be 100%!");

		if (!(this.cbAtmosHttp.isSelected() || this.cbAtmosHttp.isSelected()
				|| this.cbSwiftHttp.isSelected()
				|| this.cbSwiftHttp.isSelected() || this.cbS3Http.isSelected() || this.cbS3Http
					.isSelected()))
			isOK = ShowErrorMessage("One API at least is required!");

		return isOK;
	}

	public void LoadObjectUnitReport() {

		try {

			ObservableList<vStrikerEntities.ObjectSizeReportUnit> unit = vStrikerBizModel.ObjectSizeReportUnitBiz
					.ObjectSizeReportUnitSelectAll();

			this.ddObjUnit.getItems().clear();
			this.ddObjUnit.getItems().addAll(unit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In Edit Configuration Controller initialize");
		AddTestListener(txtPrctCreate);
		AddTestListener(txtPrctRead);
		AddTestListener(txtPrctUpdate);
		AddTestListener(txtPrctDelete);
	}

	@FXML
	public void btnEditSaveClicked(ActionEvent event) {
		System.out.println(" Save button clicked");
		if (ValidateRecord()) {
			if (iOrginalID == -1)
				SaveTest();
			else
				UpdateTest();
		}
	}

	public void SaveTest() {
		try {
			// Create Test Configuration
			PrepareTestEntity();
			vStrikerBizModel.TestConfigurationBiz
					.TestConfigurationCreate(testcfg);

			System.out.println("New Cfg Test Id "
					+ testcfg.getTestconfigurationId());

			// Create Select API
			List<vStrikerEntities.ApiType> apitypelist = vStrikerBizModel.ApiTypeBiz
					.ApiTypeSelectAll();

			for (ApiType a : apitypelist) {
				ApiSelected selected = new ApiSelected();
				selected.setTestConfiguration(testcfg);
				if ((a.getApiTypeName().equalsIgnoreCase("S3"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("http"))
						&& (cbS3Http.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("S3"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("https"))
						&& (cbS3Https.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("http"))
						&& (cbSwiftHttp.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("https"))
						&& (cbSwiftHttps.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("http"))
						&& (cbAtmosHttp.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("https"))
						&& (cbAtmosHttps.isSelected()))
					AddSelectedAPI(a, selected);

				System.out.println("New Cfg Test Id "
						+ selected.getApiSelectedId());
			}

			this.iOrginalID = testcfg.getTestconfigurationId();

			// JOptionPane.showConfirmDialog(null,
			// "Test Configuration was inserted successfully!", "VStriker",
			// JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
			vStriker.postStatus("Test Configuration was inserted successfully!");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// JOptionPane.showConfirmDialog(
			// null,
			// "Error during inserting test configuration data "
			// + e.getMessage(), "VStriker",
			// JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			vStriker.postStatus("Error during inserting test configuration data ");
		}

	}

	public void PrepareTestEntity() throws Exception {
		this.testcfg = new TestConfiguration();
		this.testcfg.setTestConfigName(this.txtTestName.getText());
		this.testcfg.setTestConfigDescription(this.txtTestDesc.getText());
		ObjectSizeReportUnit obj = this.ddObjUnit.getValue();
		this.testcfg.setObjectSizeReportUnit(obj);
		this.testcfg.setNumberOfOperations(this.txtNumOps.getNum() * 100);
		this.testcfg.setNumberOfThreads(this.txtNumTh.getNum());
		this.testcfg.setNumberOfRetry(this.txtNumofRetires.getNum());
		this.testcfg.setObjectSize(this.txtObjSize.getNum());

		this.testcfg.setEnableRandSizeObject(this.cbRandom.isSelected());

		this.testcfg.setCreateOperation(this.cbObjCreate.isSelected());
		this.testcfg.setDeleteOperation(this.cbObjDelete.isSelected());
		this.testcfg.setUpdateOperation(this.cbObjUpdate.isSelected());
		this.testcfg.setReadOperation(this.cbObjRead.isSelected());

		this.testcfg.setCreatePercent(this.txtPrctCreate.getNum());
		this.testcfg.setReadPercent(this.txtPrctRead.getNum());
		this.testcfg.setUpdatePercent(this.txtPrctUpdate.getNum());
		this.testcfg.setDeletePercent(this.txtPrctDelete.getNum());

		if (cbRandom.isSelected()) {

			testcfg.setMinRandSizeObject(this.txtMinObjSize.getNum());
			;
			testcfg.setMaxRanDsizeObject(this.txtMaxObjSize.getNum());
			;
			testcfg.setSmallSizePercent(this.txtPrctSmallSize.getNum());
			;
			testcfg.setMediumSizePercent(this.txtPrctMedSize.getNum());
			;
			testcfg.setLargeSizePercent(this.txtPrctLargeSize.getNum());
			;
		}

	}

	public void UpdateTest() {
		System.out.println("Back to Edit button clicked");
		try {

			// Prepare Test Class
			PrepareTestEntity();

			// Set ID
			testcfg.setTestconfigurationId(iOrginalID);
			vStrikerBizModel.TestConfigurationBiz
					.TestConfigurationUpdate(testcfg);
			System.out.println("New Cfg Test Id "
					+ testcfg.getTestconfigurationId());

			// Clean up old Selected API
			List<vStrikerEntities.VwApiSelectedDetail> apiSelList = vStrikerBizModel.ApiSelectedBiz
					.ApiSelectedSelectByTestID(testcfg.getTestconfigurationId());
			for (vStrikerEntities.VwApiSelectedDetail a : apiSelList) {
				vStrikerBizModel.ApiSelectedBiz.ApiSelectDelete(a
						.getApiSelectedId());
			}

			// Create Updated API
			List<vStrikerEntities.ApiType> apitypelist = vStrikerBizModel.ApiTypeBiz
					.ApiTypeSelectAll();
			for (ApiType a : apitypelist) {
				ApiSelected selected = new ApiSelected();
				selected.setTestConfiguration(testcfg);

				if ((a.getApiTypeName().equalsIgnoreCase("S3"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("http"))
						&& (cbS3Http.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("S3"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("https"))
						&& (cbS3Https.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("http"))
						&& (cbSwiftHttp.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("https"))
						&& (cbSwiftHttps.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("http"))
						&& (cbAtmosHttp.isSelected()))
					AddSelectedAPI(a, selected);
				if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("https"))
						&& (cbAtmosHttps.isSelected()))
					AddSelectedAPI(a, selected);

				System.out.println("New Cfg Test Id "
						+ selected.getApiSelectedId());
			}

			// JOptionPane.showConfirmDialog(null,
			// "Test Configuration was updated successfully!", "VStriker",
			// JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
			vStriker.postStatus("Test Configuration was updated successfully!");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// JOptionPane.showConfirmDialog(
			// null,
			// "Error during updating test configuration data "
			// + e.getMessage(), "VStriker",
			// JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
			vStriker.postStatus("Error during updating test configuration data ");
		}
	}

	public void AddSelectedAPI(ApiType a, ApiSelected s) {

		try {
			s.setApiType(a);
			vStrikerBizModel.ApiSelectedBiz.ApiSelectCreate(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void btnEditBackClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showConfiguration();
	}

	public void ClearForm() {
		// this.btnEditUpdate.setVisible(false);
		this.txtNumOps.setText("0");
		this.txtNumTh.setText("0");
		this.txtNumofRetires.setText("0");
		this.txtObjSize.setText("0");
		this.cbRandom.setSelected(false);
		this.cbObjCreate.setSelected(false);
		this.cbObjDelete.setSelected(false);
		this.cbObjUpdate.setSelected(false);
		this.cbObjRead.setSelected(false);
		this.txtPrctCreate.setText("0");
		this.txtPrctRead.setText("0");
		this.txtPrctUpdate.setText("0");
		this.txtPrctDelete.setText("0");
		this.lblTotal.setText("0");
		;
		this.cbS3Http.setSelected(false);
		this.cbS3Https.setSelected(false);
		this.cbAtmosHttp.setSelected(false);
		this.cbAtmosHttps.setSelected(false);
		this.cbSwiftHttp.setSelected(false);
		this.cbSwiftHttps.setSelected(false);

		this.txtPrctCreate.setDisable(true);
		this.txtPrctRead.setDisable(true);
		this.txtPrctUpdate.setDisable(true);
		this.txtPrctDelete.setDisable(true);

		;

		// v1 random object is hidden
		this.cbRandom.setVisible(false);
		this.paneRandom.setVisible(false);

	}

	@FXML
	private void onCheckTotal() {
		long c = 0;
		long r = 0;
		long d = 0;
		long u = 0;

		try {
			c = Long.parseLong(txtPrctCreate.getText());
		} catch (Exception e) {
		}
		;
		try {
			r = Long.parseLong(txtPrctRead.getText());
		} catch (Exception e) {
		}
		;
		try {
			d = Long.parseLong(txtPrctDelete.getText());
		} catch (Exception e) {
		}
		;
		try {
			u = Long.parseLong(txtPrctUpdate.getText());
		} catch (Exception e) {
		}
		;

		long t = c + r + d + u;
		lblTotal.setText(t + "%");

		if (t != 100) {
			lblTotal.setTextFill(Color.RED);

		}
	}

	@FXML
	private void SetCRUDText(ActionEvent event) {
		// Check Create Object
		if (cbObjCreate.isSelected()) {
			txtPrctCreate.setDisable(false);
		} else {
			txtPrctCreate.setDisable(true);
			txtPrctCreate.setText("0");
		}
		// Check Read Object
		if (cbObjRead.isSelected()) {
			txtPrctRead.setDisable(false);
		} else {
			txtPrctRead.setDisable(true);
			txtPrctRead.setText("0");
		}

		// Delete
		if (cbObjDelete.isSelected()) {
			txtPrctDelete.setDisable(false);
		} else {
			txtPrctDelete.setDisable(true);
			txtPrctDelete.setText("0");
		}

		// Update
		if (cbObjUpdate.isSelected()) {
			txtPrctUpdate.setDisable(false);
		} else {
			txtPrctUpdate.setDisable(true);
			txtPrctUpdate.setText("0");
		}

		onCheckTotal();
	}

	private void LoadTestConfiguration() {
		try {
			this.ClearForm();
			if (cfgtemp != null) {

				this.txtTestName.setText(cfgtemp.getConfTempName());
				this.txtTestDesc.setText(cfgtemp.getConfTempDescription());

				this.ddObjUnit.setValue(cfgtemp.getObjectSizeReportUnit1());
				this.txtNumOps.setText(String.valueOf(cfgtemp
						.getConfTempNumberOfOperations()/100));

				this.txtNumTh.setText(String.valueOf(cfgtemp
						.getConfTempNumberOfThreads()));

				this.txtNumofRetires.setText(String.valueOf(cfgtemp
						.getConfTempNumberOfRetry()));
				;
				this.txtObjSize.setText(String.valueOf(cfgtemp
						.getConfTempObjectSize()));

				this.cbRandom.setSelected(cfgtemp
						.getConfTempEnableRandSizeObject());

				this.cbObjCreate.setSelected(cfgtemp
						.getConfTempCreateOperation());
				this.cbObjDelete.setSelected(cfgtemp
						.getConfTempDeleteOperation());
				this.cbObjUpdate.setSelected(cfgtemp
						.getConfTempUpdateOperation());
				this.cbObjRead.setSelected(cfgtemp.getConfTempReadOperation());

				if (this.cbObjCreate.isSelected()) {
					this.txtPrctCreate.setText(String.valueOf(cfgtemp
							.getConfTempCreatePercent()));
					this.txtPrctCreate.setDisable(false);
				}
				if (this.cbObjRead.isSelected()) {
					this.txtPrctRead.setText(String.valueOf(cfgtemp
							.getConfTempReadPercent()));
					this.txtPrctRead.setDisable(false);
				}
				if (this.cbObjUpdate.isSelected()) {
					this.txtPrctUpdate.setText(String.valueOf(cfgtemp
							.getConfTempUpdatePercent()));
					this.txtPrctUpdate.setDisable(false);
				}
				if (this.cbObjDelete.isSelected()) {
					this.txtPrctDelete.setText(String.valueOf(cfgtemp
							.getConfTempDeletePercent()));
					this.txtPrctDelete.setDisable(false);
				}

				this.lblTotal.setText("100%");

				if (cbRandom.isSelected()) {

					this.paneRandom.setVisible(true);
					this.txtMinObjSize.setText(String.valueOf(cfgtemp
							.getConfTempMinRandSizeObject()));
					;
					this.txtMaxObjSize.setText(String.valueOf(cfgtemp
							.getConfTempMaxRandSizeObject()));
					;
					this.txtPrctSmallSize.setText(String.valueOf(cfgtemp
							.getConfTempSmallSizePercent()));
					;
					this.txtPrctMedSize.setText(String.valueOf(cfgtemp
							.getConfTempMediumSizePercent()));
					;
					this.txtPrctLargeSize.setText(String.valueOf(cfgtemp
							.getConfTempLargeSizePercent()));
					;
				}

				List<vStrikerEntities.VwApiSelectedDetail> apiSelList = vStrikerBizModel.ApiSelectedBiz
						.ApiSelectedSelectByConfTempID(cfgtemp
								.getConfigurationTemplateId());
				for (vStrikerEntities.VwApiSelectedDetail a : apiSelList) {
					System.out.println(a.getApiTypeName());
					if ((a.getApiTypeName().equalsIgnoreCase("S3"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("http")))
						this.cbS3Http.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("S3"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("https")))
						this.cbS3Https.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("http")))
						this.cbSwiftHttp.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("https")))
						this.cbSwiftHttps.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("http")))
						this.cbAtmosHttp.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("https")))
						this.cbAtmosHttps.setSelected(true);
				}

				iOrginalID = -1;
			}
			if (testcfg != null) {

				this.txtTestName.setText(testcfg.getTestConfigName());
				this.txtTestDesc.setText(testcfg.getTestConfigDescription());

				this.ddObjUnit.setValue(testcfg.getObjectSizeReportUnit());
				this.txtNumOps.setText(String.valueOf(testcfg
						.getNumberOfOperations()/100));

				this.txtNumTh.setText(String.valueOf(testcfg
						.getNumberOfThreads()));

				this.txtNumofRetires.setText(String.valueOf(testcfg
						.getNumberOfRetry()));
				;
				this.txtObjSize
						.setText(String.valueOf(testcfg.getObjectSize()));

				this.cbRandom.setSelected(testcfg.getEnableRandSizeObject());

				this.cbObjCreate.setSelected(testcfg.getCreateOperation());
				this.cbObjDelete.setSelected(testcfg.getDeleteOperation());
				this.cbObjUpdate.setSelected(testcfg.getUpdateOperation());
				this.cbObjRead.setSelected(testcfg.getReadOperation());

				if (this.cbObjCreate.isSelected()) {
					this.txtPrctCreate.setText(String.valueOf(testcfg
							.getCreatePercent()));
					this.txtPrctCreate.setDisable(false);
				}
				if (this.cbObjRead.isSelected()) {
					this.txtPrctRead.setText(String.valueOf(testcfg
							.getReadPercent()));
					this.txtPrctRead.setDisable(false);
				}
				if (this.cbObjUpdate.isSelected()) {
					this.txtPrctUpdate.setText(String.valueOf(testcfg
							.getUpdatePercent()));
					this.txtPrctUpdate.setDisable(false);
				}
				if (this.cbObjDelete.isSelected()) {
					this.txtPrctDelete.setText(String.valueOf(testcfg
							.getDeletePercent()));
					this.txtPrctDelete.setDisable(false);
				}

				this.lblTotal.setText("100%");

				if (cbRandom.isSelected()) {

					this.paneRandom.setVisible(true);
					this.txtMinObjSize.setText(String.valueOf(testcfg
							.getMinRandSizeObject()));
					;
					this.txtMaxObjSize.setText(String.valueOf(testcfg
							.getMaxRanDsizeObject()));
					;
					this.txtPrctSmallSize.setText(String.valueOf(testcfg
							.getSmallSizePercent()));
					;
					this.txtPrctMedSize.setText(String.valueOf(testcfg
							.getMediumSizePercent()));
					;
					this.txtPrctLargeSize.setText(String.valueOf(testcfg
							.getLargeSizePercent()));
					;
				}

				List<vStrikerEntities.VwApiSelectedDetail> apiSelList = vStrikerBizModel.ApiSelectedBiz
						.ApiSelectedSelectByTestID(testcfg
								.getTestconfigurationId());
				for (vStrikerEntities.VwApiSelectedDetail a : apiSelList) {
					if ((a.getApiTypeName().equalsIgnoreCase("S3"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("http")))
						this.cbS3Http.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("S3"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("https")))
						this.cbS3Https.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("http")))
						this.cbSwiftHttp.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("https")))
						this.cbSwiftHttps.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("http")))
						this.cbAtmosHttp.setSelected(true);
					if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("https")))
						this.cbAtmosHttps.setSelected(true);
				}

				this.iOrginalID = testcfg.getTestconfigurationId();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
