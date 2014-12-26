package com.emccode.vstriker.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.emccode.vstriker.VStriker;
import com.emccode.vstriker.model.Account;

//@author Sanjeev Chauhan

public class HomepageController {
	@FXML
	private Tab accountTab;
	@FXML
	private TableView<Account> accountTable;
	@FXML
	private TableColumn<Account, String> nameColumn;
	@FXML
	private TableColumn<Account, String> locationColumn;
	@FXML
	private Button addButton;
	@FXML
	private Button validateButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button configureButton;

	private VStriker vStriker;

	// Constructor
	public HomepageController() {
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In HomepageController initialize");
		assert accountTab != null : "fx:id=\"accountTab\" was not injected: check your FXML file 'Home.fxml'.";
		// Initialize the account table with two columns
		nameColumn.setCellValueFactory(cellData -> cellData.getValue()
				.nameProperty());
		locationColumn.setCellValueFactory(cellData -> cellData.getValue()
				.locationProperty());
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;
		accountTable.setItems(vStriker.getAccountData());
	}

	// Add account button clicked
	@FXML
	public void addAccountClicked(ActionEvent event) {
		System.out.println("Add account button clicked");
		vStriker.showAccount();
	}

	// Validate account button clicked
	@FXML
	public void validateAccountClicked(ActionEvent event) {
		System.out.println("Validate account button clicked");
	}

	// Update account button clicked
	@FXML
	public void updateAccountClicked(ActionEvent event) {
		System.out.println("Update account button clicked");
	}

	// Delete account button clicked
	@FXML
	public void deleteAccountClicked(ActionEvent event) {
		System.out.println("Delete account button clicked");
	}

	// Configure account button clicked
	@FXML
	public void configureAccountClicked(ActionEvent event) {
		System.out.println("Configure account button clicked");
	}
}
