define(['require','jquery','static'],function(require,$,Static){
	
	var BASE_URL = Static.base_rest_url;
	var token =null;
	if(localStorage.getItem("TOKEN") != undefined){
		token = localStorage.getItem("TOKEN");
	}
	var setToken = function(receivedToken){
		token = receivedToken;
	}
	var getToken = function(){
		return token;
	}
	var auth_ajax = function(method,url,data,successCallback,errorCallback,headers){
		
		$.ajax({
			url : url,
			data : data,
			statusCode:{
				401:function(data){
					localStorage.setItem('USERID','');
					localStorage.setItem('TOKEN','');
					localStorage.setItem('CURRENT_PHOTO_TO_VOTE','');
					window.location = Static.base_url + '/index.html';
				}
			},
			beforeSend: function(xhr){
				xhr.setRequestHeader("Content-Type","application/json");
				xhr.setRequestHeader("Authorization",token);
			},
			success : successCallback,
			error:errorCallback,
			type: method
		});
	}
	
	var ajax = function(method,url,data,successCallback,errorCallback,headers){
		$.ajax({
			url : url,
			data : data,
			beforeSend: function(xhr){
				xhr.setRequestHeader("Content-Type","application/json");
			},
			success : successCallback,
			error:errorCallback,
			type: method
		});
	}
	var get = function(url,successCallback,errorCallback,additionalHeader){
		ajax('GET',BASE_URL + url,null,successCallback,errorCallback,additionalHeader);
	}
	
	var post = function(url,data,sucessCallback,errorCallback,additionalHeader){
		ajax('POST',BASE_URL + url,data,sucessCallback,errorCallback,additionalHeader);
	}
	
	var post_auth = function(url,data,sucessCallback,errorCallback,additionalHeader){
		auth_ajax('POST',BASE_URL + url,data,sucessCallback,errorCallback,additionalHeader);
	}
	
	var get_auth = function(url,successCallback,errorCallback,additionalHeader){
		auth_ajax('GET',BASE_URL + url,null,successCallback,errorCallback,additionalHeader);
	}
	
	var put_auth = function(url,data,sucessCallback,errorCallback,additionalHeader){
		auth_ajax('PUT',BASE_URL + url,data,sucessCallback,errorCallback,additionalHeader);
	}
	
	
	return{
		baseurl :BASE_URL,
		setToken : setToken,
		getToken : getToken,
		get : get ,
		get_auth : get_auth,
		post : post,
		post_auth : post_auth,
		put_auth : put_auth
	}
})