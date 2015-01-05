package com.emccode.vstriker.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import vStrikerBizModel.AccountBiz;
import vStrikerEntities.Account;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class AccountController {
	@FXML
	private Button backtoAccount;
	@FXML
	private Button addAPI;
	@FXML
	private ChoiceBox<String> chooseAPI;
	@FXML
	private Button saveupdate;
	@FXML
	private TextField accountName;
	@FXML
	private TextField accountLocation;

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
		chooseAPI.setItems(FXCollections.observableArrayList("S3", "Swift",
				"Atmos"));
		chooseAPI.setValue("S3");
		// Set prompt text
		accountName.setPromptText("Enter Account Name");
		accountLocation.setPromptText("Enter Account Location");
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("back to Accounts button clicked");
		vStriker.showHome();
	}

	@FXML
	public void saveupdateClicked(ActionEvent event) {
		System.out.println("Save/Update button clicked");
		if (accountName.getText() == null
				|| accountName.getText().length() == 0
				|| accountLocation.getText() == null
				|| accountLocation.getText().length() == 0) {
			System.out.println("Please set Account Name and Account Location");
		} else {
			System.out.println("Account to be added: " + accountName.getText()
					+ " " + accountLocation.getText());
			Account acct = new Account();
			acct.setName(accountName.getText());
			acct.setAccountLocation(accountLocation.getText());
			try {
				AccountBiz.AccountCreate(acct);
			} catch (Exception e) {
				System.out.println("Failed to save Account");
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void addAPIClicked(ActionEvent event) {
		System.out.println("Add API button clicked");
		switch (chooseAPI.getValue().toString()) {
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
			System.out
					.println("addAPIClicked - chooseAPI is neither S3, Swift or Atmos");
			break;
		}
	}
}
