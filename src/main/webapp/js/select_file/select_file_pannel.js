define(['jquery','static','coyote','profile/profile','utils/collections','lib/jquery.fileupload'],
		function($,Static,Coyote,Profile,Collections){
	
	var URL_UPLOAD_FILE ='/uploadfile';
	var MAX_NUMBER_CHARACTERS_COMMENT = 50;
	var ERROR = '<div class="row image">' +
					'<div class="col-lg-10 col-md-offset-1 row-uploaded error">' +
						'<p>Error uploading file</p>' +
					'</div>' +
				'</div>';
	var profilePhotosToSave = new Object();
	
	var show = function(){
		$(".shadow_background").removeClass('hidden');
		$("#select_file_pannel").removeClass('hidden');
		
	}
	
	var close = function(){
		$(".shadow_background").addClass('hidden');
		$("#select_file_pannel").addClass('hidden');
		
	}
	
	var addRowWithFileUploaded = function(file){
		var row = '<div class="row image">' +
						'<div class="col-lg-10 col-md-offset-1 row-uploaded">' +
							'<div class="col-sm-2">' +
								'<img class="uploaded_image" src="' + file.url + '" height="100px" width="100px">' +						
							'</div>' +
							'<div class="col-sm-7 col-md-offset-1">' +
								'<textarea class="comment_txt" placeholder="Type cool title for your selfie in 50 character! "></textarea>' +
							'</div>' +
							'<div class="col-sm-2" col-md-offset-1>' +
								'<img class="delete_row_btn" src ="img/close_icon.png" height="40px" width="40px" >' +
							'</div>' +
						'</div>' +
				  '</div>';
		$("#select_file_pannel .container").append(row);
	}
	
	var addRowError = function(){
		$("#select_file_pannel .container").append(ERROR);
	}
	
	var addPhoto = function(url){
		var photo = new Object();
		photo.url = url;
		photo.owner = localStorage.getItem('USERID');
		profilePhotosToSave[url] =photo;
	}
	
	
	var addListeners = function(){
		
		$('#fileupload').fileupload();
		
		$('#fileupload').fileupload({'done':function(e, ui){
			ga('send','event', 'upload_file','addPhoto');
			var wereFileUploaded = ui.result.files.length > 0;
			if(wereFileUploaded){
				addRowWithFileUploaded(ui.result.files[0]);
				addPhoto(ui.result.files[0].url);
			}else{
				addRowError();
			}
		}});
		$('#fileupload').attr('action', Static.base_url + URL_UPLOAD_FILE);
		addUploadListener();
		addCancelListener();
		addDeleteRowListener();
		addTextAreaListener();
	}
	var addDeleteRowListener = function(){
		$(document).on('click','.delete_row_btn',function(){
			ga('send','event', 'delete_btn','deletePhoto');
			$(this).parent().parent().parent().addClass('hidden').remove();
			var urlTouchedImage = $(this).parent().parent().find('.uploaded_image').attr('src');
			delete profilePhotosToSave[urlTouchedImage];
			
		});
		
	}
	
	var addTextAreaListener = function(){
		$(document).on('keypress','#select_file_pannel textarea.comment_txt',function(){
			var numberCharacterEntered = $(this).val().length;
			if(numberCharacterEntered > MAX_NUMBER_CHARACTERS_COMMENT){
				var newString = $(this).val().substr(0,MAX_NUMBER_CHARACTERS_COMMENT);
				$(this).val(newString);
			}
		});
	}
	
	var saveInsertedComment = function(){
		$('#select_file_pannel .comment_txt').each(function(){
			var isCommentEmpty = $(this).val().length <= 0;
			if(!isCommentEmpty){
				var url = $(this).parent().parent().find('.uploaded_image').attr('src');
				profilePhotosToSave[url].comment = $(this).val();
			}
		});
	}
	var addUploadListener = function(){
		$('.fileupload-buttonbar .btn-upload').click(function(){
			ga('send','event', 'upload-btn','uploadPhoto');
			var successCallback = function(data){
				close();
				clear();
				Profile.populateProfile();
				
			}
			var errorCallback = function(xhr,exception){
				addRowError();
			}
			saveInsertedComment();
			var photosToSave = Collections.convertMapToArray(profilePhotosToSave);
			Coyote.post_auth('/photos/bunch', JSON.stringify(photosToSave),
					successCallback, errorCallback, null);
			
		});
		
		
		
	}
	
	var clear = function(){
		profilePhotosToSave = new Object();
		$("#select_file_pannel .container .image").remove();
		
	}
	var addCancelListener = function(){
		$('.fileupload-buttonbar .btn-cancel').click(function(){
			ga('send','event', 'cancel-btn','cancelUploadPhoto');
			close();
			clear();
			
		});
	}
	
	
	
	return {
		
		show : show,
		addListeners : addListeners
	}
	
});