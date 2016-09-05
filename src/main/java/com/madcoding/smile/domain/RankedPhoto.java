package com.madcoding.smile.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RankedPhoto {
	private int position;
	private Photo photo;
	
	public RankedPhoto() {
		
	}

	public RankedPhoto(int position, Photo photo) {
		this.position = position;
		this.photo = Photo.makeInstance(photo.getId(), photo.getUrl(), photo.getOwner(),photo.getNumberVotes(),photo.getComment());
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	
	

}
