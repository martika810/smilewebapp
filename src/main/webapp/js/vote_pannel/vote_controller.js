define([ 'jquery', 'utils/collections', 'vote_pannel/vote_pannel',
		'vote_pannel/vote_provider' ], function($, Collections, VotePannel,
		VoteProvider) {

	var photosToVote = new Object();
	var photosVoted = [];

	var loadPhotosToVote = function() {

		var successCallback = function(data) {
			var photosMap = Collections.convertArrayToMap(data);
			photosToVote = $.extend(photosToVote, photosMap);
			if(VotePannel.isImageToVoteEmpty()){
				VotePannel.fetchFirstPhoto(getNextPhotoToVote());
				VotePannel.showLikeNextButton();
			}
		}

		var errorCallback = function(xhr, exception) {
			console.log('error loading photos to vote')
		}

		VoteProvider.loadPhotosToVote(successCallback, errorCallback);

	}

	var loadFirstPhotosToVote = function() {
		var successCallback = function(data) {
			var photosMap = Collections.convertArrayToMap(data);
			photosToVote = $.extend(photosToVote, photosMap);
			VotePannel.fetchFirstPhoto(getNextPhotoToVote());
		}

		var errorCallback = function(xhr, exception) {
			console.log('error loading photos to vote');
		}

		VoteProvider.loadPhotosToVote(successCallback, errorCallback);
	}

	var loadNextPhoto = function() {
		var nextPhoto = getNextPhotoToVote();
		if(nextPhoto === undefined){
			VotePannel.replaceByLoading();
			loadPhotosToVote();
		}else{
			localStorage.setItem('CURRENT_PHOTO_TO_VOTE',nextPhoto.id);
			VotePannel.loadNextPhoto(nextPhoto);
		}
	}

	var getNextPhotoToVote = function() {
		var isPhotosToVoteEmpty = Object.keys(photosToVote).length <= 0;
		if (!isPhotosToVoteEmpty) {
			var firstKey = Object.keys(photosToVote)[0];
			return photosToVote[firstKey];
		}
		if (shouldLoadPhotosToVote()) {
			loadPhotosToVote();
		}

	}

	var clearPhotosVoted = function() {
		photosVoted = [];
	}

	var addVote = function(votedPhotoId) {

		var newVote = new Object();
		newVote.voterUserId = localStorage.getItem('USERID');
		newVote.votedPhotoId = votedPhotoId;
		var isVoteAlreadySaved = $.inArray(newVote,photosVoted) !== -1;
		if(!isVoteAlreadySaved){
			photosVoted.push(newVote);
		}
		delete photosToVote[votedPhotoId];
		if (shouldSendVotes()) {
			sendVotes();
		}

	}

	var passToNext = function(passedPhotoId) {
		delete photosToVote[passedPhotoId];
	}

	var sendVotes = function() {

		var successCallback = function(data) {
			clearPhotosVoted();
		}

		var errorCallback = function(xhr, exception) {
			console.log('Error saving votes');
		}
		VoteProvider.sendVotes(photosVoted, successCallback,
				errorCallback);

	}

	var shouldSendVotes = function() {
		var numberPhotosVoted = photosVoted.length;
		var isPhotosToVoteEmpty = Object.keys(photosToVote).length == 0;
		if (numberPhotosVoted >= 3 || isPhotosToVoteEmpty) {
			return true;
		} else {
			return false;
		}
	}

	var shouldLoadPhotosToVote = function() {
		var numberPhotosToVoteLoaded = Object.keys(photosToVote).length;
		if (numberPhotosToVoteLoaded < 3) {
			return true;
		} else {
			return false;
		}
	}

	return {

		loadPhotosToVote : loadPhotosToVote,
		loadFirstPhotosToVote : loadFirstPhotosToVote,
		loadNextPhoto : loadNextPhoto,
		addVote : addVote,
		passToNext : passToNext,
		sendVotes : sendVotes,
		shouldLoadPhotosToVote : shouldLoadPhotosToVote,
		shouldSendVotes : shouldSendVotes,
		getNextPhotoToVote : getNextPhotoToVote

	}
});