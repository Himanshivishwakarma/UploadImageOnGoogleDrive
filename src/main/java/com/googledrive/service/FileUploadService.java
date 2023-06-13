package com.googledrive.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.googledrive.model.Photo;

public interface FileUploadService{
	
	public String uploadFileOnGoogleDrive(Photo photo) throws IOException, GeneralSecurityException;
	
}