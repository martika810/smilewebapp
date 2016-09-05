package com.madcoding.smile.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

@XmlRootElement
public class User extends Identifiable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3996944364914489818L;
//	private String id = Constants.NULL_ID;
	private String email;
	private String password;
	private String pepper;
	
	public User(String username,String password){
		this.setEmail(username);
		this.setPassword(password);
	}
	
	public User(String id,String email,String password,String pepper){
		this.setId(id);
		this.setEmail(email);
		this.setPassword(password);
		this.setPepper(pepper);
	}

	public User() {
	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPepper() {
		return pepper;
	}

	public void setPepper(String pepper) {
		this.pepper = pepper;
	}


	@Override
	public String toString() {
		
		return new StringBuffer().append(" _id: ").append(this.getId())
				.append(" email: ").append(this.email)
				.append(" password: ").append(this.password).toString();
	}

	@Override
	public User clone() {
		
		return new User(this.getId(),this.getEmail(),this.getPassword(),this.getPepper());
	}

	

}
