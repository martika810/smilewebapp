package com.madcoding.smile;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.madcoding.smile.domain.Photo;
import com.madcoding.smile.domain.RankedPhoto;
import com.madcoding.smile.helpers.PhotoHelper;
import com.madcoding.smile.services.CacheDataProviderContextListener;
import com.madcoding.smile.services.PhotoToVoteProvider;
import com.madcoding.smile.services.ProfilePhotoProvider;
import com.madcoding.smile.services.RankPhotoProvider;
import com.madcoding.smile.store.PhotoStore;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/photos")
public class PhotoResource {

	@GET
	@Path("/byowner")
	public Response findPhotosByOwner(@QueryParam("owner") String ownerId) {

		List<Photo> photoList = ProfilePhotoProvider.getInstance().getProfilePhotos(ownerId);
		return Response.ok(new Gson().toJson(photoList)).build();

	}

	@GET
	@Path("/tovote")
	public Response findPhotosToVote(@QueryParam("userid") String userId) {

		List<Photo> photos = PhotoToVoteProvider.getInstance().getPhotos(userId);
		return Response.ok(new Gson().toJson(photos)).build();
	}

	@GET
	@Path("/rank")
	public Response findRankPhotos(@QueryParam("userid") String userId) {
		List<Photo> photos = RankPhotoProvider.getInstance().getRankPhoto(userId);
		List<RankedPhoto> rankedPhotos = new ArrayList<RankedPhoto>();
		if (photos != null) {
			for (Photo photo : photos) {
				RankedPhoto rankedPhoto = new RankedPhoto(photos.indexOf(photo) + 1, photo);
				rankedPhotos.add(rankedPhoto);
			}
		}
		return Response.ok(new Gson().toJson(rankedPhotos)).build();
	}

	@POST
	public Response create(Photo photo) {

		Photo insertedPhoto = PhotoStore.getInstance().insert(photo);
		CacheDataProviderContextListener.getInstance().updateCache();
		return Response.ok(new Gson().toJson(insertedPhoto)).build();
	}

	@POST
	@Path("/bunch")
	public Response createBunchPhotos(List<Photo> photos) {
		List<Photo> createdPhotoList = new ArrayList<Photo>();
		for (Photo photo : photos) {
			Photo createdPhoto = PhotoStore.getInstance().insert(photo);
			createdPhotoList.add(createdPhoto);
		}
	
		CacheDataProviderContextListener.getInstance().updateCache();
		return Response.ok(new Gson().toJson(createdPhotoList)).build();
	}
	
	@PUT 
	public Response updatePhotoComment(Photo photo){
		Photo updatedPhoto = PhotoHelper.getInstance().updatePhotoComment(photo);
		CacheDataProviderContextListener.getInstance().updateCache();
		return Response.ok(new Gson().toJson(updatedPhoto)).build();
	}

	@DELETE
	public Response delete(Photo photo) {
		PhotoStore.getInstance().deletePhoto(photo);
		return Response.ok().build();
	}

}
