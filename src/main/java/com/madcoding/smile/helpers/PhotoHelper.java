package com.madcoding.smile.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.madcoding.smile.domain.Photo;
import com.madcoding.smile.domain.Profile;
import com.madcoding.smile.domain.ProfileScope;
import com.madcoding.smile.domain.Vote;
import com.madcoding.smile.services.FriendListProvider;
import com.madcoding.smile.store.PhotoStore;
import com.madcoding.smile.store.ProfileStore;
import com.madcoding.smile.store.VoteStore;
import com.madcoding.smile.utils.CollectionUtils;

public class PhotoHelper {

	private static PhotoHelper INSTANCE = null;
	public static final int DISTANCE_LIMIT = 25;
	private PhotoHelper() {
	}

	public static PhotoHelper getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PhotoHelper();
		}
		return INSTANCE;
	}

	public List<Photo> findPhotosToVote(String userId) {
		Profile profile = ProfileStore.getinstance().loadProfileByUserId(userId);
		
		List<String> userIdsToGrabPhotos = findVisibleUserIds(profile);
		
		List<Photo> photosToVote = PhotoStore.getInstance().findPhotosByBunchOfOwner(userIdsToGrabPhotos);
		Map<String, Photo> mapPhotosToVoteById = CollectionUtils.convertListToMap(photosToVote);
		List<Photo> finalPhotoList = removeVotedPhotos(mapPhotosToVoteById, userId);
		return finalPhotoList;
	}
	
	public List<Photo> findRankPhotos(String userId){
		List<String> friendList = FriendListProvider.getInstance().getFriendList(userId);
		List<Photo> rankPhotos = PhotoStore.getInstance().findPhotosByBunchOfOwnerOrderByVotes(friendList);
		return rankPhotos;
	}

	private List<String> findVisibleUserIds(Profile profile) {

		
		Set<String> userIdToGrabPhotos = new HashSet<String>();
		if (profile != null) {
			userIdToGrabPhotos = ProfileStore.getinstance().findUserIdsSecondLineFriends(profile);
			if(userIdToGrabPhotos.size() < 20){
				
				int distance = 5;
				boolean isUserIdsNotFull  = userIdToGrabPhotos.size() < 20;
				boolean hasDistanceNotReachedLimit = distance < DISTANCE_LIMIT;
				while( isUserIdsNotFull && hasDistanceNotReachedLimit){
					Set<String> userIdsFoundByLocation = ProfileStore.getinstance().findUserIdsByLocation(profile,distance);
					userIdToGrabPhotos.addAll(userIdsFoundByLocation);
					distance = distance + 5;
					isUserIdsNotFull  = userIdToGrabPhotos.size() < 20;
					hasDistanceNotReachedLimit = distance < DISTANCE_LIMIT;
				}
			}
		}
		
		return CollectionUtils.convertSetToList(userIdToGrabPhotos);
	}

	private List<Photo> removeVotedPhotos(Map<String, Photo> photosToVoteById, String userId) {

		List<Photo> photosWithoutVotedOnes = new ArrayList<Photo>();
		List<String> photoIdsList = CollectionUtils.convertSetToList(photosToVoteById.keySet());
		List<Vote> votes = VoteStore.getInstance().findVotedPhotos(photoIdsList, userId);
		List<String> votedPhotosIds = extractPhotoIdsFromVotes(votes);
		for (String photoId : photoIdsList) {
			if (!votedPhotosIds.contains(photoId)) {
				photosWithoutVotedOnes.add(photosToVoteById.get(photoId));
			}
		}
		return photosWithoutVotedOnes;

	}

	private List<String> extractPhotoIdsFromVotes(List<Vote> votes) {
		List<String> photoIds = new ArrayList<String>();
		for (Vote vote : votes) {
			photoIds.add(vote.getVotedPhotoId());
		}
		return photoIds;
	}
	
	public Photo updatePhotoComment(Photo photo){
		Photo updatedPhoto = PhotoStore.getInstance().loadById(photo.getId());
		updatedPhoto.setComment(photo.getComment());
		PhotoStore.getInstance().updatePhoto(updatedPhoto);
		return updatedPhoto;
	}

}
