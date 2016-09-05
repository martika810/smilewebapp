define(['jquery','coyote','url_builder'],function($,Coyote,UrlBuilder){
	
	var loadNotSeenNotifications = function(successCallback,errorCallback){
		var paramsMap = new Object();
		paramsMap['receiverid'] = localStorage.getItem("USERID");
		var notificationUrl = UrlBuilder.addParameters('/notifications/byreceiver',paramsMap);
		Coyote.get_auth(notificationUrl,successCallback,errorCallback);
	}
	
	var updateNotificationAsSeen = function(notificationIds,successCallback,errorCallback){
		Coyote.put_auth('/notifications/markasseen', JSON.stringify(notificationIds),
				successCallback, errorCallback, null);
	}
	
	var addFriendToProfile = function(frienId,successCallback,errorCallback){
		var paramsMap = new Object();
		paramsMap['userid'] = localStorage.getItem("USERID");
		paramsMap['friendid'] = frienId;
		var addFriendUrl = UrlBuilder.addParameters('/profiles/addfriend',paramsMap);
		
		Coyote.put_auth(addFriendUrl,null,successCallback,errorCallback);
	}
	
	return{
		loadNotSeenNotifications : loadNotSeenNotifications,
		updateNotificationAsSeen : updateNotificationAsSeen,
		addFriendToProfile : addFriendToProfile
	}
});