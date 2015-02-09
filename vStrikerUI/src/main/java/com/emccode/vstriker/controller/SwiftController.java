package com.emccode.vstriker.controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import vStrikerBizModel.ApiBiz;
import vStrikerEntities.Account;
import vStrikerEntities.Api;
import vStrikerTestEngine.Engine;
import vStrikerTestEngine.VEngine;

import com.emccode.vstriker.VStriker;

//@author Sanjeev Chauhan

public class SwiftController {
	@FXML
	private Button backtoAccount;
	@FXML
	private Button validateswift;
	@FXML
	private Button saveswift;
	@FXML
	private TextField swiftuser;
	@FXML
	private TextField swifturl;
	@FXML
	private TextField swiftsecretkey;
	@FXML
	private TextField swiftbucket;
	@FXML
	private ChoiceBox<String> chooseprotocol;
	@FXML
	private TextField swiftport;

	private VStriker vStriker;
	private Account acct;

	// Constructor
	public SwiftController() {
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker, Account validAcct) {
		this.vStriker = vStriker;
		this.acct = validAcct;
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In SwiftController initialize");
		chooseprotocol.setItems(FXCollections.observableArrayList("http",
				"https"));
		chooseprotocol.setValue("http");
		swiftuser.setPromptText("User");
		swifturl.setPromptText("URL to service");
		swiftsecretkey.setPromptText("Secret key");
		swiftbucket.setPromptText("Bucket name");
		swiftport.setPromptText("Port number");
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showHome();
	}

	@FXML
	public void validateSwiftClicked(ActionEvent event) {
		System.out.println("Validate Swift button clicked");
		String S3_ViPR_NAMESPACE = null;
		if (swiftuser.getText() == null || swiftuser.getText().length() == 0
				|| swifturl.getText() == null
				|| swifturl.getText().length() == 0
				|| swiftsecretkey.getText() == null
				|| swiftsecretkey.getText().length() == 0) {
			System.out.println("Please set Access Key, URL and Secret Key");
			return;
		}
		Engine engine = new VEngine();
		if (engine
				.validateSwiftConnnection(swiftuser.getText(),
						swiftsecretkey.getText(), swifturl.getText(),
						S3_ViPR_NAMESPACE)) {
			System.out.println("S3 connection is validated");
		} else {
			System.out.println("S3 connection is not working");
		}
	}

	@FXML
	public void saveSwiftClicked(ActionEvent event) throws Exception {
		System.out.println("Save Swift button clicked");

		if (swiftuser.getText() == null || swiftuser.getText().length() == 0
				|| swifturl.getText() == null
				|| swifturl.getText().length() == 0
				|| swiftsecretkey.getText() == null
				|| swiftsecretkey.getText().length() == 0
				|| swiftbucket.getText() == null
				|| swiftbucket.getText().length() == 0) {
			System.out
					.println("Please set Access Key, URL, Secret Key and Bucket name");
			return;
		}
		Api swiftapi = new Api();
		swiftapi.setAccount(acct);
		swiftapi.setSubtenant(swiftuser.getText());
		swiftapi.setUrl(swifturl.getText());
		swiftapi.setSecretKey(swiftsecretkey.getText());
		swiftapi.setBucket(swiftbucket.getText());

		swiftapi.setHttpAddressIp("tobechanged");
		// This should change setProtocol
		// s3api.setHttpAddressPort("999"); // This should change to setPort
		// s3api.setApiTypeId(2); // This depends on protocol - if protocol is
		// http
		// or https

		List<vStrikerEntities.ApiType> apitypelist = vStrikerBizModel.ApiTypeBiz
				.ApiTypeSelectAll();

		switch (chooseprotocol.getValue().toString()) {
		case "http":
			for (vStrikerEntities.ApiType a : apitypelist) {
				if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("http"))) {
					swiftapi.setApiType(a);
					break;
				}
			}
			if (swiftport.getText() == null
					|| swiftport.getText().length() == 0) {
				swiftport.setText("80");
				swiftapi.setHttpAddressPort("80");
			} else {
				swiftapi.setHttpAddressPort(swiftport.getText());
			}
			if (!swifturl.getText().toLowerCase().matches("^\\w+://.*")) {
				swifturl.setText("http://" + swifturl.getText());
				swiftapi.setUrl(swifturl.getText());
			}
			break;
		case "https":
			for (vStrikerEntities.ApiType a : apitypelist) {
				if ((a.getApiTypeName().equalsIgnoreCase("Swift"))
						&& (a.getApiTypeUrl().equalsIgnoreCase("https"))) {
					swiftapi.setApiType(a);
					break;
				}
			}
			if (swiftport.getText() == null
					|| swiftport.getText().length() == 0) {
				swiftport.setText("443");
				swiftapi.setHttpAddressPort("443");
			} else {
				swiftapi.setHttpAddressPort(swiftport.getText());
			}
			if (!swifturl.getText().toLowerCase().matches("^\\w+://.*")) {
				swifturl.setText("https://" + swifturl.getText());
				swiftapi.setUrl(swifturl.getText());
			}
			break;
		default:
			System.out.println("Api type needs to be http or https");

		}
		// Add protocol and port after entity is updated - ToDo
		try {
			ApiBiz.ApiCreate(swiftapi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to create Swift Api");
			e.printStackTrace();
		}
	}

	@FXML
	public void deleteSwiftClicked(ActionEvent event) {
		System.out.println("Delete Swift button clicked");
	}
}
