define(['jquery','search/search_provider','search/search_pannel','animation','response/response_pannel'],
function($,Provider,View,Animation,Response){
	
	var addListener = function(){
		$('#search_submit').click(function(){
			ga('send','event', 'search_submit','searchFriend');
			var email = $(this).prev().val();
			searchFriend(email);
			$('.search_input').text('');
		});
		
		$(document).on('click','.search_result_row .invite_friend',function(){
			ga('send','event', 'invite_friend','inviteFriend');
			var receiverId =$(this).parent().parent().data('userid');
			sendFriendNotification(receiverId);
		});
		
	}
	
	var removePannel = function(){
		View.removePannel();
	}
	
	var sendFriendNotification = function(receiverId){
		var successCallback = function(data){
			Response.showFeedbackResponse('Friend request sent successfully!');
			View.removePannel();
		}
		var errorCallback = function(xhr,exception){
			console.log('error sendind friend request');
		}
		var senderId = localStorage.getItem('USERID');
		Provider.sendFriendNotification(senderId,receiverId,successCallback,errorCallback);
	}
	
	var searchFriend = function(email){
		
		var successCallback = function(data){
			View.appendPannel();
			View.populatePannel(data);
			
		}
		var errorCallback = function(xhr,exception){
			console.log('Error searching friend');
		}
		
		Provider.searchFriendByEmail(email,successCallback,errorCallback);
		
	}
	
	return{
		addListener : addListener,
		removePannel : removePannel
	}
});