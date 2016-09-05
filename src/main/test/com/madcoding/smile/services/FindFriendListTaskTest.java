package com.madcoding.smile.services;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.madcoding.smile.domain.Profile;
import com.madcoding.smile.store.ProfileStore;

public class FindFriendListTaskTest {
	//mself and jack can be close by location, otherwise the test would fail
	public static final String USER_ID_MYSELF = "56017587d4c6582e377d2d99";
	public static final String USER_ID_JACK_NICH_ID = "5601ae61d4c6f82c5beead00";

	private void deleteFriendRelation(){
		
		Profile profile =ProfileStore.getinstance().loadProfileByUserId(USER_ID_MYSELF);
		if(profile.getFriends().contains(USER_ID_JACK_NICH_ID)){
			int indexToRemove =profile.getFriends().indexOf(USER_ID_JACK_NICH_ID);
			profile.getFriends().remove(indexToRemove);
			ProfileStore.getinstance().update(profile);
			Profile jackProfile =ProfileStore.getinstance().loadProfileByUserId(USER_ID_JACK_NICH_ID);
			indexToRemove =jackProfile.getFriends().indexOf(USER_ID_MYSELF);
			jackProfile.getFriends().remove(indexToRemove);
						
		}
	}
	
	@Test
	public void test() {
		deleteFriendRelation();
		new Thread(new FindFriendListTask(USER_ID_MYSELF, new Callback() {
			
			@Override
			public void callback() {
				List<String> friends =FriendListProvider.getInstance().getFriendList(USER_ID_MYSELF);
				List<String> jackFriends =FriendListProvider.getInstance().getFriendList(USER_ID_JACK_NICH_ID);
				assertTrue(friends==null || !friends.contains(USER_ID_JACK_NICH_ID));
				assertTrue(jackFriends==null || !jackFriends.contains(USER_ID_MYSELF));
				
			}
		})).run();;
		
	}

}
