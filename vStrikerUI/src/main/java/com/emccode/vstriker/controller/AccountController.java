package com.emccode.vstriker.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import  com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class AccountController {
	@FXML
	private Button backtoAccount;
	@FXML
	private Button addAPI;
	@FXML
	private ChoiceBox<String> chooseAPI;
	
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
		chooseAPI.setItems(FXCollections.observableArrayList("S3", "Swift", "Atmos"));
		chooseAPI.setValue("S3");
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("back to Accounts button clicked");
		vStriker.showHome();
	}
	
	@FXML
	public void addAPIClicked(ActionEvent event) {
		System.out.println("Add API button clicked");
		switch(chooseAPI.getValue().toString())
		{
		case "S3":
			vStriker.showS3API();
			break;
		case "Swift":
			vStriker.showSwiftAPI();
			break;
		case "Atmos":
			vStriker.showAtmosAPI();
			break;
		default:
			System.out.println("addAPIClicked - chooseAPI is neither S3, Swift or Atmos");
			break;
		}
	}
}
