package com.emccode.vstriker.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import vStrikerBizModel.AccountBiz;
import vStrikerBizModel.AccountDetailBiz;
import vStrikerEntities.Account;
import vStrikerEntities.VwAccountDetail;

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
	@FXML
	private TableView<VwAccountDetail> accountDetail;
	@FXML
	private TableColumn<VwAccountDetail, String> APIColumn;
	@FXML
	private TableColumn<VwAccountDetail, String> ProtocolColumn;
	@FXML
	private TableColumn<VwAccountDetail, String> PortColumn;
	@FXML
	private TableColumn<VwAccountDetail, String> KeyColumn;
	@FXML
	private TableColumn<VwAccountDetail, String> EndPointColumn;

	private VStriker vStriker;
	private VwAccountDetail validAcct;

	// Constructor
	public AccountController() {
	}

	// Set the main application
	public void createAccount(VStriker vStriker) {
		this.vStriker = vStriker;
	}

	public void updateAccount(VStriker vStriker, VwAccountDetail validAcct) {
		System.out.println("In AccountController - updateAccount");
		this.validAcct = validAcct;
		createAccount(vStriker);
		accountName.setText(validAcct.getName());
		accountLocation.setText(validAcct.getAccountLocation());
		// Populate the table
		ObservableList<VwAccountDetail> accountData, selectedAcct;
		try {
			accountData = FXCollections.observableArrayList(AccountDetailBiz
					.AccountSelectAll());
			selectedAcct = FXCollections.observableArrayList();
			for (VwAccountDetail a : accountData) {
				if (a.getAccountId() == validAcct.getAccountId())
					selectedAcct.add(a);
			}
			accountDetail.setItems(selectedAcct);
			// Populate columns in the details table
			APIColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getApiTypeName()));
			KeyColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getSecretKey()));
			EndPointColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getUrl()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				// Show message on screen - ToDo
				// e.printStackTrace();
			}
		}
	}

	@FXML
	public void addAPIClicked(ActionEvent event) {
		System.out.println("Add API button clicked");
		// Check if a valid account exists
		if (accountName.getText() == null
				|| accountName.getText().length() == 0
				|| accountLocation.getText() == null
				|| accountLocation.getText().length() == 0) {
			System.out.println("Please set Account Name and Account Location");
		}
		Account validAcct;
		try {
			validAcct = AccountBiz.AccountSelect(accountName.getText(),
					accountLocation.getText());

			switch (chooseAPI.getValue().toString()) {
			case "S3":
				vStriker.showS3API(validAcct);
				break;
			case "Swift":
				vStriker.showSwiftAPI(validAcct);
				break;
			case "Atmos":
				vStriker.showAtmosAPI(validAcct);
				break;
			default:
				System.out
						.println("addAPIClicked - chooseAPI is neither S3, Swift or Atmos");
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Not a valid Account");
			e.printStackTrace();
		}
	}
}
