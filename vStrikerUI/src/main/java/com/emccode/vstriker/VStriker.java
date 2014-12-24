package com.emccode.vstriker;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.emccode.vstriker.model.Account;
import com.emccode.vstriker.view.HomepageController;

/*
 * @author Sanjeev Chauhan
 */
public class VStriker extends Application {
	
	private Stage primaryStage;
	private BorderPane vStrikerLayout;
	
	// Replace with Account from vStrikerEntities package - ToDo
	private ObservableList<Account> accountData = FXCollections.observableArrayList();
	
	// Constructor
	public VStriker() {
		//Adding sample data - move to vStrikerLoader package - ToDo
		accountData.add(new Account("name1", "location1"));
		accountData.add(new Account("name2", "location2"));
	}
	
	// Return list of Accounts
	public ObservableList<Account> getAccountData() {
		return accountData;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("VStriker");
		
		initVStriker();
		showHome();
	}
	
	//Initialize the VStriker application
	public void initVStriker() {
		try {
			System.out.println("Initializing the VStriker application");
			//Load the layout from the fxml file
			FXMLLoader loader = new FXMLLoader();
			//loader.setLocation(VStriker.class.getResource("com/emccode/vstriker/view/VStriker.fxml"));
			loader.setLocation(VStriker.class.getResource("view/VStriker.fxml"));
			vStrikerLayout = (BorderPane) loader.load();
			
			//Show the scene layout
			Scene scene = new Scene(vStrikerLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Show the home page in the application
	public void showHome() {
		try {
			//Load home layout
			FXMLLoader loader = new FXMLLoader();
			//loader.setLocation(VStriker.class.getResource("com/emccode/vstriker/view/Home.fxml"));
			loader.setLocation(VStriker.class.getResource("view/Home.fxml"));
			AnchorPane homeLayout = (AnchorPane) loader.load();
			
			//Show the home layout in the center of the application
			vStrikerLayout.setCenter(homeLayout);
			
			// Give controller access to main app
			HomepageController controller = loader.getController();
			controller.setVStrikerApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
