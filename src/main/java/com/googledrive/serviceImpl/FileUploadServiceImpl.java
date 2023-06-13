package com.googledrive.serviceImpl;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.services.drive.model.File;
import org.springframework.stereotype.Service;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import com.googledrive.model.Photo;
import com.googledrive.service.FileUploadService;
import com.google.api.client.http.InputStreamContent;
import java.util.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;

@Service
public class FileUploadServiceImpl implements FileUploadService{

	 private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	 
	   private static  final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	 
	 private static final String TOKENS_DIRECTORY_PATH = "tokens";
	 private static final List<String> SCOPES =
		      Collections.singletonList(DriveScopes.DRIVE);
		  private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";

	  
	  /**
	   * Creates an authorized Credential object.
	   *
	   * @param HTTP_TRANSPORT The network HTTP Transport.
	   * @return An authorized Credential object.
	   * @throws IOException If the credentials.json file cannot be found.
	   */
	  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
		      throws IOException {
		    // Load client secrets.
		    InputStream in = FileUploadServiceImpl.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		    if (in == null) {
		      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		    }
		    GoogleClientSecrets clientSecrets =
		        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		    // Build flow and trigger user authorization request.
		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
		        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
		        .setAccessType("offline")
		        .build();
		    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		    //returns an authorized Credential object.
		    return credential;
		  }
	@Override
	public String uploadFileOnGoogleDrive(Photo photo) throws IOException, GeneralSecurityException {
	
		
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	        .setApplicationName(APPLICATION_NAME)
	        .build();
	    byte[] bI = org.apache.commons.codec.binary.Base64.decodeBase64((photo.getImage().substring(photo.getImage().indexOf(",")+1)).getBytes());

	    InputStream inputStream = new ByteArrayInputStream(bI);
	    InputStreamContent mediaContent=new InputStreamContent("image/jpeg", inputStream);
	    mediaContent.setLength(bI.length);
	      File fileMetadata = new File();
	      fileMetadata.setName("done.jpg");
	      File file = service.files().create(fileMetadata , mediaContent).setFields("id").execute();
	       String fileId="StudentImage"+file.getId();
	       System.out.println("File Upload on google drive successfully.");
	       return fileId;

	}
}
	
