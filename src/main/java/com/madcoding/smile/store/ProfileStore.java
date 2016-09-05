package com.madcoding.smile.store;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.madcoding.smile.domain.Location;
import com.madcoding.smile.domain.Profile;
import com.madcoding.smile.domain.ProfileScope;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ProfileStore extends Store{
	
	private static ProfileStore INSTANCE = null;
	
	private final static String TABLE_PROFILE_NAME = "profiles";
	private final static String FIELD_ID = "_id";
	private final static String FIELD_NAME = "name";
	private final static String FIELD_LAST_NAME = "lastname";
	private final static String FIELD_PHOTO_SCOPE = "scopetobevoted";
	private final static String FIELD_VOTE_SCOPE = "scopetovote";
	private final static String FIELD_RANK_LIST = "scoperanklist";
	private final static String FIELD_FRIENDS = "friends";
	private final static String FIELD_LOCATION = "location";
	private final static String FIELD_OWNER = "ownerid";
	private final static int MAX_QUERY = 20;
	
	public static ProfileStore getinstance(){
		if(INSTANCE == null){
			INSTANCE = new ProfileStore();
		}
		return INSTANCE;
	}
	
	private ProfileStore(){}
	
	public Profile loadProfileByUserId(String ownerUserId){
		
		BasicDBObject whereQueryByUserId = new BasicDBObject();
		whereQueryByUserId.put(FIELD_OWNER, ownerUserId);
		DBObject dbProfileObj = getCollection(TABLE_PROFILE_NAME).findOne(whereQueryByUserId);
		Profile profile = makeProfile(dbProfileObj);
		return profile;
	}
	
	public Profile loadById(String profileId){
		BasicDBObject whereClause = new BasicDBObject(FIELD_ID,new ObjectId(profileId));
		DBObject dbProfileObject = getCollection(TABLE_PROFILE_NAME).findOne(whereClause);
		return makeProfile(dbProfileObject);
	}
	
	public void update(Profile profile){
		BasicDBObject updateProfile = makeDBProfileObject(profile);
		BasicDBObject searchByIdClause = new BasicDBObject(FIELD_ID,new ObjectId(profile.getId()));
		getCollection(TABLE_PROFILE_NAME).update(searchByIdClause, updateProfile,false,false);
	}
	
	public Map<String,Profile> findProfileOfFirstLineFriend(Profile profile){
		
		Map<String,Profile> friendProfileMapByOwner = findProfiles(profile.getFriends());
		return friendProfileMapByOwner;
	}
	
	public Set<String> findUserIdsOfFirstLineFriend(Profile profile){
		
		Map<String,Profile> friendProfileMapByOwner = findProfileOfFirstLineFriend(profile);
		return friendProfileMapByOwner.keySet();
	}
	
	public Map<String,Profile> findProfileSecondLineFriend(Profile profile){
		
		Map<String,Profile> directFriendsMap = findProfileOfFirstLineFriend(profile);
		List<String> friendOfFriendsIdList = buildListFriendOfFriends(directFriendsMap);
	//	friendOfFriendsIdList.remove(profile.getOwnerId());
		Map<String,Profile> indirectFriendsMap = findProfiles(friendOfFriendsIdList);
		
		Map<String,Profile> allFriends = new HashMap<String,Profile>();
		allFriends.putAll(directFriendsMap);
		allFriends.putAll(indirectFriendsMap);
		allFriends.remove(profile.getOwnerId());
		
		return allFriends;
	}
	
	public Set<String> findUserIdsSecondLineFriends(Profile profile){
		Map<String,Profile> profileSecondLineFriends = findProfileSecondLineFriend(profile);
		return new HashSet<String>(profileSecondLineFriends.keySet());
	}
	
	public Map<String,Profile> findProfilesByLocation(Location center,int distanceKm){
		BasicDBList centerList = new BasicDBList();
		centerList.add(center.getLongitude());
		centerList.add(center.getLatitude());
		BasicDBList sphere = new BasicDBList();
		sphere.add(centerList);
		sphere.add(distanceKm / 3963.2);
		BasicDBObject sphereWhereClause = new BasicDBObject("$centerSphere", sphere);
		BasicDBObject withinWhereClause = new BasicDBObject("$geoWithin", sphereWhereClause);
		BasicDBObject query = new BasicDBObject(FIELD_LOCATION, withinWhereClause);
		DBCursor dbProfileCursor = getCollection(TABLE_PROFILE_NAME).find(query).limit(MAX_QUERY);
		Map<String,Profile> profiles = makeProfiles(dbProfileCursor);
		return profiles;
	}
	
	public Set<String> findUserIdsByLocation(Profile profile,int distanceKm){
		Map<String,Profile> profiles = findProfilesByLocation(profile.getLocation(),distanceKm);
		if(profiles==null){
			return null;
		}
		return new HashSet<String>(profiles.keySet());
	}
	
	
	
	
	
	private List<String> buildListFriendOfFriends(Map<String,Profile> friends){
		Set<String> friendsOfFriendsList = new HashSet<String>();
		for(Profile friendProfile:friends.values()){
			friendsOfFriendsList.addAll(friendProfile.getFriends());
		}
		List<String> resultList = new ArrayList<String>();
		resultList.addAll(friendsOfFriendsList);
		return resultList;
	}
	
	public Map<String,Profile> findProfiles(List<String> profileIds){
		
		BasicDBObject whereFriendClause = buildFriendWhereClause(profileIds);
		DBCursor dbFriendProfilesCursor = getCollection(TABLE_PROFILE_NAME).find(whereFriendClause);
		Map<String,Profile> friendProfileMap = makeProfiles(dbFriendProfilesCursor);
		return friendProfileMap;
		
	}
	
	public Map<String,Profile> findProfiles(List<String> profileIds,ProfileScope profileVisibility){
		//where clause
		BasicDBObject whereFriendClause = buildFriendWhereClause(profileIds);
		BasicDBObject whereVisibilityClause = new BasicDBObject(FIELD_PHOTO_SCOPE,profileVisibility.toString());
		BasicDBList andList = new BasicDBList();
		andList.add(whereFriendClause);
		andList.add(whereVisibilityClause);
		BasicDBObject whereClause = new BasicDBObject("$and",andList);
		//fetch data
		DBCursor dbFriendProfilesCursor = getCollection(TABLE_PROFILE_NAME).find(whereClause);
		Map<String,Profile> friendProfileMap = makeProfiles(dbFriendProfilesCursor);
		return friendProfileMap;
		
	}
	
	public Profile insert(Profile profile){
		BasicDBObject dbProfileObject = makeDBProfileObject(profile);
		getCollection(TABLE_PROFILE_NAME).insert(dbProfileObject);
		profile.setId(((ObjectId)dbProfileObject.get(FIELD_ID)).toString());
		return profile;
	}
	
	public void delete(String profileId){
		BasicDBObject whereClause = new BasicDBObject(FIELD_ID,new ObjectId(profileId));
		getCollection(TABLE_PROFILE_NAME).remove(whereClause);
	}
	
	private BasicDBObject buildFriendWhereClause(List<String> friends){
		BasicDBList orList = new BasicDBList();
		BasicDBObject whereClause =  new BasicDBObject();
		for(String friend:friends){
			BasicDBObject friendClause = new BasicDBObject(FIELD_OWNER,friend);
			orList.add(friendClause);
		}
		if(!orList.isEmpty()){
			whereClause.put("$or",orList);
		}
		return whereClause;
		
	}
	
	private Map<String,Profile> makeProfiles(DBCursor dbProfileCursor){
		Map<String,Profile> profileMap = new HashMap<String,Profile>();
		
		while(dbProfileCursor.hasNext()){
			DBObject dbFriendProfileObj = dbProfileCursor.next();
			Profile  friendProfile = makeProfile(dbFriendProfileObj);
			profileMap.put(friendProfile.getOwnerId(), friendProfile);
		}
		
		return profileMap;
		
	}
	
	private BasicDBObject makeDBProfileObject(Profile profile){
		BasicDBObject dbProfileObject = new BasicDBObject();
		if (profile == null) return null;
		dbProfileObject.put(FIELD_NAME,profile.getName());
		dbProfileObject.put(FIELD_LAST_NAME, profile.getLastName());
		dbProfileObject.put(FIELD_PHOTO_SCOPE, profile.getScopeToBeVoted().toValue());
		dbProfileObject.put(FIELD_VOTE_SCOPE, profile.getScopeToVote().toValue());
		dbProfileObject.put(FIELD_RANK_LIST, profile.getScopeRankList().toValue());
		dbProfileObject.put(FIELD_FRIENDS, profile.getFriends());
		dbProfileObject.put(FIELD_LOCATION, profile.getLocation().toList());
		dbProfileObject.put(FIELD_OWNER, profile.getOwnerId());
		return dbProfileObject;
	}
	
	private Profile makeProfile(DBObject profileObj){
		if(profileObj == null) return null;
		Profile profile = new Profile();
		profile.setId(((ObjectId)profileObj.get(FIELD_ID)).toString());
		profile.setName((profileObj.get(FIELD_NAME)==null)?null:(String)profileObj.get(FIELD_NAME));
		profile.setLastName((profileObj.get(FIELD_LAST_NAME)==null)?"":(String)profileObj.get(FIELD_LAST_NAME));
		ProfileScope photoScope = ProfileScope.valueOf((String)profileObj.get(FIELD_PHOTO_SCOPE));
		profile.setScopeToBeVoted(photoScope);
		ProfileScope voteScope = ProfileScope.valueOf((String)profileObj.get(FIELD_VOTE_SCOPE));
		profile.setScopeToVote(voteScope);
		ProfileScope scopeRankList = ProfileScope.valueOf((String)profileObj.get(FIELD_RANK_LIST));
		profile.setScopeRankList(scopeRankList);
		List<String> friends = makeFriendList((BasicDBList)profileObj.get(FIELD_FRIENDS));
		profile.setFriends(friends);
		Location location;
		try{
			location = makeLocation((BasicDBList)profileObj.get(FIELD_LOCATION));
		}catch(Exception e){
			location = new Location();
		}
		profile.setLocation(location);
		profile.setOwnerId((String)profileObj.get(FIELD_OWNER));
		return profile;
	}
	
	private Location makeLocation(String dbLocation){
		if (dbLocation== null) return new Location();
		JsonElement coords = new JsonParser().parse(dbLocation);
		Location location = new Location(Float.parseFloat(coords.getAsJsonArray().get(1).toString()),Float.parseFloat(coords.getAsJsonArray().get(0).toString()));
		return location;
	}
	
	private Location makeLocation(BasicDBList dbLocation){
		if (dbLocation== null) return new Location();
		Location location = new Location(Float.parseFloat(dbLocation.get(1).toString()),Float.parseFloat(dbLocation.get(0).toString()));
		return location;
	}
	
	private List<String> makeFriendList(BasicDBList dbFriendArray){
		List<String> friendIds = new LinkedList<String>();
		List<Object> objectIds = Arrays.asList(dbFriendArray.toArray());
		for(Object friendId : objectIds){
			friendIds.add(friendId.toString());
		}
		return friendIds;
	}

}
