package com.madcoding.smile.images;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.cloudinary.utils.StringUtils;

public class CloudinarySignGenerator {
	private static CloudinarySignGenerator INSTANCE = null;
	private CloudinarySignGenerator(){}
	
	public static CloudinarySignGenerator getInstance(){
		if(INSTANCE == null){
			INSTANCE = new CloudinarySignGenerator();
		}
		return INSTANCE;
	}
	private final static SecureRandom RND = new SecureRandom();
	
	public String randomPublicId() {
		byte[] bytes = new byte[8];
		RND.nextBytes(bytes);
		return StringUtils.encodeHexString(bytes);
	}
	
	public String makeSignature(Map<String, Object> paramsToSign, String apiSecret) {
		
		Collection<String> params = new ArrayList<String>();
		for (Map.Entry<String, Object> param : new TreeMap<String, Object>(paramsToSign).entrySet()) {
			if (param.getValue() instanceof Collection) {
				params.add(param.getKey() + "=" + StringUtils.join((Collection) param.getValue(), ","));
			} else {
				if (StringUtils.isNotBlank(param.getValue())) {
					params.add(param.getKey() + "=" + param.getValue().toString());
				}
			}
		}
		
		String to_sign = StringUtils.join(params, "&");
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Unexpected exception", e);
		}
		byte[] digest = md.digest(getUTF8Bytes(to_sign + apiSecret));
		return StringUtils.encodeHexString(digest);
	}
	
	byte[] getUTF8Bytes(String string) {
		try {
			return string.getBytes("UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			throw new RuntimeException("Unexpected exception", e);
		}		
	}

}
