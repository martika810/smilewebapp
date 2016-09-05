package com.madcoding.smile;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.madcoding.smile.domain.Vote;
import com.madcoding.smile.helpers.VoteHelper;
import com.madcoding.smile.services.CacheDataProviderContextListener;
import com.madcoding.smile.services.PhotoToVoteProvider;
import com.madcoding.smile.services.ProfilePhotoProvider;
import com.madcoding.smile.services.RankPhotoProvider;
import com.madcoding.smile.store.PhotoStore;
import com.madcoding.smile.store.VoteStore;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/votes")
public class VoteResource {
	
	@Path("/countbyphoto")
	@GET
	public Response findNumberVotesToPhoto(@QueryParam ("photoid") String photoId){
		int numberVotes = VoteStore.getInstance().findVotesByPhotoId(photoId);
		return Response.ok(numberVotes).build();
	}
	
	@Path("/byuser")
	@GET
	public Response findVotesByUser(@QueryParam("userid") String userId){
		List<Vote> voteList = VoteStore.getInstance().findVotesByUserId(userId);
		return Response.ok(new Gson().toJson(voteList)).build();
	}
	
	@POST
	public Response create(Vote vote){
		Vote insertedVote = VoteStore.getInstance().insert(vote);
		PhotoStore.getInstance().increaseVote(vote.getVotedPhotoId());
		CacheDataProviderContextListener.getInstance().updateCache();
		return Response.ok(new Gson().toJson(insertedVote)).build();			
		
	}
	
	@POST
	@Path("/bunch")
	public Response createBunchVotes(List<Vote> votes){
		VoteHelper.getInstance().createVotes(votes);
		return Response.ok(new Gson().toJson(votes)).build();
	}

}
