package com.madcoding.smile.store;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.madcoding.smile.domain.Location;
import com.madcoding.smile.domain.Profile;
import com.madcoding.smile.domain.ProfileScope;

public class ProfileStoreTest {
	public static final String USER_ID_1 = "1";
	public static final String USER_ID_2 = "2";
	public static final String USER_ID_3 = "3";
	public static final String USER_ID_21 = "21";
	public static final String USER_ID_22 = "22";
	public static final String USER_ID_31 = "31";

	List<String> friendsF1;
	List<String> friendsF2;
	List<String> friendsF3;
	List<String> friendsF21;
	List<String> friendsF22;
	List<String> friendsF31;
	Profile profile1;
	Profile profile2;
	Profile profile3;
	Profile profile21;
	Profile profile22;
	Profile profile31;

	@Before
	public void setUp() {
		friendsF1 = new LinkedList<String>();
		friendsF1.add(USER_ID_2);
		friendsF1.add(USER_ID_3);
		friendsF2 = new LinkedList<String>();
		friendsF2.add(USER_ID_21);
		friendsF2.add(USER_ID_22);
		friendsF2.add(USER_ID_1);
		friendsF3 = new LinkedList<String>();
		friendsF3.add(USER_ID_1);
		friendsF3.add(USER_ID_31);
		friendsF21 = new LinkedList<String>();
		friendsF21.add(USER_ID_2);
		friendsF22 = new LinkedList<String>();
		friendsF22.add(USER_ID_2);
		friendsF31 = new LinkedList<String>();
		friendsF31.add(USER_ID_3);
		
		profile1 = new Profile(ProfileScope.FRIENDS_SECOND_LINE, ProfileScope.FRIENDS_SECOND_LINE,
				ProfileScope.FRIENDS_SECOND_LINE, friendsF1, new Location(0, 0), USER_ID_1);
		profile2 = new Profile(ProfileScope.FRIENDS_SECOND_LINE, ProfileScope.FRIENDS_SECOND_LINE,
				ProfileScope.FRIENDS_SECOND_LINE, friendsF2, new Location(0, 0), USER_ID_2);
		profile3 = new Profile(ProfileScope.FRIENDS_SECOND_LINE, ProfileScope.FRIENDS_SECOND_LINE,
				ProfileScope.FRIENDS_SECOND_LINE, friendsF3, new Location(0, 0), USER_ID_3);
		profile21 = new Profile(ProfileScope.FRIENDS_SECOND_LINE, ProfileScope.FRIENDS_SECOND_LINE,
				ProfileScope.FRIENDS_SECOND_LINE, friendsF21, new Location(0, 0), USER_ID_21);
		profile22 = new Profile(ProfileScope.FRIENDS_SECOND_LINE, ProfileScope.FRIENDS_SECOND_LINE,
				ProfileScope.FRIENDS_SECOND_LINE, friendsF22, new Location(0, 0), USER_ID_22);
		profile31 = new Profile(ProfileScope.FRIENDS_SECOND_LINE, ProfileScope.FRIENDS_SECOND_LINE,
				ProfileScope.FRIENDS_SECOND_LINE, friendsF31, new Location(0, 0), USER_ID_31);
	}

	@Test
	public void testInsertAndDelete() {
		Profile insertedProfile = ProfileStore.getinstance().insert(profile1);
		Profile loadedProfile = ProfileStore.getinstance().loadById(insertedProfile.getId());
		assertTrue(insertedProfile.equals(loadedProfile));
		
		ProfileStore.getinstance().delete(insertedProfile.getId());
		Profile deletedProfile = ProfileStore.getinstance().loadById(insertedProfile.getId());
		assertTrue(deletedProfile == null);
	}

}
