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
	private TableColumn<VwAccountDetail, Boolean> selectColumn;
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
    @FXML
    private Button validateAPIBtn;
    @FXML
    private Button updateAPIBtn;
    @FXML
    private Button deleteAPIBtn;

	private VStriker vStriker;
	private Account validAcct;
	private int selectedRow;

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
		createAccount(vStriker);
		accountName.setText(validAcct.getName());
		accountLocation.setText(validAcct.getAccountLocation());
		// If this account has not apis yet - exit immediately
		if (validAcct.getApis() == null || validAcct.getApis().isEmpty()) {
			System.out.println("Account has no apis");
			accountDetail.getItems().clear();
			return;
		}
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
			List<BooleanProperty> selectedRowList = setupCheckboxColumn(selectedAcct);
			selectColumn
					.setCellFactory(CheckBoxTableCell
							.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
								public ObservableValue<Boolean> call(Integer index) {
									System.out.println("Index is: " + index);
									return selectedRowList.get(index);
								}
							}));
			APIColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getApiTypeName()));
			KeyColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getSecretKey()));
			EndPointColumn
					.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
							cellData.getValue().getUrl()));
			ProtocolColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
					cellData.getValue().getUrl().contains("https")? "https": "http"));
			PortColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
					cellData.getValue().getUrl().contains("https")? cellData.getValue().getHttpsAddressPort() : cellData.getValue().getHttpAddressPort()));
			selectColumn.setEditable(true);
			accountDetail.setEditable(true);
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
    public void validateAPIClicked(ActionEvent event) {
        System.out.println("Validate API button clicked");
    }

    @FXML
    public void updateAPIClicked(ActionEvent event) {
        System.out.println("Update API button clicked");
        // Check if a valid account exists
        if (accountName.getText() == null
                || accountName.getText().length() == 0
                || accountLocation.getText() == null
                || accountLocation.getText().length() == 0) {
            System.out.println("Please set Account Name and Account Location");
        }
    }

    @FXML
    public void deleteAPIClicked(ActionEvent event) {
        System.out.println("Delete API button clicked");
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
	
	private List<BooleanProperty> setupCheckboxColumn(ObservableList<VwAccountDetail> selectedAcct) {
		// Associate a BooleanProperty to every cell in the first column.
		ArrayList<BooleanProperty>selectedRowList = new ArrayList<BooleanProperty>();
		try {
			for (VwAccountDetail a : selectedAcct) {
				selectedRowList.add(new SimpleBooleanProperty());
			}
			// Add a listener for each boolean property
			for (BooleanProperty b : selectedRowList) {
				b.addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> obs,
							Boolean wasSelected, Boolean isSelected) {
						System.out.println("isSelected: " + isSelected);
						// Change all other BooleanProperty to false
						if (b.getValue()) {
							// This means b just was selected so every other
							// property should be unchecked
							for (BooleanProperty bo : selectedRowList) {
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
		return selectedRowList;
	}
}
