define(['jquery','static','cloudinary','coyote','animation','profile/profile','vote_pannel/vote_controller',
        'select_file/select_file_pannel','upload_file/uploadfile_handler','ranking/ranking_controller',
        'notification/notification_controller','search/search_controller'],
function($,Static,Cloudinary,Coyote,Animation,Profile,VoteController,
		SelectFilePannel,UploadFile,RankingController,Notification,SearchController){
	
	var connectAndLoadFirstCloudinary = function(){
			Coyote.get_auth('/cloudinarydetails',function(data){
				$.cloudinary.config({ cloud_name: data.name, 
					  api_key: data.apiKey});
				VoteController.loadPhotosToVote();
				RankingController.populateRanking();
				Profile.populateProfile();
			},function(xhr,exception){
				console.log('Error getting cloudinary details!!')
			},null);
		
	}	
	
	var updateProfileLocation = function(){
		var profile = new Object();
		profile.ownerId = localStorage.getItem('USERID');
		var location = new Object();
		location.latitude = parseFloat(localStorage.getItem('LAT'));
		location.longitude = parseFloat(localStorage.getItem('LONG'));
		profile.location = location;
		var successCallback = function(data){
			console.log('location updated');
		}
		var errorCallback = function(xhr,exception){
			console.log('location not updated');
		}
		Coyote.put_auth('/profiles/updatelocation',JSON.stringify(profile),successCallback,errorCallback,null);
	}
	
	var clearLocalStorage = function(){
		localStorage.setItem('USERID','');
		localStorage.setItem('TOKEN','');
		localStorage.setItem('CURRENT_PHOTO_TO_VOTE','');
	}
	
	
	var addListener = function(){
		$(".like_btn").click(function(){
			ga('send','event', 'like_btn','likePhoto');
			Animation.pushButton($(this));
			VoteController.addVote(localStorage.getItem('CURRENT_PHOTO_TO_VOTE'));
			VoteController.loadNextPhoto();
			
		});
		$(".next_btn").click(function(){
			ga('send','event', 'next_btn','nextPhoto');
			Animation.pushButton($(this));
			VoteController.passToNext(localStorage.getItem('CURRENT_PHOTO_TO_VOTE'));
			VoteController.loadNextPhoto();
			
		});
		$(".navbar li a").click(function(e){
			e.preventDefault();
			$(".navbar li").removeClass('active');
			$(this).parent().addClass('active');
			var selectedTab = $(this).parent().data('id');
			if(selectedTab == "ranking"){
				ga('send','event', 'ranking_tab','selectRanking');
				$("#profile_content").addClass('hidden');
				RankingController.populateRanking();
				$("#ranking_content").removeClass('hidden');
			}else{
				ga('send','event', 'profile_tab','selectProfile');
				$("#ranking_content").addClass('hidden');
				Profile.populateProfile();
				$("#profile_content").removeClass('hidden');
			}
			
		});
		$('#logout_btn').click(function(){
			ga('send','event', 'logout_btn','logout');
			VoteController.sendVotes();
			clearLocalStorage();
			window.location =  Static.base_url;
		});
		
		$(document).on('click','#profile_content .add_btn',function(){
			ga('send','event', 'add_btn','openSelectPhoto');
			SelectFilePannel.show();
			
		});
		//addDocumentListener();
		SearchController.addListener();
		Profile.addListener();
		SelectFilePannel.addListeners();
		
	}
	
	var main = function(){
		
		connectAndLoadFirstCloudinary();
		updateProfileLocation();
		UploadFile.main();
		Notification.main();
		addListener();
				

	}
	
	return{
		main:main
	}
});