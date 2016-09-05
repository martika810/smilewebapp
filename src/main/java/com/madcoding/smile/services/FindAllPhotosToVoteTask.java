package com.madcoding.smile.services;

import java.util.Set;

import com.madcoding.smile.helpers.UserHelper;

public class FindAllPhotosToVoteTask implements Runnable {

	@Override
	public void run() {

		
			try {
					Set<String> userIds = UserHelper.getInstance().findAllUserIds();
					for (String userId : userIds) {
						PhotoToVoteProvider.getInstance().addPhotosToUser(userId);
					}
			} catch (Exception e) {
				e.printStackTrace();
			}


	}

}
