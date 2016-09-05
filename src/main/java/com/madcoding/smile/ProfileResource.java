package com.madcoding.smile;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.madcoding.smile.domain.Profile;
import com.madcoding.smile.helpers.ProfileHelper;
import com.madcoding.smile.services.CacheDataProviderContextListener;
import com.madcoding.smile.services.FriendListProvider;
import com.madcoding.smile.services.PhotoToVoteProvider;
import com.madcoding.smile.services.ProfilePhotoProvider;
import com.madcoding.smile.services.RankPhotoProvider;
import com.madcoding.smile.store.ProfileStore;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/profiles")
public class ProfileResource {

	@POST
	public Response createProfile(Profile profile){
		Profile insertedProfile = ProfileStore.getinstance().insert(profile);
		CacheDataProviderContextListener.getInstance().updateCache();
		return Response.ok(new Gson().toJson(insertedProfile)).build();
		
	}
	
	@GET
	@Path("/byowner")
	public Response loadByOwnerId(@QueryParam("owner") String ownerId){
		Profile profile = ProfileStore.getinstance().loadProfileByUserId(ownerId);
		return Response.ok(new Gson().toJson(profile)).build();
	}
	
	@PUT
	@Path("/addfriend")
	public Response updateFriend(@QueryParam("userid") String userId,@QueryParam("friendid") String friendId){
		Profile updatedProfile = ProfileHelper.getInstance().addFriendToProfile(userId, friendId);
		return Response.ok(new Gson().toJson(updatedProfile)).build();
	}
	
	@PUT
	@Path("/updatelocation")
	public Response updateLocation(Profile profile){
		Profile updatedProfile = ProfileHelper.getInstance().updateProfileLocation(profile);
		return Response.ok(new Gson().toJson(updatedProfile)).build();
	}
	

	@PUT
	@Path("/updateprofile")
	public Response updateProfile(Profile profile){
		Profile updatedProfile = ProfileHelper.getInstance().updateProfile(profile);
		return Response.ok(new Gson().toJson(updatedProfile)).build();
	}
}
