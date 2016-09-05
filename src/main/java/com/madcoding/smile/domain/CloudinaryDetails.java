package com.madcoding.smile.domain;

import javax.xml.bind.annotation.XmlRootElement;

import com.madcoding.smile.conf.SmileProperties;

@XmlRootElement
public class CloudinaryDetails {
	private String name = SmileProperties.getInstance().getProperty("cloudinary_name");;
	private String apiKey =  SmileProperties.getInstance().getProperty("cloudinary_api_key");
	
	public CloudinaryDetails(){
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getApiKey(){
		return this.apiKey;
	}


	
}
