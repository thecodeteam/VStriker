package  com.emccode.vstriker.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import vStrikerBizModel.AccountBiz;
import vStrikerEntities.Account;

import com.emccode.vstriker.VStriker;


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
		/*
		nameColumn.setCellValueFactory(new Callback<CellDataFeatures<Account, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Account, String> p) {
				return new ReadOnlyObjectWrapper(p.getValue().getName());
			}
		});
		*/
		nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getName()));
		locationColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getAccountLocation()));
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker) {
		this.vStriker = vStriker;
		new AccountBiz();
		//accountTable.setItems(vStriker.getAccountData());
		javafx.collections.ObservableList<Account> accountData;
		try {
			accountData = FXCollections.observableArrayList(AccountBiz.AccountSelectAll());
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
