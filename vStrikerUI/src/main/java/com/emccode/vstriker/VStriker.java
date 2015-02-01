package com.emccode.vstriker;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vStrikerEntities.Account;

import com.emccode.vstriker.controller.AccountController;
import com.emccode.vstriker.controller.AtmosController;
import com.emccode.vstriker.controller.EditConfigurationController;
import com.emccode.vstriker.controller.HomepageController;
import com.emccode.vstriker.controller.ResultsChartController;
import com.emccode.vstriker.controller.S3Controller;
import com.emccode.vstriker.controller.SwiftController;
/*
 * @author Sanjeev Chauhan
 */
public class VStriker extends Application {

	private Stage primaryStage;
	private BorderPane vStrikerLayout;

	// Replace with Account from vStrikerEntities package - ToDo
	// private ObservableList<Account> accountData =
	// FXCollections.observableArrayList();

	// Constructor
	public VStriker() {
		// Adding sample data - move to vStrikerLoader package - ToDo
		// accountData.add(new Account("name1", "location1"));
		// accountData.add(new Account("name2", "location2"));
	}

	// Return list of Accounts
	// public ObservableList<Account> getAccountData() {
	// return accountData;
	// }

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("VStriker");

		initVStriker();
		showHome();
	}

	// Initialize the VStriker application
	public void initVStriker() {
		try {
			System.out.println("Initializing the VStriker application");
			// Load the layout from the fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/VStriker.fxml"));
			vStrikerLayout = (BorderPane) loader.load();

			// Show the scene layout
			Scene scene = new Scene(vStrikerLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Show the home page in the application
	public void SetTitle(String msg)
	{
		
		this.primaryStage.setTitle(msg);
	}
	public void showHome() {
		try {
			// Set title
			this.SetTitle("vStriker");
			// Load home layout
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/Home.fxml"));
			AnchorPane homeLayout = (AnchorPane) loader.load();

			// Show the home layout in the center of the application
			vStrikerLayout.setCenter(homeLayout);

			// Give controller access to main app
			HomepageController controller = loader.getController();
			controller.setVStrikerApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showConfiguration() {
		try {
			// Set title
			this.SetTitle("vStriker:Configuration");
			// Load home layout
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/Home.fxml"));
			AnchorPane homeLayout = (AnchorPane) loader.load();

			// Show the home layout in the center of the application
			vStrikerLayout.setCenter(homeLayout);

			// Give controller access to main app
			HomepageController controller = loader.getController();
			controller.setVStrikerApp(this);
			controller.setTab(1);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showAccount() {
		System.out.println("In VStriker showAccount");
		try {
			// Change page title
			this.SetTitle("vStriker:Account");
			// Load home layout
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/Account.fxml"));
			AnchorPane accountLayout = (AnchorPane) loader.load();

			// Show the home layout in the center of the application
			vStrikerLayout.setCenter(accountLayout);

			// Give controller access to main app
			AccountController controller = loader.getController();
			controller.createAccount(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showAddConfiguration() {
		System.out.println("In VStriker ShowEditConfiguaration");
		try {
			// Change page title
			this.SetTitle("vStriker:Add Configuration");
			// Load home layout
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/EditConfiguration.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();

			// Show the home layout in the center of the application
			vStrikerLayout.setCenter(layout);

			// Give controller access to main app
			EditConfigurationController controller = loader.getController();
			controller.setVStrikerApp(this);
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	public void showCharts(java.util.List<vStrikerEntities.ExecutionReportData>  rptData, String selectedAccount,String selectedTest) {
		System.out.println("In VStriker ShowEditConfiguaration");
		try {
			// Change page title
			this.SetTitle("vStriker:Result Charts");
			// Load home layout
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/ResultsChart.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();

			// Show the home layout in the center of the application
			vStrikerLayout.setCenter(layout);

			// Give controller access to main app
			ResultsChartController controller = loader.getController();
			controller.setVStrikerApp(this,rptData,selectedAccount,selectedTest);
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	public void showEditConfiguration(vStrikerEntities.ConfigurationTemplate tempcfg, vStrikerEntities.TestConfiguration testcfg, long l) {
		System.out.println("In VStriker ShowEditConfiguaration");
		try {
			// Change page title
			this.SetTitle("vStriker:Edit Configuration");
			// Load home layout
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/EditConfiguration.fxml"));
			AnchorPane layout = (AnchorPane) loader.load();

			// Show the home layout in the center of the application
			vStrikerLayout.setCenter(layout);

			// Give controller access to main app
			EditConfigurationController controller = loader.getController();
			controller.setVStrikerApp(this);
			controller.seTestEntity(tempcfg,testcfg,l);
			
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
	public void updateAccount(Account validAcct) {
		System.out.println("In VStriker updateAccount");
		try {
			// Change page title
			this.SetTitle("vStriker:Account");
			// Load home layout
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/Account.fxml"));
			AnchorPane accountLayout = (AnchorPane) loader.load();

			// Show the home layout in the center of the application
			vStrikerLayout.setCenter(accountLayout);

			// Give controller access to main app
			AccountController controller = loader.getController();
			controller.updateAccount(this, validAcct);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showS3API(Account validAcct) {
		System.out.println("In VStriker showS3API");
		try {
			// Change page title
			this.SetTitle("vStriker:S3 API Information");
			// Load S3 page
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/S3API.fxml"));
			AnchorPane S3Layout = (AnchorPane) loader.load();

			// Show the Swift page in the center of the application
			vStrikerLayout.setCenter(S3Layout);

			// Give controller access to the main app
			S3Controller controller = loader.getController();
			controller.setVStrikerApp(this, validAcct);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showSwiftAPI(Account validAcct) {
		System.out.println("In VStriker showSwiftAPI");
		try {
			// Change page title
			this.SetTitle("vStriker:Swift API Information");
			// Load Swift page
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/SwiftAPI.fxml"));
			AnchorPane SwiftLayout = (AnchorPane) loader.load();

			// Show the Swift page in the center of the application
			vStrikerLayout.setCenter(SwiftLayout);

			// Give controller access to the main application
			SwiftController controller = loader.getController();
			controller.setVStrikerApp(this, validAcct);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showAtmosAPI(Account validAcct) {
		System.out.println("In VStriker showAtmosAPI");
		try {
			// Change page title
			this.SetTitle("vStriker:Atmos API Information");
			// Load Atmos page
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(VStriker.class.getResource("view/AtmosAPI.fxml"));
			AnchorPane AtmosLayout = (AnchorPane) loader.load();

			// Show the S3 page in the center of the application
			vStrikerLayout.setCenter(AtmosLayout);

			// Give controller access to the main app
			AtmosController controller = loader.getController();
			controller.setVStrikerApp(this, validAcct);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
