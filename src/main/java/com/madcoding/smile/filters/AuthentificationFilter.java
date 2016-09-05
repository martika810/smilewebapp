package com.madcoding.smile.filters;





import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.madcoding.smile.cache.TokenCache;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Provider
public class AuthentificationFilter implements ContainerRequestFilter {

	 @Override
	 public ContainerRequest filter(ContainerRequest requestContext) {
		 
		 if(shouldIgnore(requestContext)){
			 return requestContext;
		 }
		 
		 String token = requestContext.getHeaderValue(ContainerRequest.AUTHORIZATION);
		 boolean isTokenValid = TokenCache.getInstance().isTokenValid(token);
		 if (!isTokenValid) {
			 
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
	
		}
	 return requestContext;
	 }
	 
	 private boolean shouldIgnore(ContainerRequest requestContext){
		 
		 boolean isLoginRequest = requestContext.getPath().equals("users/login") && requestContext.getMethod().equals("POST");
		 boolean isCreateUserRequest = requestContext.getPath().equals("users") && requestContext.getMethod().equals("POST");
		 
		 if(isLoginRequest || isCreateUserRequest){
			 return true;
		 }else{
			 return false;
		 }
	 }
		 
	

}
