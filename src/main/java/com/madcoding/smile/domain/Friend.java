package com.madcoding.smile.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Friend {
	String photoProfileUrl;
	String userId;
	String email;
	
	public Friend(){
		
	}

	public String getPhotoProfileUrl() {
		return photoProfileUrl;
	}

	public void setPhotoProfileUrl(String photoProfileUrl) {
		this.photoProfileUrl = photoProfileUrl;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public static Friend makeInstance(User user,Photo photo){
		Friend friend = new Friend();
		friend.email = user.getEmail();
		friend.userId = user.getId();
		if(photo!=null){
			friend.photoProfileUrl = photo.getUrl();
		}else{
			friend.photoProfileUrl = "";
		}
		return friend;
	}
	

}
