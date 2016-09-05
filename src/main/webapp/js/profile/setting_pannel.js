define(['jquery','profile/profile_provider'],function($,Provider){
	
	var TITLE ='<div class="title">' +
					'<h3>Settings</h3>' +
				'</div>';
	
	var PROFILE_VISIBILITY_RADIO_BUTTON = '<div class="visibility_radio">' +
											'<div class="item_radio botton_border">' +
												'<input type="radio" name="profile_radio" value="friends">Friends</input>' +
											'</div>' +
											'<div class="item_radio botton_border">' +
												'<input type="radio" name="profile_radio" value="friends_of_friends">Friends of Friends</input>' +
											'</div>' +
											'<div class="item_radio">' +
												'<input type="radio" name="profile_radio" value="location">Location</input>' +
											'</div>' +
										'</div>';

	var RANK_VISIBILITY_RADIO_BUTTON = '<div class="rank_visibility_radio">' +
											'<div class="item_radio botton_border">' +
												'<input type="radio" name="rank_radio" value="friends">Friends</input>' +
											'</div>' +
											'<div class="item_radio botton_border">' +
												'<input type="radio" name="rank_radio" value="friends_of_friends">Friends of Friends</input>' +
											'</div>' +
											'<div class=" item_radio">' +
												'<input type="radio" name="rank_radio" value="location">Location</input>' +
											'</div>' +
										'</div>';
	var SETTING_PANNEL = '<div id="setting_pannel">' +
							'<span class="close-btn">' +
								'<img src="img/close_icon.png" height="20px" width="20px">' +
							'</span>' +
								TITLE +
								
								'<div class="custom_row">' +
									'<div class="col-md-3 col-sm-offset-1">' +
										'<div class="input_text">' +
											'<input id="name" name="name" type="text" placeholder="Type your name" style="{margin-top:40px;}"></input>' +
										'</div>' +
										'<div class="input_text">' +	
											'<input id="last_name" name="last_name" type="text" placeholder="Type your last name" style="{margin-bottom:40px;}"></input>' +
										'</div>' +
									'</div>' +
									'<div class="col-md-3">' +
										PROFILE_VISIBILITY_RADIO_BUTTON +
									'</div>' +
									'<div class="col-md-3">' +
										RANK_VISIBILITY_RADIO_BUTTON +
									'</div>' +
								'</div>' +
								'<span class="update-btn">' +
									'<button type="button" class="btn-success" id="update_profile">Update</button>' +
								'</span>' +
						'</div>';
						
	
	var showPannel = function(){
		$('#setting_pannel').show();
		populateSettings();
	}
	
	var appendPannel = function(){
		$('#profile_content .container').prepend(SETTING_PANNEL);
	}
	
	var addListener = function(){
		$(document).on('click','#setting_pannel .close-btn',function(){
			$('#setting_pannel').hide();
		});
		
		$(document).on('click','#setting_pannel #update_profile',function(){
			var profile = new Object();
			profile.name = $('#setting_pannel input#name').val();
			profile.lastName = $('#setting_pannel input#last_name').val();
			profile.ownerId = localStorage.getItem("USERID");	
			var profile_visibility = $('#setting_pannel .visibility_radio input:checked').val();
			var rank_visibility = $('#setting_pannel .rank_visibility_radio input:checked').val();
			if(profile_visibility === "friends"){
				profile.scopeToBeVoted = "FRIENDS_FIRST_LINE"
			}else if(profile_visibility === "friends_of_friends"){
				profile.scopeToBeVoted = "FRIENDS_SECOND_LINE"
			}else{
				profile.scopeToBeVoted = "LOCATION";
			}
			if(rank_visibility === "friends"){
				profile.scopeRankList = "FRIENDS_FIRST_LINE"
			}else if(rank_visibility === "friends_of_friends"){
				profile.scopeRankList = "FRIENDS_SECOND_LINE"
			}else{
				profile.scopeRankList = "LOCATION";
			}
			updateProfile(profile);
		});
		
	}
	
	var updateProfile = function(profile){
		var successCallback = function(data){
			populate(data);
		}
		var errorCallback = function(xhr,exception){
			console.log("error updating");
		}
		Provider.updateProfile(profile,successCallback,errorCallback);
	}
	
	var populate = function(data){
		if(data.name !== undefined){
			$('#setting_pannel input#name').val(data.name);
		}
		if(data.lastName!== undefined){
			$('#setting_pannel input#last_name').val(data.lastName);
		}
		
		if(data.scopeToBeVoted === "FRIENDS_FIRST_LINE"){
			$('#setting_pannel .visibility_radio input[value="friends"]').attr('checked',true);
		}else if(data.scopeToBeVoted === "FRIENDS_SECOND_LINE"){
			$('#setting_pannel .visibility_radio input[value="friends_of_friends"]').attr('checked',true);
		}else{
			$('#setting_pannel .visibility_radio input[value="location"]').attr('checked',true);
		}
		
		if(data.scopeRankList === "FRIENDS_FIRST_LINE"){
			$('#setting_pannel .rank_visibility_radio input[value="friends"]').attr('checked',true);
		}else if(data.scopeRankList === "FRIENDS_SECOND_LINE"){
			$('#setting_pannel .rank_visibility_radio input[value="friends_of_friends"]').attr('checked',true);
		}else{
			$('#setting_pannel .rank_visibility_radio input[value="location"]').attr('checked',true);
		}
	}
	
	var populateSettings = function(){
		var successCallback = function(data){
			populate(data);
		}
		
		var errorCallback = function(xhr,exception){
			
		}
		Provider.loadProfile(successCallback,errorCallback);
	}
	
	var main = function(){
		appendPannel();
		populateSettings();
		addListener();
	}
	
	return{
		main:main,
		showPannel : showPannel
	}
});