define(['jquery'],function($){
	
	var PANNEL = '<div id="search_result_pannel">'
		+ '<span class="close-btn" onclick="this.parentNode.remove(); return false;">'
		+ '<img src="img/close_icon.png" height="20px" width="20px">'
		+'</span>'
		+ '</div>';
	
	
	
	var appendPannel = function(){
		$('body').append(PANNEL);
		$('#search_result_pannel').css({
			left : $('.search_input').offset().left + 'px'
		});
	}
	
	var removePannel = function(){
		$('#search_result_pannel').remove();
	}
	
	var populatePannel = function(friend){
		createSearchResultRow(friend);
		$('#search_result_pannel').focus();
	}
	
	var createRow = function(id){
		var row= '<div data-userid="'+ id.toString() +'" class="row search_result_row not_seen">'
					+ '<div class="col-sm-4 friend_photo">'
					+ '</div>'
					+ '<div class="col-sm-7 friend_info">'
						+ '<h4></h4>'
						+ '<button type="button" class="btn btn-success invite_friend">Invite</button>'
					+ '</div>' 
				+ '</div>';
		return row;
	}
	
	var createSearchResultRow = function(friend){
		var frienProfileImage;
		if(friend.photoProfileUrl!==undefined && friend.photoProfileUrl.length>0){
			var friendProfilePhotoUrl = friend.photoProfileUrl;
			frienProfileImage = $.cloudinary.image(friendProfilePhotoUrl, {
				width : 80,
				height : 80,
				gravity : "face"}).addClass('sender_image');
		}else{
			frienProfileImage = '<img src="img/profile.png" height="80px" width="80px">';
		}
		var row = createRow(friend.userId);
		
		$('#search_result_pannel').append(row);
		$('#search_result_pannel .friend_photo').last().append(frienProfileImage);
		$('#search_result_pannel .friend_info h4').last().text(friend.email);
	}
	
	return{
		appendPannel : appendPannel,
		populatePannel : populatePannel,
		removePannel : removePannel
	}
});