define(['jquery','notification/notification_pannel','notification/notification_provider','response/response_pannel'],
function($,View,Provider,Response){
	
	var notificationToMarkAsSeen = [];
	
	var addListener = function() {
		$('#notification_btn').click(
				
				function() { 
					ga('send','event', 'notification-btn','openNotification');
					var isPannelHidden = $('#notification_pannel')
							.hasClass('hidden')
							|| $('#notification_pannel').length == 0;
					if (isPannelHidden) {
						var isNotificationEmpty = $('#notification_pannel .notification_row').length == 0;
						if(!isNotificationEmpty){
							View.showPannel();
						}
					} else {
						View.closePannel();
					}
		});
		
		$(document).on('click','#notification_pannel .close-btn',function(){
			View.closePannel();
		})
		
		$(document).on('click','#notification_pannel .notification_row',function(){
			if($(this).hasClass('not_seen')){
				ga('send','event', 'notification-pannel','markAsSeen');
				$(this).removeClass('not_seen');
				$(this).addClass('seen');
				var touchedNotification = $(this).data('id');
				notificationToMarkAsSeen.push(touchedNotification);
				if(shouldSendNotificationUpdate){
					var successCallback = function(data){
						notificationToMarkAsSeen = [];
						populateNotification();
					}
					var errorCallback = function(xhr,exception){
						console.log('error updating notifications');
					}
					Provider.updateNotificationAsSeen(notificationToMarkAsSeen,successCallback,errorCallback);
				}
			}
		});
		$(document).on('click','#notification_pannel .notification_row .accept-btn',function(){
			ga('send','event', 'accept-btn','acceptFriend');
			var friendid= $(this).parent().parent().parent().parent().data('friendid');
			var successCallback = function(data){
				Response.showFeedbackResponse('Friend added successfully');
			}
			
			var errorCallback = function(xhr,exception){
				console.log('error adding friend');
			}
			Provider.addFriendToProfile(friendid,successCallback,errorCallback);
		});
	}
	

	var removePannel = function(){
		View.closePannel();
	}
	
	var shouldSendNotificationUpdate = function(){
		var isThereNotMoreNotificationUpdate = $('#notification_pannel .notification_row .not_seen').lenght ==0;
		return notificationToMarkAsSeen>=3 || isThereNotMoreNotificationUpdate ;
	}
	
	var populateNotification = function(){
		var successCallback = function(data){
			var numberNotSeenNotification = data.length;
			View.addNotificationNumberIcon(numberNotSeenNotification);
			View.populateNotificationPannel(data);
		}
		
		var errorCallback = function(xhr,exception){
			
		}
		Provider.loadNotSeenNotifications(successCallback,errorCallback);
	}
	
	var main = function(){
		addListener();
		View.createNotificationPannel();
		populateNotification();
	}
	
	return{
		populateNotification:populateNotification,
		main : main,
		removePannel : removePannel
	}
	
});