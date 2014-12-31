package  com.emccode.vstriker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import  com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class AtmosController {
	@FXML
	private Button backtoAccount;
	@FXML
	private Button validateSwift;
	
	private VStriker vStriker;

	// Constructor
	public AtmosController() {
	}
	
	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In AtmosController initialize");
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showHome();
	}
	
	@FXML
	public void validateAtmosClicked(ActionEvent event) {
		System.out.println("Validate Atmos button clicked");
	}
}
