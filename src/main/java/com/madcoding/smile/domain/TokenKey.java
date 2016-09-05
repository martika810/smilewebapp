package com.madcoding.smile.domain;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TokenKey {
	
	private String userId;
	private Calendar expiryDate;
	private String token;
	
	private TokenKey(String userId,Calendar expiryDate,String token){
		this.userId=userId;
		this.expiryDate = expiryDate;
		this.token =token;
	}
	
	public static TokenKey makeInstance(String userId){
		Calendar expiryDate = Calendar.getInstance();
		expiryDate.add(Calendar.HOUR, 2);
		
		String generatedToken = UUID.randomUUID().toString();
		return new TokenKey(userId,expiryDate,generatedToken);
	}
	
	public boolean isValid(){
		
		return Calendar.getInstance().compareTo(this.expiryDate)<0;
		
	}
	
	public void extendToken(){
		this.expiryDate.add(Calendar.MINUTE, 2);
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}
	
	public Calendar getExpiryDate(){
		return expiryDate;
	}
	


}
