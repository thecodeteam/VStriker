package  com.emccode.vstriker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import vStrikerEntities.Account;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class ResultsChartController {
	@FXML
	private Button btnBack;
	private VStriker vStriker;
	


	// Constructor
	public ResultsChartController() {
	}
	
	// Set the main application
	public void setVStrikerApp(VStriker vStrikert) {
		this.vStriker = vStriker;

	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In ConfigurationController initialize");
	}


		@FXML
	public void btnBackClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showHome();
	}

}
