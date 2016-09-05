package com.madcoding.smile.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.madcoding.smile.domain.Profile;
import com.madcoding.smile.domain.ProfileScope;
import com.madcoding.smile.store.ProfileStore;
import com.madcoding.smile.utils.CollectionUtils;

public class FriendListProvider {
	
	private boolean updated = false;
	
	private Map<String,List<String>> friendListMap;
	private static FriendListProvider INSTANCE = null;
	
	private FriendListProvider(){
		friendListMap = new HashMap<String,List<String>>();
	}
	public static FriendListProvider getInstance(){
		if(INSTANCE == null){
			INSTANCE = new FriendListProvider();
		}
		return INSTANCE;
	}
	
	public void addFriendList(String userId){
		Profile profile = ProfileStore.getinstance().loadProfileByUserId(userId);
		
		boolean isScopeRankFirstLine = profile.getScopeRankList().equals(ProfileScope.FRIENDS_FIRST_LINE);
		boolean isScopeRankSecondLine = profile.getScopeRankList().equals(ProfileScope.FRIENDS_SECOND_LINE);
		boolean isScopeRankLocation = profile.getScopeRankList().equals(ProfileScope.LOCATION);
		if(isScopeRankFirstLine){
			friendListMap.put(userId, profile.getFriends());
		}else if(isScopeRankSecondLine){
			Set<String> friendSet = ProfileStore.getinstance().findProfileSecondLineFriend(profile).keySet();
			List<String> friendList = CollectionUtils.convertSetToList(friendSet);
			friendListMap.put(userId, friendList);
			
		}else if(isScopeRankLocation){
			Set<String> friendSet = ProfileStore.getinstance().findUserIdsByLocation(profile, 5);
			List<String> friendList = CollectionUtils.convertSetToList(friendSet);
			friendListMap.put(userId, friendList);
		}
	}
	
	public List<String> getFriendList(String userId){
		return friendListMap.get(userId);
	}
	
	public boolean isUpdated(){
		return updated;
	}
	
	public synchronized void setUpdated(boolean updated){
		this.updated = updated;
	}

}
