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

public class AtmosController {
	@FXML
	private Button backtoAccount;
	@FXML
	private Button validateAtmos;
	@FXML
	private Button saveAtmos;
	@FXML
	private TextField atmosuser;
	@FXML
	private TextField atmosurl;
	@FXML
	private TextField atmossecretkey;
	@FXML
	private TextField atmosbucket;
	@FXML
	private ChoiceBox<String> chooseprotocol;
	@FXML
	private TextField atmosport;

	private VStriker vStriker;
	private Account acct;
	private Api api;

	// Constructor
	public AtmosController() {
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker, Account validAcct) {
		this.vStriker = vStriker;
		this.acct = validAcct;
	}

	public void updateAtmosApi(VStriker vStriker, Account validAcct, Api api) {
		this.vStriker = vStriker;
		this.acct = validAcct;
		this.api = api;
		atmosuser.setText(api.getSubtenant());
		atmosurl.setText(api.getUrl());
		atmossecretkey.setText(api.getSecretKey());
		atmosbucket.setText(api.getBucket());
		chooseprotocol.setValue(api.getUrl().contains("https") ? "https"
				: "http");
		atmosport.setText(api.getUrl().contains("https") ? api
				.getHttpsAddressPort() : api.getHttpAddressPort());
		saveAtmos.setText("Update");
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In AtmosController initialize");
		chooseprotocol.setItems(FXCollections.observableArrayList("http",
				"https"));
		chooseprotocol.setValue("http");
		atmosuser.setPromptText("User");
		atmosurl.setPromptText("URL to service");
		atmossecretkey.setPromptText("Secret key");
		atmosbucket.setPromptText("Bucket name");
		atmosport.setPromptText("Port number");
	}

	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showHome();
	}

	@FXML
	public void validateAtmosClicked(ActionEvent event) {
		System.out.println("Validate Atmos button clicked");
		if (atmosuser.getText() == null || atmosuser.getText().length() == 0
				|| atmosurl.getText() == null
				|| atmosurl.getText().length() == 0
				|| atmossecretkey.getText() == null
				|| atmossecretkey.getText().length() == 0) {
			System.out.println("Please set Access Key, URL and Secret Key");
			return;
		}
		Engine engine = new VEngine();
		if (engine.validateAtmosConnection(atmosuser.getText(),
				atmossecretkey.getText(), atmosurl.getText(), null)) {
			System.out.println("Atmos connection is validated");
		} else {
			System.out.println("Atmos connection is not working");
		}
	}

	@FXML
	public void saveAtmosClicked(ActionEvent event) throws Exception {
		System.out.println("Save/Update Atmos button clicked");

		if (atmosuser.getText() == null || atmosuser.getText().length() == 0
				|| atmosurl.getText() == null
				|| atmosurl.getText().length() == 0
				|| atmossecretkey.getText() == null
				|| atmossecretkey.getText().length() == 0
				|| atmosbucket.getText() == null
				|| atmosbucket.getText().length() == 0) {
			System.out
					.println("Please set Access Key, URL, Secret Key and Bucket name");
			return;
		}
		if (saveAtmos.getText().equals("Update")) {
			// Update the existing api object
			api.setSubtenant(atmosuser.getText());
			api.setUrl(atmosurl.getText());
			api.setSecretKey(atmossecretkey.getText());
			api.setBucket(atmosbucket.getText());
			switch (chooseprotocol.getValue()) {
			case "https":
				api.setHttpsAddressPort(atmosport.getText());
				break;
			case "http":
				api.setHttpAddressPort(atmosport.getText());
				break;
			default:
				System.out.println("Api type needs to be http or https");
			}
			ApiBiz.ApiUpdate(api);

		} else {
			Api atmosapi = new Api();
			atmosapi.setAccount(acct);
			atmosapi.setSubtenant(atmosuser.getText());
			atmosapi.setUrl(atmosurl.getText());
			atmosapi.setSecretKey(atmossecretkey.getText());
			atmosapi.setBucket(atmosbucket.getText());

			atmosapi.setHttpAddressIp("tobechanged");
			// This should change setProtocol
			// s3api.setHttpAddressPort("999"); // This should change to setPort
			// s3api.setApiTypeId(2); // This depends on protocol - if protocol
			// is
			// http
			// or https

			List<vStrikerEntities.ApiType> apitypelist = vStrikerBizModel.ApiTypeBiz
					.ApiTypeSelectAll();

			switch (chooseprotocol.getValue().toString()) {
			case "http":
				for (vStrikerEntities.ApiType a : apitypelist) {
					if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("http"))) {
						atmosapi.setApiType(a);
						break;
					}
				}
				if (atmosport.getText() == null
						|| atmosport.getText().length() == 0) {
					atmosport.setText("80");
					atmosapi.setHttpAddressPort("80");
				} else {
					atmosapi.setHttpAddressPort(atmosport.getText());
				}
				if (!atmosurl.getText().toLowerCase().matches("^\\w+://.*")) {
					atmosurl.setText("http://" + atmosurl.getText());
					atmosapi.setUrl(atmosurl.getText());
				}
				break;
			case "https":
				for (vStrikerEntities.ApiType a : apitypelist) {
					if ((a.getApiTypeName().equalsIgnoreCase("Atmos"))
							&& (a.getApiTypeUrl().equalsIgnoreCase("https"))) {
						atmosapi.setApiType(a);
						break;
					}
				}
				if (atmosport.getText() == null
						|| atmosport.getText().length() == 0) {
					atmosport.setText("443");
					atmosapi.setHttpAddressPort("443");
				} else {
					atmosapi.setHttpAddressPort(atmosport.getText());
				}
				if (!atmosurl.getText().toLowerCase().matches("^\\w+://.*")) {
					atmosurl.setText("https://" + atmosurl.getText());
					atmosapi.setUrl(atmosurl.getText());
				}
				break;
			default:
				System.out.println("Api type needs to be http or https");

			}
			try {
				ApiBiz.ApiCreate(atmosapi);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Failed to create Atmos Api");
				e.printStackTrace();
			}
		} // end else
	}

	@FXML
	public void deleteAtmosClicked(ActionEvent event) {
		System.out.println("Delete Atmos button clicked");
		try {
			ApiBiz.ApiDelete(api.getApiId());
			atmosuser.clear();
			atmosurl.clear();
			atmossecretkey.clear();
			atmosbucket.clear();
			atmosport.clear();
			saveAtmos.setText("Save");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
