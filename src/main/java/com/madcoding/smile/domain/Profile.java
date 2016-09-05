package com.madcoding.smile.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Profile extends Identifiable{
	
	private String name;
	private String lastName;
	private ProfileScope scopeToBeVoted;
	private ProfileScope scopeToVote;
	private ProfileScope scopeRankList;
	private List<String> friends;
	private Location location;
	private String ownerId = Constants.NULL_ID;
	
	public Profile(){
		setFriends(new LinkedList<String>());
	}
	
	
	public Profile(ProfileScope scopeToBeVoted, ProfileScope scopeToVote, ProfileScope scopeRankList,
			List<String> friends, Location location, String ownerId) {
		this();
		this.name="";
		this.lastName="";
		this.scopeToBeVoted = scopeToBeVoted;
		this.scopeToVote = scopeToVote;
		this.scopeRankList = scopeRankList;
		this.friends.addAll(friends);
		this.location = location;
		this.ownerId = ownerId;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getLastName(){
		return this.lastName;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}


	public ProfileScope getScopeToBeVoted() {
		return scopeToBeVoted;
	}

	public void setScopeToBeVoted(ProfileScope scopeToBeVoted) {
		this.scopeToBeVoted = scopeToBeVoted;
	}

	public ProfileScope getScopeToVote() {
		return scopeToVote;
	}

	public void setScopeToVote(ProfileScope scopeToVote) {
		this.scopeToVote = scopeToVote;
	}
	
	public ProfileScope getScopeRankList() {
		return scopeRankList;
	}

	public void setScopeRankList(ProfileScope scopeRankList) {
		this.scopeRankList = scopeRankList;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	

}
