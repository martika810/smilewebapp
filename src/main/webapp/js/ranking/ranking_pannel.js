define([ 'jquery','photo_pannel/photo_pannel_common' ], 
function($,PhotoPannelCommon) {
	
	var ODD_ROW = '<div class="row custom_row">'
			+ '<div class="col-sm-4 rank_photo_spot"></div>'
			+ '<div class="col-sm-4 rank_photo_spot"></div>'
			+ '<div class="col-sm-4 rank_photo_spot"></div>' + '</div>';

	var EVEN_ROW = '<div class="row custom_row">'
			+ '<div class="col-sm-4 col-md-offset-2 rank_photo_spot"></div>'
			+ '<div class="col-sm-4 rank_photo_spot"></div>' + '</div>';
	
	var RANKING_EMPY_MSG = '<div class="center-block">' + 
								'<h1>No Selfies Available</h1>' +
						   '</div>';
					
	
	var populateWithRows = function(numberPhotos){
		var numberRowsToAdd = PhotoPannelCommon.calculateRowNumToPlacePhoto(numberPhotos);
		for(var i=0;i<=numberRowsToAdd;i++){
			if(i % 2 == 0){
				$('#ranking_content .container').append(EVEN_ROW);
			}else{
				$('#ranking_content .container').append(ODD_ROW);
			}
		}
	}
	
	var populateWithPhotos = function(photos){
		for(var i=0;i<photos.length;i++){
			var imageUrl =photos[i].photo.url;
			var image = $.cloudinary.image(imageUrl, 
					 {width: 230, height: 230, gravity: "face" });
			var votePannel = PhotoPannelCommon.createRankingVotePannel(photos[i].photo.numberVotes,photos[i].photo.comment);
			$('.rank_photo_spot').eq(i).append(image);
			$('.rank_photo_spot').eq(i).append(votePannel);
			
		}
	}
	

	
	var renderRankingPannel = function(rankingPhotos) {
		$('#ranking_content .container .row').remove();
		if(rankingPhotos.length == 0){
			$('#ranking_content .container').append(RANKING_EMPY_MSG);
		}else{
			populateWithRows(rankingPhotos.length);
			populateWithPhotos(rankingPhotos);
		}
	}

	return {
		renderRankingPannel : renderRankingPannel
	}
});