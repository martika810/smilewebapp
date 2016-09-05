package com.madcoding.smile.resources;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.madcoding.smile.PhotoResource;
import com.madcoding.smile.domain.Photo;
import com.madcoding.smile.services.PhotoToVoteProvider;
import com.madcoding.smile.services.ProfilePhotoProvider;
import com.madcoding.smile.services.RankPhotoProvider;

import java.lang.reflect.Type;

import javax.ws.rs.core.Response;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhotoResourceTest {
	public static final String USER_ID_MYSELF = "56017587d4c6582e377d2d99";
	public static final String USER_ID_JACK_NICH_ID = "5601ae61d4c6f82c5beead00";
	public static final String USER_ID_CHRIS_EVANS = "56096f33d4c688f1364b0872";
	public static final String USER_ID_ANGELINA = "5611767ad4c686b7e3654137";
	public static final String USER_ID_BRAD_PITT = "56124ca2d4c6e813731be070";
	public PhotoResource photoResource = new PhotoResource();
	private Photo photo1;
	private Photo photo2;
	private Photo photo3;
	private Photo photo4;
	private Photo createdPhoto1;
	private Photo createdPhoto2;
	private Photo createdPhoto3;
	private Photo createdPhoto4;
	private Type type;
	
	@Before
	public void setUp() {
		
		type = new TypeToken<List<Photo>>() {}.getType();
		photo1 = Photo.makeInstance();
		photo1.setUrl("https://res.cloudinary.com/dwckrucnb/image/upload/v1444154726/wzmpubnrivvlmh0ercfa.jpg");
		photo1.setOwner(USER_ID_MYSELF);
		
		photo2 = Photo.makeInstance();
		photo2.setUrl("https://res.cloudinary.com/dwckrucnb/image/upload/v1444055301/s7uopon5sehpw96xlcpt.jpg");
		photo2.setOwner(USER_ID_MYSELF);
		
		photo3 = Photo.makeInstance();
		photo3.setUrl("https://res.cloudinary.com/dwckrucnb/image/upload/v1444055269/zazs8n2kipl6fea41sey.png");
		photo3.setOwner(USER_ID_MYSELF);
		
		photo4 = Photo.makeInstance();
		photo4.setUrl("https://res.cloudinary.com/dwckrucnb/image/upload/v1444045316/x0yzpas0lvhf01vsjvoe.jpg");
		photo4.setOwner(USER_ID_ANGELINA);
		
	}

	@Test
	public void test1_InsertedPhotosAreAvailableInPhotoToVoteProvider() throws InterruptedException {
		
//		List<Photo> photoList = new ArrayList<Photo>();
//		photoList.add(photo1);
//		photoList.add(photo2);
//		Response response = photoResource.createBunchPhotos(photoList);
//		String reponseString = (String) response.getEntity();
//		Type type = new TypeToken<List<Photo>>() {
//		}.getType();
//		List<Photo> createdPhotos = new Gson().fromJson(reponseString, type);
//		Thread.sleep(2000);
//
//		List<Photo> photosToVoteForJack = PhotoToVoteProvider.getInstance().getPhotos(USER_ID_JACK_NICH_ID);
//		assertTrue(photosToVoteForJack.contains(createdPhotos.get(0)));
//		assertTrue(photosToVoteForJack.contains(createdPhotos.get(1)));
//		createdPhoto1=createdPhotos.get(0);
//		createdPhoto2=createdPhotos.get(1);
//		
//		
//		photoList.clear();
//		photoList.add(photo3);
//		Response response2 = photoResource.createBunchPhotos(photoList);
//		List<Photo> secondCreatedPhoto = new Gson().fromJson((String)response2.getEntity(),type);
//		photosToVoteForJack = PhotoToVoteProvider.getInstance().getPhotos(USER_ID_JACK_NICH_ID);
//		assertTrue(photosToVoteForJack.contains(secondCreatedPhoto.get(0)));
//		createdPhoto3=secondCreatedPhoto.get(0);
//		
//		
//		photoList.clear();
//		photoList.add(photo4);
//		response = photoResource.createBunchPhotos(photoList);
//		List<Photo> thirdCreatedPhoto = new Gson().fromJson((String)response.getEntity(),type);
//		photosToVoteForJack = PhotoToVoteProvider.getInstance().getPhotos(USER_ID_JACK_NICH_ID);
//		assertTrue(photosToVoteForJack.contains(thirdCreatedPhoto.get(0)));
//		createdPhoto4 = thirdCreatedPhoto.get(0);
//		
//		response =photoResource.findPhotosByOwner(USER_ID_MYSELF);
//		List<Photo> profilePhotos = new Gson().fromJson((String)response.getEntity(), type);
//		assertTrue(profilePhotos.size() == 3);
//		assertTrue(profilePhotos.contains(createdPhoto1));
//		assertTrue(profilePhotos.contains(createdPhoto2));
//		assertTrue(profilePhotos.contains(createdPhoto3));
		
	}
	
	@Test
	public void test2_FindPhotosByOwner(){
//		Response response =photoResource.findPhotosByOwner(USER_ID_MYSELF);
//		List<Photo> profilePhotos = new Gson().fromJson((String)response.getEntity(), type);
//		assertTrue(profilePhotos.size() == 3);
//		assertTrue(profilePhotos.contains(createdPhoto1));
//		assertTrue(profilePhotos.contains(createdPhoto2));
//		assertTrue(profilePhotos.contains(createdPhoto3));
//		
//		Response responseAngelina =photoResource.findPhotosByOwner(USER_ID_ANGELINA);
//		List<Photo> profilePhotosAngelina = new Gson().fromJson((String)responseAngelina.getEntity(), type);
//		assertTrue(profilePhotosAngelina.size() == 1);
//		assertTrue(profilePhotos.contains(createdPhoto4));
		
	}
	
	@Test
	public void test3_FindRankPhotos(){
//		Response response = photoResource.findRankPhotos(USER_ID_ANGELINA);
//		List<Photo> rankPhotos = new Gson().fromJson((String)response.getEntity(), type);
//		assertTrue(rankPhotos.contains(createdPhoto1));
//		assertTrue(rankPhotos.contains(createdPhoto2));
//		assertTrue(rankPhotos.contains(createdPhoto3));
	}
	
	

}
