package com.googledrive.model;

public class Photo{
	
	private String fileId;
	
	private String image;
	
	private String content;
	
	
	
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		System.out.println("_______________________________");
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	
}