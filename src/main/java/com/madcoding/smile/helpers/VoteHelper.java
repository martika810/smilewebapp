package com.madcoding.smile.helpers;

import java.util.List;

import com.madcoding.smile.domain.Photo;
import com.madcoding.smile.domain.Vote;
import com.madcoding.smile.services.CacheDataProviderContextListener;
import com.madcoding.smile.services.PhotoToVoteProvider;
import com.madcoding.smile.services.ProfilePhotoProvider;
import com.madcoding.smile.services.RankPhotoProvider;
import com.madcoding.smile.store.PhotoStore;
import com.madcoding.smile.store.VoteStore;

public class VoteHelper {
	
	private static VoteHelper INSTANCE = null;
	private VoteHelper(){}
	
	public static VoteHelper getInstance(){
		if(INSTANCE == null){
			INSTANCE = new VoteHelper();
		}
		return INSTANCE;
	}
	
	public void createVotes(List<Vote> votes){
		
		for(Vote vote:votes){
			VoteStore.getInstance().insert(vote);
			Photo votedPhoto = PhotoStore.getInstance().loadById(vote.getVotedPhotoId());
			NotificationHelper.getInstance().createVoteNotification(vote.getVoterUserId(), votedPhoto.getOwner(),vote.getVotedPhotoId());
			PhotoStore.getInstance().increaseVote(vote.getVotedPhotoId());
		}
		

		CacheDataProviderContextListener.getInstance().updateCache();
		
	}
}
