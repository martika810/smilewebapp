define(['jquery','coyote','url_builder'],function($,Coyote,UrlBuilder){
	
	var loadProfilePhotos = function(successCallback,errorCallback){
		var paramsMap = new Object();
		paramsMap['owner'] = localStorage.getItem("USERID");
		var photoProfileUrl = UrlBuilder.addParameters('/photos/byowner',paramsMap);
		
		Coyote.get_auth(photoProfileUrl,successCallback,errorCallback,null);
	}
	
	var updatePhotoComment = function(photo,successCallback,errorCallback){
	
		Coyote.put_auth('/photos', JSON.stringify(photo),
				successCallback, errorCallback, null);
	}
	
	var loadProfile = function(successCallback,errorCallback){
		var paramsMap = new Object();
		paramsMap['owner'] = localStorage.getItem("USERID");
		var photoProfileUrl = UrlBuilder.addParameters('/profiles/byowner',paramsMap);
		
		Coyote.get_auth(photoProfileUrl,successCallback,errorCallback,null);
	}
	
	var updateProfile = function(profile,successCallback,errorCallback){
		
		Coyote.put_auth('/profiles/updateprofile', JSON.stringify(profile),
				successCallback, errorCallback, null);
	}
	
	return{
		loadProfilePhotos: loadProfilePhotos,
		updatePhotoComment : updatePhotoComment,
		loadProfile : loadProfile,
		updateProfile : updateProfile
	}
});