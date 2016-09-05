package com.madcoding.smile.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Photo extends Identifiable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8540521443255959466L;
	private String url ;
	private String owner ;
	private int numberVotes;
	private String comment;
	private Date created;
	
	private Photo(){}
	private Photo(String url,String owner,int numVotes,String comment){
		this.setUrl(url);
		this.setOwner(owner);
		this.setNumberVotes(numVotes);
		this.setComment(comment);
		this.created = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
	}
	
	private Photo(String id,String url,String owner,int numVotes,String  comment){
		this.setId(id);
		this.setUrl(url);
		this.setOwner(owner);
		this.setNumberVotes(numVotes);
		this.setComment(comment);
		this.created = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();
	}
	
	public static Photo makeInstance(){
		return new Photo();
	}
	
	public static Photo makeInstance(String url,String owner,int numberVotes,String comment){
		return new Photo(url, owner,numberVotes,comment);
	}
	
	public static Photo makeInstance(String id,String url,String owner,int numberVotes,String comment){
		return new Photo(id, url, owner,numberVotes,comment);
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public int getNumberVotes() {
		return numberVotes;
	}
	public void setNumberVotes(int numberVotes) {
		this.numberVotes = numberVotes;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created=created;
	}


	@Override
	public String toString() {
		
		return new StringBuffer().append(" _id: ").append(this.getId())
				.append(" url: ").append(this.url)
				.append(" password: ").append(this.owner).toString();
	}
	
	@Override
	public boolean equals(Object anotherPhoto) {
		return this.getId().equals(((Photo)anotherPhoto).getId());
	}
	
	
}
