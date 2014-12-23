package com.emc.vipr.atmos;

import java.net.URI;
import java.net.URISyntaxException;


import com.emc.atmos.api.AtmosApi;
import com.emc.atmos.api.AtmosConfig;
import com.emc.atmos.api.jersey.AtmosApiClient;
import java.io.InputStream;




import com.emc.atmos.api.ObjectId;

public class atmosapi {
	
	
	 public static AtmosApi getAtmosApi(String UID, String SECRET,String ENDPOINT) throws URISyntaxException {
	        AtmosConfig config = new AtmosConfig( UID, SECRET, new URI( ENDPOINT ) );
	        return new AtmosApiClient( config );
	    }

	 
	   public static ObjectId CreateObject(String UID, String SECRET,String ENDPOINT,String key, InputStream content, String contentType) throws Exception {
	        
		   AtmosApi atmos = getAtmosApi( UID, SECRET,ENDPOINT);
	        return atmos.createObject( content, contentType );
	    }
	   
	   public static void UpdateObject(String UID, String SECRET,String ENDPOINT,String key, InputStream content, String oid) throws Exception {
	        
		   AtmosApi atmos = getAtmosApi( UID, SECRET,ENDPOINT);
		   
	        atmos.updateObject( new ObjectId( oid ), content );
	    }
	   
	   public static void DeleteObject(String UID, String SECRET,String ENDPOINT,String key,  String oid) throws Exception {
	        
		   AtmosApi atmos = getAtmosApi( UID, SECRET,ENDPOINT);
		   
	        atmos.delete( new ObjectId( oid ) );
	    }
	   
	   public static String ReadStringObject(String UID, String SECRET,String ENDPOINT,String key,  String oid ) throws Exception {
	        
		   AtmosApi atmos = getAtmosApi( UID, SECRET,ENDPOINT);
		   
	       return atmos.readObject( new ObjectId( oid ),String.class );
	    }
}
