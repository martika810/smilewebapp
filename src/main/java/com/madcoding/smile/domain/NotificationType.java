package com.madcoding.smile.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum NotificationType {
	VOTE("VOTE"),FRIEND("FRIEND"),MESSAGE("MESSAGE");
	
	private final String value;
	
	private NotificationType(String value){
		this.value = value;
	}
	
	public static NotificationType fromValue(String value){
		if(value != null){
			for(NotificationType notificationType: values()){
				if(notificationType.value.equals(value)){
					return notificationType;
				}
			}
		}
		return null;
	}
	
	public String toValue(){
		return value;
	}
	

}
