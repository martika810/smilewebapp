package com.madcoding.smile.store;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.madcoding.smile.domain.Constants;
import com.madcoding.smile.domain.Notification;
import com.madcoding.smile.domain.NotificationType;
import com.madcoding.smile.domain.Photo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

public class NotificationStore extends Store{
	
	private static NotificationStore INSTANCE = null;
	private final static String FIELD_ID="_id";
	private final static String FIELD_SENDER = "sender_id";
	private final static String FIELD_RECEIVER = "receivxer_id";
	private final static String FIELD_TYPE = "type";
	private final static String FIELD_TARGET = "target";
	private final static String FIELD_SEEN = "seen";
	private final static String FIELD_CREATED = "created_date";
	private final static String TABLE_NAME = "notifications";
	
	private NotificationStore (){}
	
	public static NotificationStore getInstance(){
		if(INSTANCE == null){
			INSTANCE = new NotificationStore();
		}
		return INSTANCE;
	}
	
	public List<Notification> findNotSeenNotificationsByReceiver(String userId){
		
		BasicDBObject whereQuery =  new BasicDBObject(FIELD_RECEIVER,userId);
		whereQuery.put(FIELD_SEEN, false);
		DBCursor dbNotificationCursor = getCollection(TABLE_NAME).find(whereQuery);
	
		List<Notification> notificationList = makeNotifications(dbNotificationCursor);
		
		return notificationList;
		
	}
	
	public Notification insert(Notification notification){
		try{
		BasicDBObject dbNotification = makeDBNotificationObject(notification); 
		getCollection(TABLE_NAME).insert(dbNotification);
		notification.setId(((ObjectId)dbNotification.get(FIELD_ID)).toString());
		}catch(MongoException e){
			if(e.getMessage().contains(MongoExceptionCode.DUPLICATED)){
				
			}
			e.printStackTrace();
		}
		return notification;
		
	}

	public void markAsSeen(List<String> notificationIds){
		BasicDBObject whereClause = buildIdsOrClause(FIELD_ID, notificationIds);
		BasicDBObject updateFields = new BasicDBObject(FIELD_SEEN,true);
		BasicDBObject updateClause = new BasicDBObject("$set",updateFields);
		getCollection(TABLE_NAME).update(whereClause, updateClause,false,true);
	}
	
	private List<Notification> makeNotifications(DBCursor dbNotificationCursor){
		
		List<Notification> notificationList = new ArrayList<Notification>();
		while(dbNotificationCursor.hasNext()){
			DBObject dbNotificationObject = dbNotificationCursor.next();
			notificationList.add(makeNotification(dbNotificationObject));
		}
		return notificationList;
		
	}
	
	private Notification makeNotification(DBObject obj){
		
		Notification notification = new Notification();
		notification.setId(((ObjectId)obj.get(FIELD_ID)).toString());
		notification.setSenderId((String)obj.get(FIELD_SENDER));
		notification.setReceiverId((String)obj.get(FIELD_RECEIVER));
		notification.setType(NotificationType.fromValue((String)obj.get(FIELD_TYPE)));
		notification.setTargetId((String)obj.get(FIELD_TARGET));
		notification.setSeen((Boolean)obj.get(FIELD_SEEN));
		notification.setCreated(new DateTime(obj.get(FIELD_CREATED)).toDate());
		return notification;
		
	}
	
	private BasicDBObject makeDBNotificationObject(Notification notification){
		
		BasicDBObject dbNotificationObject = new BasicDBObject();
		dbNotificationObject.put(FIELD_SENDER,notification.getSenderId());
		dbNotificationObject.put(FIELD_RECEIVER, notification.getReceiverId());
		dbNotificationObject.put(FIELD_TYPE, notification.getType().toValue());
		//if(!notification.getTargetId().equals(Constants.EMPTY_TARGET)){ 
			dbNotificationObject.put(FIELD_TARGET, notification.getTargetId());
	//	}
		dbNotificationObject.put(FIELD_SEEN,notification.isSeen());
		dbNotificationObject.put(FIELD_CREATED, notification.getCreated());
		return dbNotificationObject;
		
	}


}
