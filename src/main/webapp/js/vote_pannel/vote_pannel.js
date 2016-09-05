define(['jquery','animation'],function($,Animation){
	
	var EMPTY = '<img id="empty" class="img_to_vote" src="img/empty.png" height="360px" width="360px" >';
		
	var fetchFirstPhoto = function(nextImage){
		
		var image;
		if(nextImage !== undefined){
			localStorage.setItem('CURRENT_PHOTO_TO_VOTE',nextImage.id);
			var urlImage = nextImage.url;
			image = $.cloudinary.image(urlImage, 
					 {width: 360, height: 360, gravity: "face" });
			image.addClass('img_to_vote');
			$(".vote_pannel").append(image);
		}else{
			replaceByLoading();
		}

		
		
	}
	
	var isImageToVoteEmpty = function () {
		if($('.vote_pannel .img_to_vote').length == 0 || ($('.vote_panel #empty').length == 1)){
			return true;
		}else{
			return false;
		}
	}
	
	var replaceByLoading = function(){
		Animation.close($(".vote_pannel .img_to_vote"));
		$(".vote_pannel").remove(".img_to_vote");
		$(".vote_pannel").append(EMPTY);
		$(".vote_pannel .like_btn").addClass('hidden');
		$(".vote_pannel .next_btn").addClass('hidden');
		
	}
	
	var showLikeNextButton = function(){
		$(".vote_pannel .like_btn").removeClass('hidden');
		$(".vote_pannel .next_btn").removeClass('hidden');
	}
	
	var loadNextPhoto = function(nextImage){
		
		Animation.close($(".vote_pannel .img_to_vote"));
		$(".vote_pannel").remove(".img_to_vote");
		var urlImage = nextImage.url;
		var image = $.cloudinary.image(urlImage,
				{width:360,height:360,gravity:"face"});
		image.addClass('img_to_vote');
		$(".vote_pannel").append(image);
		
	}
	return{
		fetchFirstPhoto: fetchFirstPhoto,
		loadNextPhoto : loadNextPhoto,
		replaceByLoading : replaceByLoading,
		isImageToVoteEmpty : isImageToVoteEmpty,
		showLikeNextButton : showLikeNextButton
	}
});