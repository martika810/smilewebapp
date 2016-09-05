package com.madcoding.smile.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

@XmlRootElement
public class Location {
	
	private float latitude;
	private float longitude;
	
	public Location (){
		this.setLatitude(0L);
		this.setLongitude(0L);
	}
	
	public Location(float latitude,float longitude){
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}
	
	public Location(Location anotherLocation){
		this.setLatitude(anotherLocation.latitude);
		this.setLongitude(anotherLocation.longitude);
	}
	
	public Location(String location){
		JsonObject jsonLocation;
		try{
			jsonLocation = (JsonObject) new JsonParser().parse(location);
		}catch(Exception e){
			return;
		}
		if(jsonLocation.get("coordinates")!=null){
			this.setLongitude(jsonLocation.getAsJsonArray().get(0).getAsFloat());
			this.setLatitude(jsonLocation.getAsJsonArray().get(1).getAsFloat());
		}
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public List<Float> toList(){
	
		List<Float> list = new ArrayList<Float>();
		list.add(this.longitude);
		list.add(this.latitude);
		return list;
	}
	
	

}
