package com.madcoding.smile.store;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.madcoding.smile.domain.Photo;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class PhotoStore extends Store {
	
	private static PhotoStore INSTANCE = null;
	private final static String PHOTOS_TABLE_NAME = "photos";
	private final static String FIELD_ID = "_id";
	private final static String FIELD_URL = "url";
	private final static String FIELD_OWNER = "owner";
	private final static String FIELD_NUMBER_VOTES = "numberVotes";
	private final static String FIELD_COMMENT = "comment";
	private final static String FIELD_CREATED = "created";
	private final static int LIMIT_QUERY = 30;
	
	public static PhotoStore getInstance(){
		
		if(INSTANCE == null){
			INSTANCE = new PhotoStore();
		}
		return INSTANCE;
		
	}
	
	public List<Photo> findPhotosByOwner(String ownerId){
		
		BasicDBObject whereQuery =  new BasicDBObject(FIELD_OWNER,ownerId);
		DBCursor dbPhotoCursor = getCollection(PHOTOS_TABLE_NAME).find(whereQuery);
		dbPhotoCursor.sort(new BasicDBObject(FIELD_NUMBER_VOTES,-1)).limit(LIMIT_QUERY);//descending
	
		List<Photo> photoList = makePhotos(dbPhotoCursor);
		
		return photoList;
		
	}
	
	public List<Photo> findPhotosByBunchOfOwner(List<String> ownerIdsList){
		BasicDBObject whereOwnerClause = buildWhereOwnerIdsClause(ownerIdsList);
		DBCursor dbPhotoCursor = getCollection(PHOTOS_TABLE_NAME).find(whereOwnerClause).limit(LIMIT_QUERY);
		
		List<Photo> photoList = makePhotos(dbPhotoCursor);
		return photoList;		
		
	}
	
	public List<Photo> findPhotosByBunchOfOwnerOrderByVotes(List<String> userIds){
		BasicDBObject whereOwnerClause = buildWhereOwnerIdsClause(userIds);
		DBCursor dbPhotoCursor = getCollection(PHOTOS_TABLE_NAME).find(whereOwnerClause);
		dbPhotoCursor.sort(new BasicDBObject(FIELD_NUMBER_VOTES,-1)).limit(LIMIT_QUERY);//descending
		List<Photo> photoList = makePhotos(dbPhotoCursor);
		return photoList;
		
	}
	
	private BasicDBObject buildWhereOwnerIdsClause(List<String> ownerIdsList){
		BasicDBObject finalWhereClause = new BasicDBObject();
		if(ownerIdsList == null ) return finalWhereClause;
		BasicDBList orList = new BasicDBList();
		for(String ownerId : ownerIdsList){
			BasicDBObject ownerClause = new BasicDBObject(FIELD_OWNER,ownerId);
			orList.add(ownerClause);
		}
		
		if(!ownerIdsList.isEmpty()){
			finalWhereClause.put("$or",orList);
		}
		return finalWhereClause;
	}
	public Photo insert(Photo photo){
		
		photo.setCreated(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime());
		photo.setNumberVotes(0);
		BasicDBObject dbPhotoObject = makeDBPhotoObject(photo);
		getCollection(PHOTOS_TABLE_NAME).insert(dbPhotoObject);
		photo.setId(((ObjectId)dbPhotoObject.get(FIELD_ID)).toString());
		return photo;
		
	}
	
	public void updatePhoto(Photo photo){
		BasicDBObject updateVote = makeDBPhotoObject(photo);
		BasicDBObject searchByIdClause = new BasicDBObject(FIELD_ID,new ObjectId(photo.getId()));
		getCollection(PHOTOS_TABLE_NAME).update(searchByIdClause, updateVote,false,false);
	}
	
	public void increaseVote(String photoId){
		Photo photoToUpdate = loadById(photoId);
		photoToUpdate.setNumberVotes(photoToUpdate.getNumberVotes()+1);
		updatePhoto(photoToUpdate);
	}
	
	public Photo loadById(String photoId){
		BasicDBObject whereQuerById = new BasicDBObject(FIELD_ID,new ObjectId(photoId));
		DBObject dbPhoto = getCollection(PHOTOS_TABLE_NAME).findOne(whereQuerById);
		return makePhoto(dbPhoto);
	}
	
	public void deletePhoto(Photo photo){
		
		BasicDBObject whereQueryById = new BasicDBObject(FIELD_ID,new ObjectId(photo.getId()));
		getCollection(PHOTOS_TABLE_NAME).remove(whereQueryById);
		
	}
	
	private List<Photo> makePhotos(DBCursor dbPhotoCursor){
		List<Photo> photoList = new ArrayList<Photo>();
		while(dbPhotoCursor.hasNext()){
			DBObject dbPhotoObject = dbPhotoCursor.next();
			photoList.add(makePhoto(dbPhotoObject));
		}
		return photoList;
	}
	
	private Photo makePhoto(DBObject obj){
		
		Photo photo = Photo.makeInstance();
		photo.setId(((ObjectId)obj.get(FIELD_ID)).toString());
		photo.setUrl((String)obj.get(FIELD_URL));
		photo.setOwner((String)obj.get(FIELD_OWNER));
		photo.setNumberVotes((Integer)obj.get(FIELD_NUMBER_VOTES));
		photo.setComment((String)obj.get(FIELD_COMMENT));
		photo.setCreated(new DateTime(obj.get(FIELD_CREATED)).toDate());
		return photo;
		
	}
	
	private BasicDBObject makeDBPhotoObject(Photo photo){
		
		BasicDBObject dbPhotoObject = new BasicDBObject();
		dbPhotoObject.put(FIELD_URL,photo.getUrl());
		dbPhotoObject.put(FIELD_OWNER, photo.getOwner());
		dbPhotoObject.put(FIELD_NUMBER_VOTES, photo.getNumberVotes());
		dbPhotoObject.put(FIELD_COMMENT, photo.getComment());
		dbPhotoObject.put(FIELD_CREATED,photo.getCreated());
		return dbPhotoObject;
		
	}

}
