package com.madcoding.smile;

import java.util.List;

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
import com.madcoding.smile.domain.Notification;
import com.madcoding.smile.domain.NotificationWrapper;
import com.madcoding.smile.helpers.NotificationHelper;
import com.madcoding.smile.store.NotificationStore;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/notifications")
public class NotificationResource {

	@GET
	@Path("/byreceiver")
	public Response getNotSeenNotificationByReceiver(@QueryParam("receiverid") String receiverId){
		List<Notification> notSeenNotifications = NotificationStore.getInstance().findNotSeenNotificationsByReceiver(receiverId);
		List<NotificationWrapper> wrappers = NotificationHelper.getInstance().convertToWrapper(notSeenNotifications);
		return Response.ok(new Gson().toJson(wrappers)).build();
	}
	
	@PUT
	@Path("/markasseen")
	public Response markAsSeen(List<String> notificationIds){
		NotificationStore.getInstance().markAsSeen(notificationIds);
		return Response.ok(new Gson().toJson(notificationIds)).build();
			
	}
	
	@POST
	@Path("/friendnotification")
	public Response createFriendNotification(@QueryParam("receiverid") String receiverId,@QueryParam("senderid") String senderId){
		Notification friendNotification = NotificationHelper.getInstance().createFriendNotification(senderId, receiverId);
		return Response.ok(new Gson().toJson(friendNotification)).build();
	}
	
}
