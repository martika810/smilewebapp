define(['jquery'],function($){
	
	var convertArrayToMap = function(photoArray){
		var map = new Object();
		for(var i = 0; i<photoArray.length ; i++){
			map[photoArray[i].id] = photoArray[i];
			
		}
		return map;
		
	}
	
	var convertMapToArray = function(map){
		var array = []
		for(var i = 0;i<Object.keys(map).length;i++){
			var value = map[Object.keys(map)[i]];
			array.push(value);
		}
		return array;
	}
	return {
		convertArrayToMap : convertArrayToMap,
		convertMapToArray : convertMapToArray
	}
});