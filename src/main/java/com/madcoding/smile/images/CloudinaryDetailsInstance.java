package com.madcoding.smile.images;

import com.madcoding.smile.conf.SmileProperties;
import com.madcoding.smile.domain.CloudinaryDetails;

public class CloudinaryDetailsInstance {
	private static CloudinaryDetailsInstance INSTANCE = null;
	private CloudinaryDetails details;
	private CloudinaryDetailsInstance(){
		details = new CloudinaryDetails();
	}
	
	public static CloudinaryDetailsInstance getInstance(){
		if(INSTANCE == null){
			INSTANCE = new CloudinaryDetailsInstance();
		}
		return INSTANCE;
	}
	
	public CloudinaryDetails getDetails(){
		return this.details;
	}


}
