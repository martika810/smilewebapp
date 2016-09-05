package com.madcoding.smile.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PhotoWrapper {
	private String id;
	private Photo photo;
	
	
	public PhotoWrapper() {}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Photo getPhoto() {
		return photo;
	}


	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
	

}
