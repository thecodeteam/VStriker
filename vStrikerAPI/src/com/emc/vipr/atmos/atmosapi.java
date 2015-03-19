package com.emc.vipr.atmos;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.emc.atmos.api.AtmosApi;
import com.emc.atmos.api.AtmosConfig;
import com.emc.atmos.api.ObjectId;
import com.emc.atmos.api.jersey.AtmosApiClient;
import com.emc.atmos.api.ObjectIdentifier;
import com.emc.atmos.api.ObjectPath;
import com.emc.atmos.api.bean.Metadata;
import com.emc.atmos.api.bean.ObjectEntry;
import com.emc.atmos.api.request.*;
import com.emc.atmos.api.bean.CreateObjectResponse;
import java.io.*;
public class atmosapi {
	
	
	 public static AtmosApi getAtmosApi(String UID, String SECRET,String ENDPOINT) throws URISyntaxException {
	        AtmosConfig config = new AtmosConfig( UID, SECRET, new URI( ENDPOINT ) );
	        return new AtmosApiClient( config );
	    }

	 
	   public static ObjectId CreateObject(String UID, String SECRET,String ENDPOINT,String key, File fis,String bucket) throws Exception {
	        
		   AtmosApi atmos = getAtmosApi( UID, SECRET,ENDPOINT);
		   ObjectIdentifier identifier = new ObjectPath(bucket+"/"+key);
		   CreateObjectRequest request = new CreateObjectRequest();
		   // builder style (optional) keeps parameters short and concise
		   InputStream is = new FileInputStream(fis);
		   request.content(is).userMetadata( new Metadata( key,"1", true ) ).setIdentifier(identifier);

		   CreateObjectResponse reponse= atmos.createObject(request);
		   //return atmos.createObject(identifier , fis,null);
		   ObjectId id =reponse.getObjectId();

		   return id;
	    }
	   
	   public static long UpdateObject(String UID, String SECRET,String ENDPOINT,String key, InputStream content, String oid) throws Exception {

		   long t=0;
		   long startTime = System.nanoTime();
		   AtmosApi atmos = getAtmosApi( UID, SECRET,ENDPOINT);
		   long endTime = System.nanoTime();

		   t=endTime-startTime;

		   ListObjectsRequest request = new ListObjectsRequest().metadataName( key );
		   List<ObjectEntry> results = atmos.listObjects( request ).getEntries();
		   // while there are more pages, keep getting them
		   while ( request.getToken() != null )
			   results.addAll( atmos.listObjects( request ).getEntries() );

		   System.out.println( String.format( "Objects tagged with \"%s\"", key ) );
		   for ( ObjectEntry entry : results ) {
			   System.out.println( entry.getObjectId() );
			   atmos.updateObject(new ObjectId(oid), content);
			   endTime = System.nanoTime();
			   t=t+endTime-startTime;
		   }

		   return t;
	    }
	   
	   public static long DeleteObject(String UID, String SECRET,String ENDPOINT,String key,  String oid) throws Exception {
	       long t=0;
		   long startTime = System.nanoTime();
		   AtmosApi atmos = getAtmosApi( UID, SECRET,ENDPOINT);
		   long endTime = System.nanoTime();

		   t=endTime-startTime;

		   ListObjectsRequest request = new ListObjectsRequest().metadataName( key );
		   List<ObjectEntry> results = atmos.listObjects( request ).getEntries();
		   // while there are more pages, keep getting them
		   while ( request.getToken() != null )
			   results.addAll( atmos.listObjects( request ).getEntries() );

		   System.out.println( String.format( "Objects tagged with \"%s\"", key ) );
		   for ( ObjectEntry entry : results ) {
			   System.out.println( entry.getObjectId() );
			   startTime = System.nanoTime();
			   atmos.delete(entry.getObjectId() );
			   endTime = System.nanoTime();
			   t=t+endTime-startTime;
		   }

		   return t;

	    }
	   
	   public static String ReadStringObject(String UID, String SECRET,String ENDPOINT,String key,  String oid ) throws Exception {
	        
		   AtmosApi atmos = getAtmosApi( UID, SECRET,ENDPOINT);
		   ListObjectsRequest request = new ListObjectsRequest().metadataName( key );
		   List<ObjectEntry> results = atmos.listObjects( request ).getEntries();
		   // while there are more pages, keep getting them
		   while ( request.getToken() != null )
			   results.addAll( atmos.listObjects( request ).getEntries() );

		   System.out.println( String.format( "Objects tagged with \"%s\"", key ) );
		   for ( ObjectEntry entry : results ) {
			   System.out.println( entry.getObjectId() );

			   return atmos.readObject( new ObjectId( oid ),String.class );
		   }

		   return null;
	    }
}
