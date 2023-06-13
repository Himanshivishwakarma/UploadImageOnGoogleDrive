package com.googledrive.controller;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.googledrive.model.Photo;
import com.googledrive.service.FileUploadService;

@RestController
public class FileUploadController{
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@PostMapping("/api/add")
	public ResponseEntity<String> FileUploadOnGoogleDrive(@RequestBody Photo photo) throws IOException, GeneralSecurityException{
		
		String fileId = this.fileUploadService.uploadFileOnGoogleDrive(photo);
		return new ResponseEntity<String>(fileId,HttpStatus.CREATED);
	}
}