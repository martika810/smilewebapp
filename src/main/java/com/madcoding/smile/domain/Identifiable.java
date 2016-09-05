package com.madcoding.smile.domain;


public abstract class Identifiable {
	private String id = Constants.NULL_ID;
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
}
