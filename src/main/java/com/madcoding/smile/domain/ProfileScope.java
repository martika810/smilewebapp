package com.madcoding.smile.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ProfileScope {
	
	FRIENDS_FIRST_LINE("FRIENDS_FIRST_LINE"),FRIENDS_SECOND_LINE("FRIENDS_SECOND_LINE"),LOCATION("LOCATION");
	
	private final String value;
	
	private ProfileScope(String value){
		this.value = value;
	}
	
	public static ProfileScope fromValue(String value){
		if(value != null){
			for(ProfileScope scope: values()){
				if(scope.value.equals(value)){
					return scope;
				}
			}
		}
		return null;
	}
	
	public String toValue(){
		return value;
	}
	
	

}
