package com.madcoding.smile.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.madcoding.smile.domain.Photo;
import com.madcoding.smile.helpers.PhotoHelper;

public class PhotoToVoteProvider {
	
	private volatile boolean updated = false;
	
	private Map<String,List<Photo>> photosToVote;
	private static PhotoToVoteProvider INSTANCE = null;
	private PhotoToVoteProvider(){
		photosToVote = new HashMap<String,List<Photo>>();
	}
	
	public static PhotoToVoteProvider getInstance(){
		if(INSTANCE == null){
			INSTANCE = new PhotoToVoteProvider();
		}
		return INSTANCE;
	}
	
	
	public void addPhotosToUser(String userId){
		photosToVote.put(userId,PhotoHelper.getInstance().findPhotosToVote(userId));
	}
	
	public List<Photo> getPhotos(String userId){
		
		List<Photo> listPhotos = photosToVote.get(userId);
		if(listPhotos == null){
			listPhotos = PhotoHelper.getInstance().findPhotosToVote(userId);
		}
		return listPhotos;
	}
	
	public boolean isUpdated(){
		return updated;
	}
	
	public synchronized void setUpdated(boolean updated){
		this.updated = updated;
	}

}
