define([ 'jquery','coyote','url_builder'], function($,Coyote,UrlBuilder) {
	
	var sendVotes = function(photosToVote, successCallback, errorCallback) {

		Coyote.post_auth('/votes/bunch', JSON.stringify(photosToVote),
				successCallback, errorCallback, null);
	}
	
	var loadPhotosToVote = function(successCallback,errorCallback){
		var paramsMap = new Object();
		paramsMap['userid'] = localStorage.getItem('USERID');
		var urlPhotosToVote =UrlBuilder.addParameters('/photos/tovote',paramsMap);
		
		Coyote.get_auth(urlPhotosToVote,successCallback,errorCallback,null);
	}
	
	return {
		sendVotes : sendVotes,
		loadPhotosToVote : loadPhotosToVote
	}
});