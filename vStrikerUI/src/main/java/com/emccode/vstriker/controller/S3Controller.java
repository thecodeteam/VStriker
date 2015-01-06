package  com.emccode.vstriker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import vStrikerTestEngine.Engine;
import vStrikerTestEngine.VEngine;

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
		// Read these variables from the screen - ToDo
		String S3_ACCESS_KEY_ID = "user045";
        String S3_SECRET_KEY = "vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg";
        String S3_ENDPOINT = "http://object.vipronline.com";
        String S3_ViPR_NAMESPACE = null;
        
		Engine engine = new VEngine();
		if (engine.validateS3Connection(S3_ACCESS_KEY_ID, S3_SECRET_KEY, S3_ENDPOINT, S3_ViPR_NAMESPACE)) {
			System.out.println("S3 connection is validated");
		} else {
			System.out.println("S3 connection is not working");
		}
	}
}
