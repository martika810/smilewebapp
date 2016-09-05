define(['jquery','coyote','url_builder'],
function($,Coyote,UrlBuilder){
	
	var loadRankingPhotos = function(successCallback,errorCallback){
		var paramsMap = new Object();
		paramsMap['userid'] = localStorage.getItem("USERID");
		var rankPhotosUrl = UrlBuilder.addParameters('/photos/rank',paramsMap);
		
		Coyote.get_auth(rankPhotosUrl,successCallback,errorCallback);
	}
	return {
		loadRankingPhotos : loadRankingPhotos
	}
});