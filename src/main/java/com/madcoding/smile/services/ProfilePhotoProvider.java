package com.madcoding.smile.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.madcoding.smile.domain.Photo;
import com.madcoding.smile.helpers.PhotoHelper;
import com.madcoding.smile.store.PhotoStore;

public class ProfilePhotoProvider {
	
private volatile boolean updated = false;
	
	private Map<String,List<Photo>> profilePhotoMap;
	
	private ProfilePhotoProvider(){
		profilePhotoMap = new HashMap<String, List<Photo>>();
	}
	
	private static ProfilePhotoProvider INSTANCE = null;
	public static ProfilePhotoProvider getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ProfilePhotoProvider();
		}
		return INSTANCE;
	}
	
	public void addProfilePhoto(String userId){
		profilePhotoMap.put(userId, PhotoStore.getInstance().findPhotosByOwner(userId));
	}
	
	public List<Photo> getProfilePhotos(String userId){
		return profilePhotoMap.get(userId);
	}
	
	public Photo getMainProfilePhoto(String userId){
		if(profilePhotoMap.get(userId)!=null && !profilePhotoMap.get(userId).isEmpty()){
			return profilePhotoMap.get(userId).get(0);
		}else{
			return null;
		}
	}
	
	public boolean isUpdated(){
		return updated;
	}
	
	public synchronized void setUpdated(boolean updated){
		this.updated = updated;
	}


}
