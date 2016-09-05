define(['jquery'],function($){
	var BEGIN_PARAMETER = '?';
	var PARAMETER_SEPARATOR = '&';
	var addParameters = function(url,params){
		
		url = url + BEGIN_PARAMETER;
		for(var i = 0;i<Object.keys(params).length;i++){
			var key = Object.keys(params)[i];
			var value = params[Object.keys(params)[i]];
			if( i == 0 ){
				url = url + key + '=' + value ;
			}else{
				url = url + PARAMETER_SEPARATOR + key + '=' + value ;
			}
		}
		
		return url;
	}
	return {
		addParameters : addParameters
	}
});