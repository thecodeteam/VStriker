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
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.text.Text;
import javafx.util.Callback;
import vStrikerBizModel.AccountBiz;
import vStrikerBizModel.ApiBiz;
import vStrikerEntities.Account;
import vStrikerEntities.Api;
import vStrikerTestEngine.Engine;
import vStrikerTestEngine.VEngine;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class APIValidationController {

	@FXML
	private Text acctname;
	@FXML
	private Text acctlocation;
	@FXML
	private Button backtoAccount;
	@FXML
	private Button validateButton;
	@FXML
	private ProgressBar validationProgress;
	@FXML
	private TextArea validationMessage;
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

	private VStriker vStriker;
	private Account validAcct;
	private List<Api> apis;
	private List<Api> selectedapis = new ArrayList<Api>();
	static private Task validationWorker;

	// Constructor
	public APIValidationController() {
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In APIValidationController initialize");
	}

	// Setup
	public void setup(VStriker vStriker, int accountId) throws Exception {
		this.vStriker = vStriker;
		this.validAcct = AccountBiz.AccountSelect(accountId);
		this.apis = ApiBiz.ApiSelectforAccount(validAcct);
		vStriker.postStatus("");
		acctname.setText(validAcct.toString());
		acctlocation.setText(validAcct.getAccountLocation());
		populateApiTable();
	}

	private void populateApiTable() {
		try {
			ObservableList<Api> accountApis = FXCollections
					.observableArrayList(apis);
			List<BooleanProperty> listofcheckboxes = setupCheckboxColumn(accountApis);
			apiDetail.setItems(accountApis);
			SelectColumn
					.setCellFactory(CheckBoxTableCell
							.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
								@Override
								public ObservableValue<Boolean> call(
										Integer index) {
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

	private List<BooleanProperty> setupCheckboxColumn(
			ObservableList<Api> apiList) {
		// Associate a BooleanProperty to every cell in the first column.
		ArrayList<BooleanProperty> listBool = new ArrayList<BooleanProperty>();
		try {
			for (Api a : apiList) {
				listBool.add(new SimpleBooleanProperty());
			}
			// Add a listener for each boolean property
			for (BooleanProperty b : listBool) {
				b.addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> obs,
							Boolean wasSelected, Boolean isSelected) {
						if (b.getValue()) {
							selectedapis.add(apiList.get(listBool.indexOf(b)));
						} else {
							selectedapis.remove(apiList.get(listBool.indexOf(b)));
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listBool;
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("back to Accounts button clicked");
		vStriker.showHome();
	}

	@FXML
	public void validateButtonClicked(ActionEvent event) {
		System.out.println("Validate Apis button clicked");
		validationProgress.progressProperty().unbind();
		validationProgress.setProgress(0);
		validationMessage.setText("");

		// Check if an api is selected
		if (selectedapis == null || selectedapis.size() == 0) {
			vStriker.postStatus("Please select an api");
			return;
		}
		validationWorker = createValidationWorker(selectedapis);

		// wire up the progress bar
		validationProgress.progressProperty().bind(
				validationWorker.progressProperty());
		// append to text area box
		validationWorker.messageProperty().addListener(
				new ChangeListener<String>() {
					@Override
					public void changed(
							ObservableValue<? extends String> observable,
							String oldValue, String newValue) {
						validationMessage.appendText(newValue + "\n");
					}
				});

		new Thread(validationWorker).start();

	}

	private Task createValidationWorker(List<Api> apis) {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				int totalsteps = apis.size() * 5;
				int currentstep = 1;
				for (Api a : apis) {
					switch (a.getApiType().getApiTypeName()) {
					case "S3":
						if (a.getSecretKey() != null && a.getUrl() != null
								&& a.getSubtenant() != null) {
							Engine e = new VEngine();
							if (e.validateS3Connection(a.getSubtenant(),
									a.getSecretKey(), a.getUrl(), "")) {
								updateMessage("S3 connection is validated for User: "
										+ a.getSubtenant()
										+ " at URL: "
										+ a.getUrl());

							} else {
								updateMessage("S3 connection is not working for User: "
										+ a.getSubtenant()
										+ " at URL: "
										+ a.getUrl());
							}
							updateProgress(currentstep, totalsteps);
							break;
						}
					case "Atmos":
						System.out.println("Atmos");
						break;
					case "Swift":
						System.out.println("Swift");
						break;
					default:
						System.out
								.println("Please check to ensure the right type of Api is set");
						vStriker.postStatus("Unable to perform validation: Api not of type S3, Swift or Atmos");
					}
					updateProgress(totalsteps, totalsteps);
				} // end for
				return true;
			}
		};
	}
}
