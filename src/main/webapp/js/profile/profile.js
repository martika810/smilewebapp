define(['jquery','photo_pannel/photo_pannel_common','profile/profile_provider','profile/setting_pannel'],
function($,PhotoPannelCommon,Provider,Settings){
	var FIRST_ROW = '<div class="row custom_row">' +
						'<div class="col-sm-2">' + 
							'<img class="add_btn" src="img/add_icon.png" height="100px" width="100px">' +
						'</div>' +
						'<div class="col-sm-4 photo_spot"></div>' +
						'<div class="col-sm-4 photo_spot"></div>' +
						'<div class="col-sm-2">' + 
							'<img class="setting_btn" src="img/setting_icon.png" height="100px" width="100px">' +
					'</div>' +
					'</div>';
		
	var ODD_ROW = '<div class="row custom_row">' +
						'<div class="col-sm-4 photo_spot"></div>' +
						'<div class="col-sm-4 photo_spot"></div>' +
						'<div class="col-sm-4 photo_spot"></div>' +
				   '</div>';
	
	var EVEN_ROW = '<div class="row custom_row">' +
						'<div class="col-sm-4 col-md-offset-2 photo_spot"></div>' +
						'<div class="col-sm-4 photo_spot"></div>' +
				  '</div>';
	
	var MAX_NUMBER_CHARACTERS_COMMENT = 50;

				   
	var populateProfile = function(){
		var successCallback = function(data){
			renderProfile(data);
		}
		
		var errorCallback = function(xhr,exception){
			console.log('error retrieving photos');
		}
		
		Provider.loadProfilePhotos(successCallback,errorCallback);
		
		
	}
	
	var updateCommentPhoto = function(photoid,comment){
		var photo = new Object();
		photo.id=photoid;
		photo.comment = comment;
		var successCallback = function(data){
			console.log('comment updated');
		}
		var errorCallback = function(xhr,exception){
			console.log('error updating comment');
		}
		Provider.updatePhotoComment(photo,successCallback,errorCallback);
	}
	
	var addListener = function(){
		Settings.main();
		$(document).on('keypress','#profile_content .photo_vote_pannel textarea.image-info',function(){
			var numberCharacterEntered = $(this).val().length;
			if(numberCharacterEntered > MAX_NUMBER_CHARACTERS_COMMENT){
				var newString = $(this).val().substr(0,MAX_NUMBER_CHARACTERS_COMMENT);
				$(this).val(newString);
			}
		});
		
		$(document).on('focusout','#profile_content .photo_vote_pannel textarea.image-info',function(){
			ga('send','event', 'comment-textarea','updateProfilePhoto');
			var photoId = $(this).parent().parent().parent().data('photo-id');
			var comment = $(this).val();
			updateCommentPhoto(photoId,comment);
		});
		$(document).on('click','#profile_content .setting_btn',function(){
			Settings.showPannel();
		});
		
		
	}
	
	var renderProfile = function(profilephotos){
		
		$('#profile_content .container .row').remove();
		$('#profile_content .container').append(FIRST_ROW);
		if(profilephotos !== null){
			populateWithRows(profilephotos.length);
			populateWithPhotos(profilephotos);
		}
		
	}
	

	
	var populateWithPhotos = function(photos){
		for(var i=0;i<photos.length;i++){
			var imageUrl =photos[i].url;
			var image = $.cloudinary.image(imageUrl, 
					 {width: 230, height: 230, gravity: "face" });
			var votePannel = PhotoPannelCommon.createProfileVotePannel(photos[i].id,photos[i].numberVotes,photos[i].comment);
			$('.photo_spot').eq(i).append(image);
			$('.photo_spot').eq(i).append(votePannel);
			
		}
	
	}
	
	var populateWithRows = function(numberPhotos){
		var numberRowsToAdd = PhotoPannelCommon.calculateRowNumToPlacePhoto(numberPhotos);
		for(var i=1;i<=numberRowsToAdd;i++){
			if(i % 2 == 0){
				$('#profile_content .container').append(EVEN_ROW);
			}else{
				$('#profile_content .container').append(ODD_ROW);
			}
		}
	}
	
	
	return{
		populateProfile : populateProfile,
		addListener : addListener
	}
});