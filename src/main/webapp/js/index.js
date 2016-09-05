define(['jquery','coyote','static','validation/validation_public'],
		function($,Coyote,Static,Validation){
	var saveLocation = function(){
		 if (navigator.geolocation) {
		       navigator.geolocation.getCurrentPosition(saveLocationLocally,errorGetLocation);
		    } else {
		       x.innerHTML = "Geolocation is not supported by this browser.";
		    	
		    }
	}
	
	var saveLocationLocally = function(location){
		localStorage.setItem("LAT",location.coords.latitude);
		localStorage.setItem("LONG",location.coords.longitude);
	}
	
	var errorGetLocation = function(){
		var reply = prompt("Please allow the browser to pick your location to allow to see the selfies of people around you/n");
		if(reply){
			saveLocation()
		}else{
			return;
		}
	
	}
	var main = function(){
		var SIGNUP = 'signup';
		var LOGIN = 'login';
		saveLocation();
		
		$(".signing img").click(function(){
			var clickedImage = $(this).data('id');
			if(clickedImage == SIGNUP){
				$(".signing").animate({left:"-75px"});
				$(".signup").animate({left:"0px"});
			}else if(clickedImage == LOGIN){
				$(".signing").animate({left:"-75px"});
				$(".login").animate({left:"0px"});
			}
			
		});
		
		$(".close_btn").click(function(){
			var isCloseBtnFromLoginPanel = $(this).parent().hasClass("login");
				$(".login").animate({left:"-360px"})
				$(".signup").animate({left:"-360px"})
				$(".signing").animate({left:"0px"});
			
		});
		
		$(".login input[type=submit]").click(function(){
				
				var username = $(".login input[name='username']").val();
				var password = $(".login input[name='password']").val();
				var user = new Object();
				user.email=username;
				user.password = password;
				Coyote.post('/users/login',JSON.stringify(user),function(data){
					if(data!==undefined){
						localStorage.setItem("TOKEN",data.token);
						localStorage.setItem("USERID",data.userId);
					
						window.location =  Static.base_url + '/home.html';
						ga('send', 'pageview');
					}
				},function(xhr,exception){
					$('.login .error_message').text('');
					$('.login .error_message').text('Email/Password not valid');
				},null)
			
			
		});
		$(".signup input[type=submit]").click(function(){
			if(Validation.validateSignupForm()){
				//saveLocation();
				var username = $(".signup input[name='username']").val();
				var password = $(".signup input[name='password']").val();
				var user = new Object();
				user.email = username;
				user.password = password;
				Coyote.post('/users',JSON.stringify(user),function(data){
					if(data!==undefined){
						localStorage.setItem("TOKEN",data.token);
						localStorage.setItem("USERID",data.userId);
					
						window.location = Static.base_url + '/home.html';
					}
				},function(){
					console.log('Error creating user');
				});
			}
		});
		
	}
	
	
	
	return{
		main : main
		
		
	}

	
});
