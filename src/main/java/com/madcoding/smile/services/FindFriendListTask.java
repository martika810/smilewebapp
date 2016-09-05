package com.madcoding.smile.services;

import com.madcoding.smile.domain.Profile;
import com.madcoding.smile.store.ProfileStore;

public class FindFriendListTask implements Runnable{
	private String userId;
	private Callback endCallback;
	
	public FindFriendListTask(String userId,Callback endCallback) {
		this.userId = userId;
		this.endCallback = endCallback;
		
	}
	@Override
	public void run() {
		Profile userProfileToUpdate = ProfileStore.getinstance().loadProfileByUserId(userId);
		FriendListProvider.getInstance().addFriendList(userId);
		
		for(String friend:userProfileToUpdate.getFriends()){
			FriendListProvider.getInstance().addFriendList(friend);
		}
		
		endCallback.callback();
		
	}

}
