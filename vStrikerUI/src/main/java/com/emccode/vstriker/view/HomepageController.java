package com.emccode.vstriker.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import com.emccode.vstriker.VStriker;
import com.emccode.vstriker.model.Account;

//@author Sanjeev Chauhan

public class HomepageController {
	@FXML
	private TableView<Account> accountTable;
	@FXML
	private TableColumn<Account, String> nameColumn;
	@FXML
	private TableColumn<Account, String> locationColumn;
	
	private VStriker vStriker;
	
	// Constructor
	public HomepageController() {
	}
	
	// Initialize
	@FXML
	private void initialize() {
		// Initialize the account table with two columns
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
	}
	
	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;
		
		accountTable.setItems(vStriker.getAccountData());
	}
}
