package com.emccode.vstriker.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;
import vStrikerBizModel.AccountBiz;
import vStrikerBizModel.AccountDetailBiz;
import vStrikerBizModel.ApiBiz;
import vStrikerEntities.Account;
import vStrikerEntities.Api;
import vStrikerEntities.VwAccountDetail;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class HomepageController {
	@FXML
	private TabPane tbMain;
	@FXML
	private Tab accountTab;
	@FXML
	private Tab tbAccount;
	@FXML
	private Tab tbExecution;
	@FXML
	private Tab tbCfg;

	@FXML
	private TableView<Account> accountTable;
	@FXML
	private TableColumn<Account, Boolean> selectColumn;
	@FXML
	private TableColumn<Account, String> nameColumn;
	@FXML
	private TableColumn<Account, String> locationColumn;
	@FXML
	private TableColumn<Account, String> S3Column;
	@FXML
	private TableColumn<Account, String> SwiftColumn;
	@FXML
	private TableColumn<Account, String> AtmosColumn;
	@FXML
	private Button addButton;
	@FXML
	private Button validateButton;
	@FXML
	private Button updateButton;
	@FXML
	private Button deleteButton;
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
	@FXML
	private Button configureButton;

	private VStriker vStriker;
	private ObservableList<VwAccountDetail> accountData;
	private ObservableList<Account> acctList;
	private List<BooleanProperty> selectedRowList;

	@FXML
	private Parent configurationView;

	@FXML
	private ConfigurationController configurationViewController;

	@FXML
	private Parent resultsView;

	@FXML
	private ResultsController resultsViewController;

	// Constructor
	public HomepageController() {
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In HomepageController initialize");

		// Add Tab Listener
		tbMain.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> ov,
					Tab t, Tab t1) {
				if (t1.getId().equals("tbExecution")) {
					vStriker.SetTitle("vStriker:Test Execution");
					vStriker.postStatus("");
					resultsViewController.setVStrikerApp(vStriker);
					resultsViewController.LoadLists();
				}

				if (t1.getId().equals("tbCfg")) {
					vStriker.SetTitle("vStriker:Configuration");
					vStriker.postStatus("");
					configurationViewController
					.setVStrikerApp(vStriker);
				}

				if (t1.getId().equals("tbAccount")) {
					vStriker.SetTitle("vStriker");
					vStriker.postStatus("");
					resultsViewController.LoadLists();
				}
			}
		});

		assert accountTab != null : "fx:id=\"accountTab\" was not injected: check your FXML file 'Home.fxml'.";
		// Populate the name column
		nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
				cellData.getValue().getName()));
		// Populate the location column
		locationColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
						cellData.getValue().getAccountLocation()));

		List<BooleanProperty> selectedRowList = setupCheckboxColumn();
		// Populating the Checkbox Column
		selectColumn
				.setCellFactory(CheckBoxTableCell
						.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
							@Override
							public ObservableValue<Boolean> call(Integer index) {
								return selectedRowList.get(index);
							}
						}));

		// Populating the API Columns
		// S3 Column
		S3Column.setCellValueFactory(new Callback<CellDataFeatures<Account, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Account, String> p) {
				boolean flag = false;
				for (VwAccountDetail view : accountData) {
					if ((view.getAccountId() == p.getValue().getAccountId())
							&& view.getApiTypeName() != null
							&& view.getApiTypeName().contains("S3")) {
						flag = true;
					}
				}
				if (flag) {
					return new SimpleStringProperty("Yes");
				} else {
					return new SimpleStringProperty("No");
				}
			}
		});
		// Swift Column
		SwiftColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Account, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Account, String> p) {
						boolean flag = false;
						for (VwAccountDetail view : accountData) {
							if ((view.getAccountId() == p.getValue()
									.getAccountId())
									&& view.getApiTypeName() != null
									&& view.getApiTypeName().contains("Swift")) {
								flag = true;
							}
						}
						if (flag) {
							return new SimpleStringProperty("Yes");
						} else {
							return new SimpleStringProperty("No");
						}
					}
				});
		// Atmos Column
		AtmosColumn
				.setCellValueFactory(new Callback<CellDataFeatures<Account, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Account, String> p) {
						boolean flag = false;
						for (VwAccountDetail view : accountData) {
							if ((view.getAccountId() == p.getValue()
									.getAccountId())
									&& view.getApiTypeName() != null
									&& view.getApiTypeName().contains("Atmos")) {
								flag = true;
							}
						}
						if (flag) {
							return new SimpleStringProperty("Yes");
						} else {
							return new SimpleStringProperty("No");
						}
					}
				});
		// Make the checkbox column editable
		selectColumn.setEditable(true);
		accountTable.setEditable(true);
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		System.out.println("In Homepage Controller setVStrikerApp");
		this.vStriker = vStriker;
		configurationViewController.setVStrikerApp(vStriker);
		resultsViewController.setVStrikerApp(vStriker);

		try {
			accountData = FXCollections.observableArrayList(AccountDetailBiz
					.AccountSelectAll());
			acctList = FXCollections.observableArrayList(AccountBiz
					.AccountSelectAll());
			accountTable.setItems(acctList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		int selectedRow = getSelectedRow();
		if (selectedRow == -1) {
			vStriker.postStatus("Please select an Account to validate");
			return;
		}
		vStriker.showApiValidation((int) accountTable.getItems()
				.get(selectedRow).getAccountId());
	}

	public void setTab(int i) {
		SingleSelectionModel<Tab> tb = tbMain.getSelectionModel();
		tb.select(i);
		if (i == 2)
			resultsViewController.LoadLists();

	}

	// Update account button clicked
	@FXML
	public void updateAccountClicked(ActionEvent event) {
		System.out.println("Update account button clicked");
		int selectedRow = getSelectedRow();
		if (selectedRow == -1) {
			vStriker.postStatus("Please select an Account to update");
			return;
		}
		vStriker.updateAccount(accountTable.getItems().get(selectedRow));
	}

	// Delete account button clicked
	@FXML
	public void deleteAccountClicked(ActionEvent event) {
		System.out.println("Delete account button clicked");
		int selectedRow = getSelectedRow();
		if (selectedRow <0) {
			vStriker.postStatus("Please select an Account to delete");
			return;
		}

		ObservableList<Account> accts = accountTable.getItems();
		Account selectedAcct = accts.get(selectedRow);
		List<Api> numofApis = selectedAcct.getApis();
		// Check if acct has any apis
		System.out.println("Check if acct has apis");
		try {
			if (!numofApis.isEmpty()) {
				for (Api a : numofApis) {
					ApiBiz.ApiDelete(a.getApiId());
					accountDetail.getItems().clear();
				}
			}
		} catch (Exception e) {
			vStriker.postStatus("Unable to delete rows in Api table");
			e.printStackTrace();
			return;
		}

		/*
		 * // Delete rows from the API table try {
		 * 
		 * ApiBiz.ApiDeleteByAcount(accts.get(selectedRow).getAccountId());
		 * ObservableList<VwAccountDetail> acctDetails =
		 * accountDetail.getItems(); acctDetails.removeAll(acctDetails); } catch
		 * (Exception e1) { // TODO Auto-generated catch block
		 * System.out.println("Unable to delete rows in Api table");
		 * e1.printStackTrace(); }
		 */

		// Delete the account from Account table
		System.out.println("Delete account from account table");
		try {
			AccountBiz.AccountDelete(selectedAcct.getAccountId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			vStriker.postStatus("Unable to delete account");
			e.printStackTrace();
			return;
		}

		System.out.println("Delete account -remove selected row");
		accts.remove(selectedRow);
		// Clear checkboxes
		for (BooleanProperty b : selectedRowList) {
			b.setValue(false);
		}
		vStriker.postStatus("Delete account successful");
	}

	// Configure account button clicked
	@FXML
	public void configureAccountClicked(ActionEvent event) {
		System.out.println("Configure account button clicked");
		vStriker.showAddConfiguration();
	}

	private List<BooleanProperty> setupCheckboxColumn() {
		// Associate a BooleanProperty to every cell in the first column.
		selectedRowList = new ArrayList<BooleanProperty>();
		try {
			// Create a boolean property for every account that exists
			acctList = FXCollections.observableArrayList(AccountBiz
					.AccountSelectAll());
			for (Account a : acctList) {
				selectedRowList.add(new SimpleBooleanProperty());
			}
			// Add a listener for each boolean property
			for (BooleanProperty b : selectedRowList) {
				b.addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> obs,
							Boolean wasSelected, Boolean isSelected) {
						// Change all other BooleanProperty to false
						if (b.getValue()) {
							// This means b just was selected so every other
							// property should be unchecked
							for (BooleanProperty bo : selectedRowList) {
								if (b != bo)
									bo.setValue(false);
							}
							vStriker.postStatus("");
							// Show API details in the table below for Acct
							// selected.
							showAcctAPIDetails(acctList.get(selectedRowList
									.indexOf(b)));
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectedRowList;
	}

	private void showAcctAPIDetails(Account account) {
		System.out.println("In showAcctAPIDetails");
		// If this account has not apis yet - exit immediately
		if (account.getApis() == null || account.getApis().isEmpty()) {
			vStriker.postStatus("Account has no apis");
			accountDetail.getItems().clear();
			return;
		}
		ObservableList<VwAccountDetail> selectedAcct = FXCollections
				.observableArrayList();
		for (VwAccountDetail a : accountData) {
			if (a.getAccountId() == account.getAccountId())
				selectedAcct.add(a);
		}
		accountDetail.setItems(selectedAcct);
		// Populate columns in the details table
		APIColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
				cellData.getValue().getApiTypeName()));
		ProtocolColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
						cellData.getValue().getUrl().contains("https") ? "https"
								: "http"));
		PortColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
				cellData.getValue().getUrl().contains("https") ? cellData
						.getValue().getHttpsAddressPort() : cellData.getValue()
						.getHttpAddressPort()));
		KeyColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
				cellData.getValue().getSecretKey()));
		EndPointColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
						cellData.getValue().getUrl()));
	}

	private int getSelectedRow() {
		int selectedRow = 0;
		for (BooleanProperty b : selectedRowList) {
			if (b.getValue()) {
				selectedRow = selectedRowList.indexOf(b);
				// System.out.println("selected row is: " + selectedRow);
				return selectedRow;
			}
		}
		return -1;
	}
}
