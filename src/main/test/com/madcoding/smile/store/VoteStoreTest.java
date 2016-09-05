package com.madcoding.smile.store;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import com.madcoding.smile.domain.Vote;
import com.madcoding.smile.store.VoteStore;

public class VoteStoreTest {
	public final static String USER_ID_1 = "1";
	public final static String USER_ID_2 = "2";
	public final static String USER_ID_3 = "3";
	public final static String PHOTO_ID_1 = "11";
	public final static String PHOTO_ID_2 = "22";
	public final static String PHOTO_ID_3 = "33";
	public final static String PHOTO_ID_4 = "44";
	Vote vote11 = Vote.makeInstance("11",USER_ID_1, PHOTO_ID_1);
	Vote vote12 = Vote.makeInstance("12",USER_ID_1, PHOTO_ID_2);
	Vote vote13 = Vote.makeInstance("13",USER_ID_1, PHOTO_ID_3);
	Vote vote14 = Vote.makeInstance("14",USER_ID_1, PHOTO_ID_4);
	Vote vote21 = Vote.makeInstance("21",USER_ID_2, PHOTO_ID_1);
	
	
	@Test
	public void testInsertAndDelete(){
		
		Vote insertedVote = VoteStore.getInstance().insert(vote11);
		Vote lodedVote = VoteStore.getInstance().loadVote(insertedVote.getId());
		assertTrue(insertedVote.equals(lodedVote));
		
		VoteStore.getInstance().delete(vote11.getId());
		Vote loadedVote = VoteStore.getInstance().loadVote(vote11.getId());
		assertTrue(loadedVote==null);
		
		
	}
	
	@Test
	public void testFindPhotosByPhotoId(){
		VoteStore.getInstance().insert(vote11);
		VoteStore.getInstance().insert(vote21);
		
		int foundedVotes = VoteStore.getInstance().findVotesByPhotoId(PHOTO_ID_1);
		assertTrue(foundedVotes == 2);
		VoteStore.getInstance().delete(vote11.getId());
		VoteStore.getInstance().delete(vote21.getId());
	}
	
	@Test
	public void testFindVotesByUserId(){
		VoteStore.getInstance().insert(vote11);
		VoteStore.getInstance().insert(vote12);
		
		List<Vote> foundedVotes = VoteStore.getInstance().findVotesByUserId(USER_ID_1);
		assertTrue(foundedVotes.contains(vote11));
		assertTrue(foundedVotes.contains(vote12));
		VoteStore.getInstance().delete(vote11.getId());
		VoteStore.getInstance().delete(vote12.getId());
	}
	
	@Test
	public void testFindVotedPhotos(){
	    VoteStore.getInstance().insert(vote11);
		VoteStore.getInstance().insert(vote12);
		VoteStore.getInstance().insert(vote13);
		List<String> photoIds = new ArrayList<String>();
		photoIds.add(vote11.getVotedPhotoId());
		photoIds.add(vote12.getVotedPhotoId());
		photoIds.add(vote13.getVotedPhotoId());
		photoIds.add(vote14.getVotedPhotoId());
		List<Vote> listVoted = VoteStore.getInstance().findVotedPhotos(photoIds, USER_ID_1);
		assertTrue(listVoted.contains(vote11));
		assertTrue(listVoted.contains(vote12));
		assertTrue(listVoted.contains(vote13));
		assertFalse(listVoted.contains(vote14));
		
		VoteStore.getInstance().delete(vote11.getId());
		VoteStore.getInstance().delete(vote12.getId());
		VoteStore.getInstance().delete(vote13.getId());
	}
	
	

}
