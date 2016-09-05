package com.madcoding.smile.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.madcoding.smile.services.ProfilePhotoProvider;
import com.madcoding.smile.store.PhotoStore;
@XmlRootElement
public class NotificationWrapper {

	private String id;
	private PhotoWrapper senderId;
	private String receiverId;
	private NotificationType type; // vote,friendship,message
	private PhotoWrapper targetId; //if it s a vote notifcation then the target is the photo voted
	private boolean seen ;
	private Date created;
	
	public static NotificationWrapper makeInstance(Notification notification){
		
		NotificationWrapper instance = new NotificationWrapper();
		instance.id = notification.getId();
		instance.senderId = new PhotoWrapper();
		instance.senderId.setId(notification.getSenderId());
		Photo senderPhoto = ProfilePhotoProvider.getInstance().getMainProfilePhoto(notification.getSenderId());
		instance.senderId.setPhoto(senderPhoto);
		
		instance.targetId = new PhotoWrapper();
		instance.receiverId = notification.getReceiverId();
		instance.type = notification.getType();
		boolean shouldNotificationHaveTarget = notification.getType().equals(NotificationType.VOTE) && notification.getTargetId()!= null; 
		if(shouldNotificationHaveTarget){
			instance.targetId.setId(notification.getTargetId());
			instance.targetId.setPhoto(PhotoStore.getInstance().loadById(notification.getTargetId()));
		}
		instance.seen = notification.isSeen();
		instance.created = notification.getCreated();
		return instance;
		
		
	}
}
