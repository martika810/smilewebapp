define([ 'jquery', 'profile/profile' ],
function($, Profile) {

	var PANNEL = '<div id="notification_pannel" class="hidden">'
					+ '<div class="close-btn">'
					+ '<img src="img/close_icon.png" height="20px" width="20px">'
					+'</div>'
				+ '</div>';
			

			var showPannel = function() {
				$('#notification_pannel').removeClass('hidden');
			}

			var closePannel = function() {
				$('#notification_pannel').addClass('hidden');
			}

			

			var createNumberIcon = function(numberNotification) {
				var NOTIFICATION_ICON = '<div class="number_icon">'
										+ '<div class="number_txt">' + numberNotification
						+ '</div>' + '</div>';
				return NOTIFICATION_ICON;
			}

			var createNotificationMessage = function(notification) {
				var message;
				if (notification.type == "VOTE") {

					message = '<div>'
							+ '<span>like your selfie</span>'
							+ '<span class="target_image_spot">'
							+ '</span>'
							+ '<div><img src="img/like.png" height="35px" width="35px"></div>'
							+ '</div>';
				}else if(notification.type == "FRIEND"){
					message = '<div>'
						+ '<span>Please add me to your friends</span>'
						+ '<div><button type="button" class="accept-btn btn btn-success">Accept</button></div>'
						+ '</div>';
				}

				return message;
			}
			
			var createRow = function(notification){
				var row = '<div class="row notification_row not_seen" data-friendid="'+notification.senderId.id+'">'
							+ '<div class="col-sm-4 notification_photo">'
							+ '</div>'
							+ '<div class="col-sm-7 notification_message">'
							+ '</div>' 
						+ '</div>';
				return row;
				
			}

			var createNotificationRow = function(notification) {
				if (notification.senderId.photo !== undefined) {
					var senderImageUrl = notification.senderId.photo.url;

					var senderImage = $.cloudinary.image(senderImageUrl, {
						width : 80,
						height : 80,
						gravity : "face"
					}).addClass('sender_image');
				}else{
					var senderImage ='<img class="sender_image" src="img/profile.png" height="80px" width="80px">';
				}
				
				var message = createNotificationMessage(notification);
				var row = createRow(notification);
				$('#notification_pannel').append(row);
				$('#notification_pannel .notification_photo').last().append(senderImage);
				$('#notification_pannel .notification_message').last().append(message);
				$('#notification_pannel .notification_row').last().data('id',notification.id);

				if (notification.type == "VOTE") {
					var targetImageUrl = notification.targetId.photo.url;
					var targetImage = $.cloudinary.image(targetImageUrl, {
						width : 30,
						height : 30,
						gravity : "face"
					}).addClass('target_image');
					$('#notification_pannel .notification_message .target_image_spot').last().append(targetImage);
				}

			}

			var populateNotificationPannel = function(notifications) {
				$('#notification_pannel .notification_row').remove();
				for (var i = 0; i < notifications.length; i++) {
					createNotificationRow(notifications[i]);
				}
				$('#notification_pannel .notification_row').first().addClass('first');
			}

			var addNotificationNumberIcon = function(numberNotification) {
				var isNoticationNumberAlreadyAttached = $('#notification_icon .number_icon').length>0;
				if(isNoticationNumberAlreadyAttached){
					if(numberNotification == 0){
						$('#notification_icon .number_icon').css('visibility','hidden');
						///
					}else{
						$('#notification_icon .number_txt').html(numberNotification);
					}
				}else{
					if (numberNotification > 0) {
						$('#notification_btn').css({
							'margin-top' : '-20px'
						});
						$('#notification_icon').prepend(
							createNumberIcon(numberNotification));
					}
				}

			}

			var createNotificationPannel = function() {
				$('body').append(PANNEL);
				$('#notification_pannel').css({
					left : $('#notification_btn').offset().left + 'px'
				});

			}

			return {
				showPannel : showPannel,
				closePannel : closePannel,
				createNotificationPannel : createNotificationPannel,
				addNotificationNumberIcon : addNotificationNumberIcon,
				populateNotificationPannel : populateNotificationPannel
			}
		});