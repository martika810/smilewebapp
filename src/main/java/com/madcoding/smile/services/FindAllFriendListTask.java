package com.madcoding.smile.services;

import java.util.Set;

import com.madcoding.smile.helpers.UserHelper;

public class FindAllFriendListTask implements Runnable {

	@Override
	public void run() {


			try {
					Set<String> userIds = UserHelper.getInstance().findAllUserIds();
					for (String userId : userIds) {
						FriendListProvider.getInstance().addFriendList(userId);
					}
			} catch (Exception e) {
				e.printStackTrace();
			}


	}

}
