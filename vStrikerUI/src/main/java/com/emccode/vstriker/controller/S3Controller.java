package  com.emccode.vstriker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class S3Controller {
	@FXML
	private Button backtoAccount;
	@FXML
	private Button validateS3;
	
	private VStriker vStriker;

	// Constructor
	public S3Controller() {
	}
	
	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In S3Controller initialize");
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showHome();
	}
	
	@FXML
	public void validateS3Clicked(ActionEvent event) {
		System.out.println("Validate S3 button clicked");
	}
}
