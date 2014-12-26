package com.emccode.vstriker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class AccountController {
	@FXML
	private Button backtoAccount;
	
	private VStriker vStriker;

	// Constructor
	public AccountController() {
	}
	
	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In AccountController initialize");
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("back to Accounts button clicked");
		vStriker.showHome();
	}
}
