package com.madcoding.smile.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Vote extends Identifiable {
	
	private String voterUserId;
	private String votedPhotoId;
	
	private Vote(){}
	
	private Vote(String voterEmail,String votedPhotoId){
		this.setVoterUserId(voterUserId);
		this.setVotedPhotoId(votedPhotoId);
	}
	
	private Vote(String id,String voterUserId,String votedPhotoId){
		this.setId(id) ;
		this.setVoterUserId(voterUserId);
		this.setVotedPhotoId(votedPhotoId);
	}
	
	public static Vote makeInstance(){
		return new Vote();
	}
	
	public static Vote makeInstance(String voterUserId,String votedPhotoId){
		return new Vote(voterUserId,votedPhotoId);
	}
	
	public static Vote makeInstance(String id,String voterUserId,String votedPhotoId){
		return new Vote(id,voterUserId,votedPhotoId);
	}


	public String getVoterUserId() {
		return voterUserId;
	}

	public void setVoterUserId(String voterUserId) {
		this.voterUserId = voterUserId;
	}

	public String getVotedPhotoId() {
		return votedPhotoId;
	}

	public void setVotedPhotoId(String votedPhotoId) {
		this.votedPhotoId = votedPhotoId;
	}
	
	@Override
	public boolean equals(Object anotherVote) {
		return this.getId().equals(((Vote)anotherVote).getId()) &&
			   this.getVoterUserId().equals(((Vote)anotherVote).getVoterUserId()) &&
			   this.getVotedPhotoId().equals(((Vote)anotherVote).getVotedPhotoId());
	}
	

}
