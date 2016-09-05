define(['jquery','animation'],function($,Animation){
	
	var showFeedbackResponse = function(message){
		$('#response_pannel').text('');
		$('#response_pannel').text(message);
		Animation.showAndHide($('#response_pannel'),4000);
	}
	return{
		showFeedbackResponse : showFeedbackResponse
	}
});