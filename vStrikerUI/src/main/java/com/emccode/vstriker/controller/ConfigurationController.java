package  com.emccode.vstriker.controller;
import java.awt.Frame;
import java.io.IOException;
import java.net.URL;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import vStrikerEntities.*;
import vStrikerBizModel.*;

import java.util.List;

import javax.swing.JOptionPane;

import com.emccode.vstriker.VStriker;
import com.emccode.vstriker.model.TestInfo;

//@author Sanjeev Chauhan

public class ConfigurationController {
	   @FXML private Button btnUpdate;
	   @FXML private Button btnSave;
	   @FXML private Button btnBack;
	   @FXML private AnchorPane paneCfg;
	   @FXML private CheckBox cbTemplateOnly;

       @FXML private Label txtObjSize;
       @FXML private Label txtNumOps;
       @FXML private Label txtNumTh;
       @FXML private Label txtNumofRetires;
       @FXML private Label ddObjUnit;
       @FXML private ComboBox<TestInfo> ddTestList;
       @FXML private CheckBox ckRandom;
       @FXML private CheckBox cbObjCreate;
       @FXML private Label txtPrctCreate;
       @FXML private CheckBox cbObjRead;
       @FXML private Label txtPrctRead;
       @FXML private Label bObjUpdate;
       @FXML private Label txtPrctUpdate;
       @FXML private CheckBox cbObjDelete;
       @FXML private CheckBox cbObjUpdate;
       @FXML private Label txtPrctDelete;
	   @FXML private Pane paneRandom;
	   @FXML private Label lblTotal;   
       @FXML private Label txtMinObjSize;
       @FXML private Label txtMaxObjSize;
       @FXML private Label txtPrctSmallSize;
       @FXML private Label txtPrctMedSize;
       @FXML private Label txtPrctLargeSize;
       @FXML private CheckBox cbS3Http;
       @FXML private CheckBox cbS3Https;
       @FXML private CheckBox cbSwiftHttp;
       @FXML private CheckBox cbAtmosHttp;
       @FXML private CheckBox cbAtmosHttps;
       @FXML private CheckBox cbSwiftHttps;

       private final ObservableList<TestInfo> testlist = FXCollections.observableArrayList();
	
	
	private TestInfo selectTest;
	private TestConfiguration testcfg;
	private ConfigurationTemplate cfgtemp;
	
	private VStriker vStriker;

	// Constructor
	public ConfigurationController() {
		
	}
	
	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;
		this.btnUpdate.setVisible(false);
		this.ClearForm();
		this.LoadTestsList();
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In ConfigurationController initialize");
		this.cbTemplateOnly.setSelected(false);
		
	}

	@FXML
	public void btnSaveClicked(ActionEvent event) {
		System.out.println("Insert button clicked");
		try {
			// Change page title
			// Change page title
			if(selectTest!=null)
			{
				if(selectTest.getIsTemplate())
				{
					vStriker.showEditConfiguration(cfgtemp,null,-1);
				}
				else
				{
					vStriker.showEditConfiguration(null,testcfg,-1);
				}
			}
			else
				vStriker.showEditConfiguration(null,null,-1);

			
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	@FXML
	public void btnUpdateClicked(ActionEvent event) {
		System.out.println("Update button clicked");
		try {
			// Change page title
			if(!selectTest.getIsTemplate())
			{
			
				vStriker.showEditConfiguration(null,testcfg,testcfg.getTestconfigurationId());
			}
			
				
				
				
			
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	@FXML
	public void btnBackClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		System.out.println(paneCfg.getParent().getParent().getId());
		TabPane main = (TabPane)paneCfg.getParent().getParent();
		SingleSelectionModel<Tab> tb = main.getSelectionModel();
		tb.select(2);
	}
	@FXML
	public void OnRandomClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
	}	
	@FXML
	public  void oncbTemplateOnlyClick(ActionEvent event)
	{
		LoadTestsList();
		this.ClearForm();
	}
	@FXML
	public void ClearForm()
	{
		this.ddObjUnit.setText("");
	       this.txtNumOps.setText("");
	       this.txtNumTh.setText("");
	       this.txtNumofRetires.setText("");
	       this.txtObjSize.setText("");
	       this.ckRandom.setSelected(false);
	       this.cbObjCreate.setSelected(false);
	       this.cbObjDelete.setSelected(false);
	       this.cbObjUpdate.setSelected(false);
	       this.cbObjRead.setSelected(false);
	       this.txtPrctCreate.setText("");
	       this.txtPrctRead.setText("");
	       this.txtPrctUpdate.setText("");
	       this.txtPrctDelete.setText("");
		   this.lblTotal.setText("");;
		   this.cbS3Http.setSelected(false);	
		   this.cbS3Https.setSelected(false);
		   this.cbAtmosHttp.setSelected(false);
		   this.cbAtmosHttps.setSelected(false);
		   this.cbSwiftHttp.setSelected(false);
		   this.cbSwiftHttps.setSelected(false);
		   this.btnUpdate.setVisible(false);
		   
		
	}
	public void LoadTestsList()
	{
		
		this.ddTestList.getItems().clear();
		this.ddTestList.setValue(null);
		
		testlist.clear();
		
		
			try {
				List<ConfigurationTemplate> template=vStrikerBizModel.ConfigurationTemplateBiz.ConfigurationTemplateSelectAll();
				for(ConfigurationTemplate t :template)
				{
					testlist.add( new TestInfo(t.getConfTempName(), t.getConfigurationTemplateId(), true));
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!cbTemplateOnly.isSelected())	
			try {
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
			 ddTestList.getSelectionModel(); // Select first as default
			 ddTestList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TestInfo>() {
			      @Override
		            public void changed(ObservableValue<? extends TestInfo> arg0, TestInfo arg1, TestInfo arg2) {
		                if (arg2 != null) {
		                    System.out.println("Selected Test: " + arg2.getName());
		                 // load data and display
		                    selectTest=arg2;
		                    LoadTestConfiguration(arg2.geTestID(),arg2.getIsTemplate());
		                }
		            }
			 });	 
		   }
			
	   
		
	}
	
	private void LoadTestConfiguration(long id, boolean template)
	{
		try
		{
			this.ClearForm();
				if(template)
				{
					cfgtemp= ConfigurationTemplateBiz.ConfigurationTemplateSelect(id);
					
				       this.ddObjUnit.setText(String.valueOf(cfgtemp.getObjectSizeReportUnit1().getReportUnitName()));
				       this.txtNumOps.setText(String.valueOf(cfgtemp.getConfTempNumberOfOperations()));
				       
				       this.txtNumTh.setText(String.valueOf(cfgtemp.getConfTempNumberOfThreads()));
				       
				       this.txtNumofRetires.setText(String.valueOf(cfgtemp.getConfTempNumberOfRetry()));;
				       this.txtObjSize.setText(String.valueOf(cfgtemp.getConfTempObjectSize()));
				       
				       this.ckRandom.setSelected(cfgtemp.getConfTempEnableRandSizeObject());
				       
				       this.cbObjCreate.setSelected(cfgtemp.getConfTempCreateOperation());
				       this.cbObjDelete.setSelected(cfgtemp.getConfTempDeleteOperation());
				       this.cbObjUpdate.setSelected(cfgtemp.getConfTempUpdateOperation());
				       this.cbObjRead.setSelected(cfgtemp.getConfTempReadOperation());
				       
				       this.txtPrctCreate.setText(String.valueOf(cfgtemp.getConfTempCreatePercent()));;
				       this.txtPrctRead.setText(String.valueOf(cfgtemp.getConfTempReadPercent()));;
				       this.txtPrctUpdate.setText(String.valueOf(cfgtemp.getConfTempUpdatePercent()));;
				       this.txtPrctDelete.setText(String.valueOf(cfgtemp.getConfTempDeletePercent()));;
					   this.lblTotal.setText("100%");


				       if(ckRandom.isSelected())
				       {
					  
				    	   this.paneRandom.setVisible(true);
					       this.txtMinObjSize.setText(String.valueOf(cfgtemp.getConfTempMinRandSizeObject()));;
					       this.txtMaxObjSize.setText(String.valueOf(cfgtemp.getConfTempMaxRandSizeObject()));;
					       this.txtPrctSmallSize.setText(String.valueOf(cfgtemp.getConfTempSmallSizePercent()));;
					       this.txtPrctMedSize.setText(String.valueOf(cfgtemp.getConfTempMediumSizePercent()));;
					       this.txtPrctLargeSize.setText(String.valueOf(cfgtemp.getConfTempLargeSizePercent()));;
				       }

					 List<vStrikerEntities.VwApiSelectedDetail> apiSelList =vStrikerBizModel.ApiSelectedBiz.ApiSelectedSelectByConfTempID(id);
					 for(vStrikerEntities.VwApiSelectedDetail a: apiSelList)
					 {
						  if((a.getApiTypeName().equalsIgnoreCase("S3")) && (a.getApiTypeUrl().equalsIgnoreCase("http"))) this.cbS3Http.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("S3")) && (a.getApiTypeUrl().equalsIgnoreCase("https"))) this.cbS3Https.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("Swift")) && (a.getApiTypeUrl().equalsIgnoreCase("http"))) this.cbSwiftHttp.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("Swift")) && (a.getApiTypeUrl().equalsIgnoreCase("https"))) this.cbSwiftHttps.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("Atmos")) && (a.getApiTypeUrl().equalsIgnoreCase("http"))) this.cbAtmosHttp.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("Atmos")) && (a.getApiTypeUrl().equalsIgnoreCase("https"))) this.cbAtmosHttps.setSelected(true);
					 }
				}
				else
				{
					testcfg = TestConfigurationBiz.TestConfigurationSelect(id);
					
				       this.ddObjUnit.setText(String.valueOf(testcfg.getObjectSizeReportUnit().getReportUnitName()));
				       this.txtNumOps.setText(String.valueOf(testcfg.getNumberOfOperations()));
				       
				       this.txtNumTh.setText(String.valueOf(testcfg.getNumberOfThreads()));
				       
				       this.txtNumofRetires.setText(String.valueOf(testcfg.getNumberOfRetry()));;
				       this.txtObjSize.setText(String.valueOf(testcfg.getObjectSize()));
				       
				       this.ckRandom.setSelected(testcfg.getEnableRandSizeObject());
				       
				       this.cbObjCreate.setSelected(testcfg.getCreateOperation());
				       this.cbObjDelete.setSelected(testcfg.getDeleteOperation());
				       this.cbObjUpdate.setSelected(testcfg.getUpdateOperation());
				       this.cbObjRead.setSelected(testcfg.getReadOperation());
				       
				       this.txtPrctCreate.setText(String.valueOf(testcfg.getCreatePercent()));;
				       this.txtPrctRead.setText(String.valueOf(testcfg.getReadPercent()));;
				       this.txtPrctUpdate.setText(String.valueOf(testcfg.getUpdatePercent()));;
				       this.txtPrctDelete.setText(String.valueOf(testcfg.getDeletePercent()));;
					   this.lblTotal.setText("100%");


				       if(ckRandom.isSelected())
				       {
					  
				    	   this.paneRandom.setVisible(true);
					       this.txtMinObjSize.setText(String.valueOf(testcfg.getMinRandSizeObject()));;
					       this.txtMaxObjSize.setText(String.valueOf(testcfg.getMaxRanDsizeObject()));;
					       this.txtPrctSmallSize.setText(String.valueOf(testcfg.getSmallSizePercent()));;
					       this.txtPrctMedSize.setText(String.valueOf(testcfg.getMediumSizePercent()));;
					       this.txtPrctLargeSize.setText(String.valueOf(testcfg.getLargeSizePercent()));;
				       }

					 List<vStrikerEntities.VwApiSelectedDetail> apiSelList =vStrikerBizModel.ApiSelectedBiz.ApiSelectedSelectByTestID(id);
					 for(vStrikerEntities.VwApiSelectedDetail a: apiSelList)
					 {
						  if((a.getApiTypeName().equalsIgnoreCase("S3")) && (a.getApiTypeUrl().equalsIgnoreCase("http"))) this.cbS3Http.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("S3")) && (a.getApiTypeUrl().equalsIgnoreCase("https"))) this.cbS3Https.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("Swift")) && (a.getApiTypeUrl().equalsIgnoreCase("http"))) this.cbSwiftHttp.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("Swift")) && (a.getApiTypeUrl().equalsIgnoreCase("https"))) this.cbSwiftHttps.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("Atmos")) && (a.getApiTypeUrl().equalsIgnoreCase("http"))) this.cbAtmosHttp.setSelected(true);
						  if((a.getApiTypeName().equalsIgnoreCase("Atmos")) && (a.getApiTypeUrl().equalsIgnoreCase("https"))) this.cbAtmosHttps.setSelected(true);
					 }
					 this.btnUpdate.setVisible(true);
				}
			
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
