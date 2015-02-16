package com.emccode.vstriker.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;
import vStrikerBizModel.AccountBiz;
import vStrikerBizModel.ApiBiz;
import vStrikerEntities.Account;
import vStrikerEntities.Api;

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
	private TableView<Api> apiDetail;
	@FXML
	private TableColumn<Api, Boolean> SelectColumn;
	@FXML
	private TableColumn<Api, String> APIColumn;
	@FXML
	private TableColumn<Api, String> ProtocolColumn;
	@FXML
	private TableColumn<Api, String> PortColumn;
	@FXML
	private TableColumn<Api, String> KeyColumn;
	@FXML
	private TableColumn<Api, String> EndPointColumn;
	@FXML
	private Button validateAPIBtn;
	@FXML
	private Button updateAPIBtn;
	@FXML
	private Button deleteAPIBtn;

	private VStriker vStriker;
	private Account validAcct;
	private List<BooleanProperty> listofcheckboxes;

	// Constructor
	public AccountController() {
	}

	// Set the main application
	public void createAccount(VStriker vStriker) {
		this.vStriker = vStriker;
	}

	public void updateAccount(VStriker vStriker, Account validAcct) {
		System.out.println("In AccountController - updateAccount");
		this.validAcct = validAcct;
		this.vStriker = vStriker;
		accountName.setText(validAcct.getName());
		accountLocation.setText(validAcct.getAccountLocation());
		// If this account has not apis yet - exit immediately
		if (validAcct.getApis() == null || validAcct.getApis().isEmpty()) {
			System.out.println("Account has no apis");
			apiDetail.getItems().clear();
			return;
		}
		// Populate the table
		try {
			ObservableList<Api> accountApis = FXCollections
					.observableArrayList(ApiBiz.ApiSelectforAccount(validAcct));
			listofcheckboxes = setupCheckboxColumn(accountApis);
			apiDetail.setItems(accountApis);
			SelectColumn
					.setCellFactory(CheckBoxTableCell
							.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
								@Override
								public ObservableValue<Boolean> call(
								Integer index) {
									// return new SimpleBooleanProperty();
									System.out.println("Index is: " + index);
									return listofcheckboxes.get(index);
								}
							}));
			APIColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getApiType().getApiTypeName()));
			KeyColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getSecretKey()));
			EndPointColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getUrl()));
			ProtocolColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getUrl().contains("https") ? "https"
									: "http"));
			PortColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getUrl().contains("https") ? cellData
									.getValue().getHttpsAddressPort()
									: cellData.getValue().getHttpAddressPort()));
			SelectColumn.setEditable(true);
			apiDetail.setEditable(true);
		} catch (Exception e) {
			System.out.println("Unable to populate table");
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
	public void validateAPIClicked(ActionEvent event) {
		System.out.println("Validate API button clicked");
		if (validAcct != null) {
			vStriker.showApiValidation((int) validAcct.getAccountId());
		} else {
			vStriker.postStatus("Missing account");
		}
	}

	@FXML
	public void updateAPIClicked(ActionEvent event) {
		System.out.println("Update API button clicked");
		// Check if a valid account exists
		if (accountName.getText() == null
				|| accountName.getText().length() == 0
				|| accountLocation.getText() == null
				|| accountLocation.getText().length() == 0
				|| getSelectedRow() == -1) {
			System.out
					.println("Please set Account Name and Account Location and select a row");
		}
		int selectedRow = getSelectedRow();
		if (selectedRow != -1) {
			switch (apiDetail.getItems().get(selectedRow).getApiType()
					.getApiTypeName()) {
					case "S3":
						vStriker.updateS3API(validAcct,
								apiDetail.getItems().get(selectedRow));
						break;
					case "Swift":
						vStriker.updateSwiftAPI(validAcct,
						apiDetail.getItems().get(selectedRow));
						break;
					case "Atmos":
						vStriker.updateAtmosAPI(validAcct,
								apiDetail.getItems().get(selectedRow));
						break;
					default:
						System.out.println("updateApi - unexpected case");
			}
		}
	}

	@FXML
	public void deleteAPIClicked(ActionEvent event) {
		System.out.println("Delete API button clicked");
		/*
		 * if (listofcheckboxes == null) { System.out
		 * .println("List of checkboxes not initialized - Unexpected error");
		 * return; } int selectedrow = -1; for (int i = 0; i <
		 * listofcheckboxes.size(); i++) { if (listofcheckboxes.get(i).get()) {
		 * selectedrow = i; } }
		 */
		int selectedrow = getSelectedRow();
		if (selectedrow != -1) {
			Api selectedApi = apiDetail.getItems().get(selectedrow);
			try {
				ApiBiz.ApiDelete(selectedApi.getApiId());
			} catch (Exception e) {
				System.out.println("Delete API failed");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			apiDetail.getItems().remove(selectedrow);
		} else {
			System.out.println("Please select an API to delete");
		}
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
			// e.printStackTrace();
		}
	}

	private List<BooleanProperty> setupCheckboxColumn(
			ObservableList<Api> apiList) {
		// Associate a BooleanProperty to every cell in the first column.
		ArrayList<BooleanProperty> listBool = new ArrayList<BooleanProperty>();
		try {
			for (Api a : apiList) {
				listBool.add(new SimpleBooleanProperty());
				System.out.println("Adding BooleanProperty");
			}
			// Add a listener for each boolean property
			for (BooleanProperty b : listBool) {
				b.addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> obs,
							Boolean wasSelected, Boolean isSelected) {
						System.out.println("isSelected: " + isSelected);
						// Change all other BooleanProperty to false
						if (b.getValue()) {
							// This means b just was selected so every other
							// property should be unchecked
							for (BooleanProperty bo : listBool) {
								if (b != bo)
									bo.setValue(false);
							}
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Returning list of BooleanProperty");
		return listBool;
	}

	private int getSelectedRow() {
		if (listofcheckboxes == null) {
			System.out
					.println("List of checkboxes not initialized - Unexpected error");
			return -1;
		}
		int selectedrow = -1;
		for (int i = 0; i < listofcheckboxes.size(); i++) {
			if (listofcheckboxes.get(i).get()) {
				System.out.println("selected row is: " + i);
				selectedrow = i;
			}
		}
		return selectedrow;
	}
}
