package com.madcoding.smile.helpers;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.madcoding.smile.cache.TokenCache;
import com.madcoding.smile.domain.Friend;
import com.madcoding.smile.domain.Photo;
import com.madcoding.smile.domain.User;
import com.madcoding.smile.services.ProfilePhotoProvider;
import com.madcoding.smile.store.UserStore;
import com.madcoding.smile.utils.CollectionUtils;


public class UserHelper {
	
	private static UserHelper INSTANCE = null;
	
	private UserHelper(){}
	
	public static UserHelper getInstance(){
		if(INSTANCE == null){
			INSTANCE = new UserHelper();
		}
		return INSTANCE;
	}
	
	public Set<String> findAllUserIds(){
		List<User> users=UserStore.getInstance().loadAllUsers();
		Map<String,User> mapUsers = CollectionUtils.convertListToMap(users);
		return mapUsers.keySet();
	}
	
	public User populateUser(User user) throws NoSuchAlgorithmException{
		
		User populatedUser = user.clone();
		populatedUser.setPepper(generatePepper());
		populatedUser.setPassword(generatePassword(user.getPassword(), user.getPepper()));
		return populatedUser;
		
	}
	
	public boolean isValidUser(User user) throws NoSuchAlgorithmException{
		
		User existingUser = UserStore.getInstance().loadByEmail(user.getEmail());
		String enteredHashedPassword = generatePassword(user.getPassword(), existingUser.getPepper());
		boolean isPasswordValid = enteredHashedPassword.equals(existingUser.getPassword());
		return isPasswordValid;
		
	}
	
	public Friend findFriendByEmail(String email){
		User foundUser = UserStore.getInstance().loadByEmail(email);
		Photo userMainPhoto = ProfilePhotoProvider.getInstance().getMainProfilePhoto(foundUser.getId());
		return Friend.makeInstance(foundUser, userMainPhoto);
	}
	
	public String generateToken(String userId){
		
		return TokenCache.getInstance().add(userId);
		
		
	}
	
	private String generatePassword(String password,String pepper) throws NoSuchAlgorithmException{
		return generateHash(password+pepper);
	}
	
	private String generatePepper() throws NoSuchAlgorithmException{
		return generateHash(generateKey());
	}
	
	private String generateHash(String text) throws NoSuchAlgorithmException{
		MessageDigest md5=MessageDigest.getInstance("MD5");
		md5.update(text.getBytes());
		byte[] digest = md5.digest();
		StringBuffer sb = new StringBuffer();
		for(byte b:digest){
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
		
	}
	

	
	private String generateKey(){
		byte[] array = new byte[4];
		new Random().nextBytes(array);
		String generatedKey = new String(array,Charset.forName("UTF-8"));
		return generatedKey;
	}

}
