package com.madcoding.smile.services;

import java.util.Set;

import com.madcoding.smile.helpers.UserHelper;

public class FindAllProfilePhotoTask implements Runnable {

	@Override
	public void run() {

			try {
					Set<String> userIds = UserHelper.getInstance().findAllUserIds();
					for (String userId : userIds) {
						ProfilePhotoProvider.getInstance().addProfilePhoto(userId);
					}
					ProfilePhotoProvider.getInstance().setUpdated(true);
			} catch (Exception e) {
				e.printStackTrace();
			}

		
	}

}
