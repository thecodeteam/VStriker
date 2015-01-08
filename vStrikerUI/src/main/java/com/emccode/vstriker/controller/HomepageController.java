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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;
import vStrikerBizModel.AccountDetailBiz;
import vStrikerEntities.VwAccountDetail;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class HomepageController {
	@FXML
	private Tab accountTab;
	@FXML
	private TableView<VwAccountDetail> accountTable;
	@FXML
	private TableColumn<VwAccountDetail, Boolean> selectColumn;
	@FXML
	private TableColumn<VwAccountDetail, String> nameColumn;
	@FXML
	private TableColumn<VwAccountDetail, String> locationColumn;
	@FXML
	private TableColumn<VwAccountDetail, String> S3Column;
	@FXML
	private TableColumn<VwAccountDetail, String> SwiftColumn;
	@FXML
	private TableColumn<VwAccountDetail, String> AtmosColumn;
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
		/*
		 * nameColumn.setCellValueFactory(new Callback<CellDataFeatures<Account,
		 * String>, ObservableValue<String>>() { public ObservableValue<String>
		 * call(CellDataFeatures<Account, String> p) { return new
		 * ReadOnlyObjectWrapper(p.getValue().getName()); } });
		 */
		nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
				cellData.getValue().getName()));
		locationColumn
				.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(
						cellData.getValue().getAccountLocation()));

		// Checkbox in first column
		List<BooleanProperty> selectedRowList = new ArrayList<BooleanProperty>();
		javafx.collections.ObservableList<VwAccountDetail> acctList;
		try {
			// Create a boolean property for every account that exists
			acctList = FXCollections.observableArrayList(AccountDetailBiz
					.AccountSelectAll());
			for (VwAccountDetail a : acctList) {
				selectedRowList.add(new SimpleBooleanProperty());
			}
			// Add a listener for each boolean property
			for (BooleanProperty b : selectedRowList) {
				b.addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> obs,
							Boolean wasSelected, Boolean isSelected) {
						System.out.println("isSelected: " + isSelected);
					}
				});
			}
			// Callback for CheckBoxTableCell
			Callback<Integer, ObservableValue<Boolean>> selectedStateSelectColumn = new Callback<Integer, ObservableValue<Boolean>>() {
				public ObservableValue<Boolean> call(Integer index) {
					return selectedRowList.get(index);
				}
			};
			selectColumn.setCellFactory(CheckBoxTableCell
					.forTableColumn(selectedStateSelectColumn));

			// Populating the API Columns
			// S3 Column
			S3Column.setCellValueFactory(new Callback<CellDataFeatures<VwAccountDetail, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(
						CellDataFeatures<VwAccountDetail, String> p) {
					if (p.getValue().getApiTypeName() != null
							&& p.getValue().getApiTypeName().contains("S3")) {
						return new SimpleStringProperty("Yes");
					} else {
						return new SimpleStringProperty("No");
					}
				}
			});
			// Swift Column
			SwiftColumn.setCellValueFactory(new Callback<CellDataFeatures<VwAccountDetail, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(
						CellDataFeatures<VwAccountDetail, String> p) {
					if (p.getValue().getApiTypeName() != null
							&& p.getValue().getApiTypeName().contains("Swift")) {
						return new SimpleStringProperty("Yes");
					} else {
						return new SimpleStringProperty("No");
					}
				}
			});
			// Atmos Column
			AtmosColumn.setCellValueFactory(new Callback<CellDataFeatures<VwAccountDetail, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(
						CellDataFeatures<VwAccountDetail, String> p) {
					if (p.getValue().getApiTypeName() != null
							&& p.getValue().getApiTypeName().contains("Atmos")) {
						return new SimpleStringProperty("Yes");
					} else {
						return new SimpleStringProperty("No");
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Make the checkbox column editable
		selectColumn.setEditable(true);
		accountTable.setEditable(true);

	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;
		new AccountDetailBiz();
		javafx.collections.ObservableList<VwAccountDetail> accountData;
		try {
			accountData = FXCollections.observableArrayList(AccountDetailBiz
					.AccountSelectAll());
			accountTable.setItems(accountData);
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