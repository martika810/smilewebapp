define(['jquery','ranking/ranking_pannel','ranking/ranking_provider'],
function($,View,Provider){
	
	var rankingPhotos = [];
	var populateRanking = function(){
		var successCallback = function(data){
			rankingPhotos = data;
			View.renderRankingPannel(rankingPhotos);
		}
		
		var errorCallback = function(xhr,exception){
			console.log('error loading ranking photos');
		}
		Provider.loadRankingPhotos(successCallback,errorCallback);
	}
	
	return{
		populateRanking : populateRanking
	}
});