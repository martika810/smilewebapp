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
import com.madcoding.smile.cache.TokenCache;
import com.madcoding.smile.domain.Friend;
import com.madcoding.smile.domain.Profile;
import com.madcoding.smile.domain.TokenKey;
import com.madcoding.smile.domain.User;
import com.madcoding.smile.helpers.ProfileHelper;
import com.madcoding.smile.helpers.UserHelper;
import com.madcoding.smile.store.ProfileStore;
import com.madcoding.smile.store.UserStore;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserResource {

	@GET
	public Response getAllUsers() {
		List<User> users = UserStore.getInstance().loadAllUsers();

		return Response.ok(new Gson().toJson(users)).build();
	}
	
	@Path("/login")
	@POST 
	public Response login(User user){
		try{
			boolean isUserValid = UserHelper.getInstance().isValidUser(user);
			if(isUserValid){
				user = UserStore.getInstance().loadByEmail(user.getEmail());
				String generatedToken = UserHelper.getInstance().generateToken(user.getId());
				TokenKey token = TokenCache.getInstance().getToken(generatedToken);
				return Response.ok(new Gson().toJson(token)).build();
			}else{
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}
		}
		catch(Exception ex){
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
	}
	
	@GET
	@Path("/searchfriend")
	public Response searchFriend(@QueryParam("email") String email){
		Friend foundFriend = UserHelper.getInstance().findFriendByEmail(email);
		return Response.ok(new Gson().toJson(foundFriend)).build();
		
	}
	
	@POST
	public Response create(User user){
		try{
			
			User hashedPassUser = UserHelper.getInstance().populateUser(user);
			user = UserStore.getInstance().insertUser(hashedPassUser);
			Profile newProfile = ProfileHelper.getInstance().createDefaultProfile(user.getId(), null);
			ProfileStore.getinstance().insert(newProfile);
			String generatedToken = UserHelper.getInstance().generateToken(user.getId());
			TokenKey token = TokenCache.getInstance().getToken(generatedToken);
			return Response.ok(new Gson().toJson(token)).build();
			
		}catch(Exception ex){
			return Response.notModified().build();
		}
		
	}

}
