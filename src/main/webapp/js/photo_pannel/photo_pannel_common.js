define(['jquery'],function($){
	
	var MAX_PROFILE_COMMENT = 50;
	var calculateRowNumToPlacePhoto = function(photoIndex){
		var numRow = Math.ceil((photoIndex - 2) / 2);
		var mod = (photoIndex - 2) % 2;
		return numRow;
	}
	
	
	
	var cropProfileComment = function(comment){
		var result = comment;
		if(comment.length>MAX_PROFILE_COMMENT){
			result = comment.substr(0,MAX_PROFILE_COMMENT) + '...';
		}
		return result;
	}
	
	var createRankingVotePannel = function(votes,comment){
		var commentText ="";
		if(comment.length>0){
			commentText = comment;
		}
		var votePannel = '<span class="photo_vote_pannel">' +
							'<div class="row">' +
								'<div class="col-sm-8">' +
									'<div class="image-info">' +
									cropProfileComment(commentText) +
									'</div>'+
								'</div>' +
								'<div class="col-sm-4">' +
									'<div class="image-votes">' +
										'<img src="img/like_pannel.png" height="60px" width="60px">' +
										'<span class="vote_number">' + votes + '</span>'
									'</div>' +
								'</div>'
							'</div>' +
						 '</span>';
		return votePannel;
	}
	
	var createProfileVotePannel = function(id,votes,comment){
		var commentText ="";
		if(comment.length>0){
			commentText = comment;
		}
		var votePannel = '<span class="photo_vote_pannel"data-photo-id="'+ id +'">' +
							'<div class="row">' +
								'<div class="col-sm-8">' +
									'<textarea class="image-info">' +
									  commentText +
									'</textarea>'+
								'</div>' +
								'<div class="col-sm-4">' +
									'<div class="image-votes">' +
										'<img src="img/like_pannel.png" height="60px" width="60px">' +
										'<span class="vote_number">' + votes + '</span>'
									'</div>' +
								'</div>'
							'</div>' +
						 '</span>';
		return votePannel;
	}
	
	return{
		calculateRowNumToPlacePhoto : calculateRowNumToPlacePhoto,
		createRankingVotePannel : createRankingVotePannel,
		createProfileVotePannel : createProfileVotePannel
	}
});