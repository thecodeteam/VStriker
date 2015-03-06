package com.emc.vipr.swift;

import org.javaswift.joss.client.factory.AccountConfig;
import org.javaswift.joss.client.factory.AccountFactory;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class swiftapi {

	public static Account ViPRSwiftClient(String username,String password,String dataNode)throws Exception
	{
		
		   AccountConfig config = new AccountConfig();  
		              config.setUsername(username);  
		               config.setPassword(password);  
		               config.setAuthUrl(dataNode + "/v2.0/tokens");  
		               config.setDisableSslValidation(true);  
		               Account account = new AccountFactory(config).createAccount();  
		   return account;
		
	}

	public static void CreateObject(String username,String passwod,String dataNode, String container, String key, InputStream content)throws Exception
	{
		
		Account client= ViPRSwiftClient(username, passwod, dataNode);
		Container myContainer=client.getContainer(container);
		StoredObject object = myContainer.getObject(key);
	    object.uploadObject(content);

	}

	public static InputStream ReadObject(String username,String passwod,String dataNode, String container, String key)throws Exception
	{
		
		Account client= ViPRSwiftClient(username, passwod, dataNode);
		Container myContainer=client.getContainer(container);
		StoredObject object = myContainer.getObject(key);
	    return object.downloadObjectAsInputStream();

	}
	
	public static void DeleteObject(String username,String passwod,String dataNode, String container, String key)throws Exception
	{
		
		Account client= ViPRSwiftClient(username, passwod, dataNode);
		Container myContainer=client.getContainer(container);
		StoredObject object = myContainer.getObject(key);
	    object.delete();
	}

	
}
