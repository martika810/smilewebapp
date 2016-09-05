package com.madcoding.smile.helpers;

import java.util.ArrayList;
import java.util.List;

import com.madcoding.smile.domain.Constants;
import com.madcoding.smile.domain.Notification;
import com.madcoding.smile.domain.NotificationType;
import com.madcoding.smile.domain.NotificationWrapper;
import com.madcoding.smile.store.NotificationStore;

public class NotificationHelper {
	private static NotificationHelper INSTANCE = null;
	private NotificationHelper (){}
	
	
	public static NotificationHelper getInstance(){
		if(INSTANCE == null){
			INSTANCE = new NotificationHelper();
		}
		return INSTANCE;
	}
	
	public Notification createVoteNotification(String senderId,String receiverId,String votedPhotoId){
		Notification notification = new Notification();
		notification.setSenderId(senderId);
		notification.setReceiverId(receiverId);
		notification.setType(NotificationType.VOTE);
		notification.setTargetId(votedPhotoId);
		
		return NotificationStore.getInstance().insert(notification);
	}
	
	public Notification createFriendNotification(String senderId,String receiverId){
		Notification notification = new Notification();
		notification.setSenderId(senderId);
		notification.setReceiverId(receiverId);
		notification.setType(NotificationType.FRIEND);
		notification.setTargetId(receiverId);
		
		return NotificationStore.getInstance().insert(notification);
	}
	
	public void updateNotificationBunchAsSeen(List<String> notificationIds){
		NotificationStore.getInstance().markAsSeen(notificationIds);
	}
	
	
	public List<NotificationWrapper> convertToWrapper(List<Notification> notifications){
		List<NotificationWrapper> wrappers = new ArrayList<NotificationWrapper>();
		for(Notification notification: notifications){
			wrappers.add(NotificationWrapper.makeInstance(notification));
		}
		return wrappers;
	}
	
	


}
