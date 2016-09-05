define(['jquery'],function($){
	var close = function(element){
		element.animate({
			width:"0px",
			height:"0px",
			'margin-left' : '0px',
			'margin-top' : '200px'
		
		},5000);
	}
	
	var pushButton = function(element){
		element.animate({
			width : element.width()+20 + "px" ,
			height : element.height()+20 + "px" ,
			'margin-left' : element.css('margin-left') + 20 + "px" ,
			'margin-top' : element.css('margin-top') + 20 + "px"
		},100);
		element.animate({
			width : element.width() + "px" ,
			height : element.height() + "px" ,
			'margin-left' : element.css('margin-left')  + "px" ,
			'margin-top' : element.css('margin-top')  + "px"
		},100);
	}
	
	var showAndHide = function(element,duration){
		element.removeClass('hidden');
		setTimeout(function(){ element.addClass('hidden'); },duration);
	}
	
	return {
		close : close,
		pushButton : pushButton,
		showAndHide : showAndHide
	}
	
});