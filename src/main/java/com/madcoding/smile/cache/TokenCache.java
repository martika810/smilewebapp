package com.madcoding.smile.cache;

import java.util.HashMap;
import java.util.Map;

import com.madcoding.smile.domain.TokenKey;

public class TokenCache {
	
	private static TokenCache INSTANCE = null;
	private Map<String,TokenKey> tokensMap = new HashMap<String,TokenKey>();
	
	private TokenCache(){};
	
	public static TokenCache getInstance(){
		if(INSTANCE == null){
			INSTANCE = new TokenCache();
		}
		return INSTANCE;
	}
	
	public String add(String userId){
		TokenKey generatedToken = TokenKey.makeInstance(userId);
		tokensMap.put(generatedToken.getToken(),generatedToken);
		cleanTokenCache();
		return generatedToken.getToken();
	}
	
	public boolean isTokenValid(String token){
		
		TokenKey checkedToken =tokensMap.get(token);
		return (checkedToken == null) ? false : checkedToken.isValid();
		
	}
	
	public TokenKey getToken(String token){
		return tokensMap.get(token);
	}
	
	public void extendToken(String token){
		TokenKey extendedToken = tokensMap.get(token);
		extendedToken.extendToken();
	}
	
	private void cleanTokenCache(){
		
		for(TokenKey token:tokensMap.values()){
			if(!token.isValid()){
				tokensMap.remove(token);
			}
		}
	}
}
