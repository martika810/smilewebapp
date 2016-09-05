package com.madcoding.smile.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.madcoding.smile.domain.Photo;
import com.madcoding.smile.helpers.PhotoHelper;

public class RankPhotoProvider {
	
	private volatile boolean updated = false;
	
	private Map<String,List<Photo>> rankPhotoMap;
	private RankPhotoProvider(){
		rankPhotoMap = new HashMap<String, List<Photo>>();
	}
	
	private static RankPhotoProvider INSTANCE = null;
	public static RankPhotoProvider getInstance(){
		if(INSTANCE == null){
			INSTANCE = new RankPhotoProvider();
		}
		return INSTANCE;
	}
	
	public void addRankPhoto(String userId){
		rankPhotoMap.put(userId, PhotoHelper.getInstance().findRankPhotos(userId));
	}
	
	public List<Photo> getRankPhoto(String userId){
		return rankPhotoMap.get(userId);
	}
	
	public boolean isUpdated(){
		return updated;
	}
	
	public synchronized void setUpdated(boolean updated){
		this.updated = updated;
	}

	
}
