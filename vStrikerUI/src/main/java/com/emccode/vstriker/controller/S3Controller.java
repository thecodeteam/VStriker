package com.emccode.vstriker.controller;

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

public class S3Controller {
	@FXML
	private Button backtoAccount;
	@FXML
	private Button validateS3;
	@FXML
	private Button saveS3;
	@FXML
	private TextField s3accesskey;
	@FXML
	private TextField s3url;
	@FXML
	private TextField s3secretkey;
	@FXML
	private TextField s3bucket;
	@FXML
	private ChoiceBox<String> chooseProtocol;
	@FXML
	private TextField port;


	private VStriker vStriker;
	private Account acct;

	// Constructor
	public S3Controller() {
	}

	// Set the main application
	public void setVStrikerApp(VStriker vStriker, Account validAcct) {
		this.vStriker = vStriker;
		this.acct = validAcct;
	}

	// Initialize
	@FXML
	private void initialize() {
		System.out.println("In S3Controller initialize");
		chooseProtocol.setItems(FXCollections.observableArrayList("http", "https"));
		chooseProtocol.setValue("http");
		s3accesskey.setPromptText("Access key");
		s3url.setPromptText("URL to service");
		s3secretkey.setPromptText("Secret key");
		s3bucket.setPromptText("Bucket name");
		port.setPromptText("Port number");
	}
	
	@FXML
	public void backtoAccountClicked(ActionEvent event) {
		System.out.println("Back to Accounts button clicked");
		vStriker.showHome();
	}

	@FXML
	public void validateS3Clicked(ActionEvent event) {
		System.out.println("Validate S3 button clicked");
		/*
		 * Read these variables from the screen - ToDo String S3_ACCESS_KEY_ID =
		 * "user045"; String S3_SECRET_KEY =
		 * "vd2bty66GwFjJxB34VHFEBgEJ/b8QWDwnAdA1zjg"; String S3_ENDPOINT =
		 * "http://object.vipronline.com"; String S3_ViPR_NAMESPACE = null;
		 */
		String S3_ViPR_NAMESPACE = null;
		if (s3accesskey.getText() == null
				|| s3accesskey.getText().length() == 0
				|| s3url.getText() == null || s3url.getText().length() == 0
				|| s3secretkey.getText() == null
				|| s3secretkey.getText().length() == 0) {
			System.out.println("Please set Access Key, URL and Secret Key");
			return;
		}
		Engine engine = new VEngine();
		if (engine.validateS3Connection(s3accesskey.getText(),
				s3secretkey.getText(), s3url.getText(), S3_ViPR_NAMESPACE)) {
			System.out.println("S3 connection is validated");
		} else {
			System.out.println("S3 connection is not working");
		}
	}
	
	@FXML
	public void saveS3Clicked(ActionEvent event) {
		System.out.println("Save S3 button clicked");
		if (s3accesskey.getText() == null
				|| s3accesskey.getText().length() == 0
				|| s3url.getText() == null || s3url.getText().length() == 0
				|| s3secretkey.getText() == null
				|| s3secretkey.getText().length() == 0
				|| s3bucket.getText() == null
				|| s3bucket.getText().length() == 0) {
			System.out.println("Please set Access Key, URL, Secret Key and Bucket name");
			return;
		}
		Api s3api = new Api();
		s3api.setAccountId((int) acct.getAccountId());
		s3api.setSubtenant(s3accesskey.getText());
		s3api.setUrl(s3url.getText());
		s3api.setSecretKey(s3secretkey.getText());
		s3api.setBucket(s3bucket.getText());
		s3api.setHttpAddressIp("tobechanged"); // This should change setProtocol
		s3api.setHttpAddressPort("999"); // This should change to setPort
		s3api.setApiTypeId(2); // This depends on protocol - if protocol is http or https 
		//Add protocol and port after entity is updated - ToDo
		try {
			ApiBiz.ApiCreate(s3api);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to create S3 Api");
			e.printStackTrace();
		}
	}
}
