package com.madcoding.smile.helpers;

import com.madcoding.smile.domain.Location;
import com.madcoding.smile.domain.Profile;
import com.madcoding.smile.domain.ProfileScope;
import com.madcoding.smile.services.CacheDataProviderContextListener;
import com.madcoding.smile.store.ProfileStore;

public class ProfileHelper {
	
	private static ProfileHelper INSTANCE = null;
	private ProfileHelper(){}
	
	public static ProfileHelper getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ProfileHelper();
		}
		return INSTANCE;
	}
	
	public Profile createDefaultProfile(String userId,Location location){
		Profile profile = new Profile();
		profile.setOwnerId(userId);
		if(location == null){
			profile.setLocation(new Location());
		}else{
			profile.setLocation(location);
		}
		profile.setScopeRankList(ProfileScope.FRIENDS_SECOND_LINE);
		profile.setScopeToBeVoted(ProfileScope.FRIENDS_SECOND_LINE);
		profile.setScopeToVote(ProfileScope.FRIENDS_SECOND_LINE);
		return profile;
	}
	
	public Profile addFriendToProfile(String userId,String friendId){
		Profile profile = ProfileStore.getinstance().loadProfileByUserId(userId);
		if(!profile.getFriends().contains(friendId)){
			profile.getFriends().add(friendId);
		}
		ProfileStore.getinstance().update(profile);
		
		Profile friendProfile = ProfileStore.getinstance().loadProfileByUserId(friendId);
		if(!friendProfile.getFriends().contains(userId)){
			friendProfile.getFriends().add(userId);
		}
		ProfileStore.getinstance().update(friendProfile);
		
		CacheDataProviderContextListener.getInstance().updateCache();
		return profile;
	}
	
	public Profile updateProfileLocation(Profile profile){
		Profile updatedProfile = ProfileStore.getinstance().loadProfileByUserId(profile.getOwnerId());
		updatedProfile.setLocation(new Location(profile.getLocation()));
		ProfileStore.getinstance().update(updatedProfile);
		CacheDataProviderContextListener.getInstance().updateCache();
		return updatedProfile;
	}
	
	public Profile updateProfile(Profile profile){
		Profile updatedProfile = ProfileStore.getinstance().loadProfileByUserId(profile.getOwnerId());
		updatedProfile.setName(profile.getName());
		updatedProfile.setLastName(profile.getLastName());
		updatedProfile.setScopeToBeVoted(profile.getScopeToBeVoted());
		updatedProfile.setScopeRankList(profile.getScopeRankList());
		ProfileStore.getinstance().update(updatedProfile);
		CacheDataProviderContextListener.getInstance().updateCache();
		return updatedProfile;
	}

}
