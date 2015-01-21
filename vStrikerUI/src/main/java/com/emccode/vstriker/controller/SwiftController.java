package com.emccode.vstriker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import vStrikerEntities.Account;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class SwiftController {
	@FXML
	private Button backtoAccount;
	@FXML
	private Button validateSwift;

	private VStriker vStriker;
	private Account acct;

	// Constructor
	public SwiftController() {
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker, Account validAcct) {
		this.vStriker = vStriker;
		this.acct = validAcct;
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In SwiftController initialize");
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showHome();
	}

	@FXML
	public void validateSwiftClicked(ActionEvent event) {
		System.out.println("Validate Swift button clicked");
	}
}
