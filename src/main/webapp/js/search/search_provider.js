define(['jquery','coyote','url_builder'],function($,Coyote,UrlBuilder){
	
	var searchFriendByEmail = function(email,successCallback,errorCallback){
		
		var paramsMap = new Object();
		paramsMap['email'] = email;
		var searchFriendUrl = UrlBuilder.addParameters('/users/searchfriend',paramsMap);
		
		Coyote.get_auth(searchFriendUrl,successCallback,errorCallback);
	}
	
	var sendFriendNotification = function(senderId,receiverId,successCallback,errorCallback){
		var paramsMap = new Object();
		paramsMap['senderid'] = senderId;
		paramsMap['receiverid'] = receiverId;
		var friendNotificationUrl = UrlBuilder.addParameters('/notifications/friendnotification',paramsMap);
		
		Coyote.post_auth(friendNotificationUrl,null,successCallback,errorCallback);
		
	}
	
	return{
		searchFriendByEmail : searchFriendByEmail,
		sendFriendNotification : sendFriendNotification
	}
});