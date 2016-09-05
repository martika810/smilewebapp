package com.madcoding.smile.services;

import java.util.Set;

import com.madcoding.smile.helpers.UserHelper;

public class FindAllRankPhotoTask implements Runnable{

	@Override
	public void run() {
		

			try {
					Set<String> userIds = UserHelper.getInstance().findAllUserIds();
					for (String userId : userIds) {
						RankPhotoProvider.getInstance().addRankPhoto(userId);
					}
			} catch (Exception e) {
				e.printStackTrace();
			}

		
	}

}
