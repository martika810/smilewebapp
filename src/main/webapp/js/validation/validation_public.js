define(['jquery'],function($){
	
	var validateSignupForm = function(){
		var email = $('.signup .email_input').val();
		var password = $('.signup .password_input').val();
		var confirm = $('.signup .confirm_input').val();
		var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		var isValidEmail= re.test(email);
		var isValidPassword = (password.length >=6) && (password == confirm);
		if(!isValidEmail){
			$('.signup .error_message').text('Email is not valid');
			return false;
		}
		if(!isValidPassword){
			$('.signup .error_message').text('Password is not valid');
			return false;
		}
		return true;
	}
	return{
		validateSignupForm : validateSignupForm
	}
	
});