package com.madcoding.smile.store;

import java.net.UnknownHostException;

import com.madcoding.smile.conf.SmileProperties;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoClientInstance {
	
	private MongoClient client;
	private static MongoClientInstance INSTANCE = null;
	private final static String HOST = SmileProperties.getInstance().getProperty("dbmongo_host");
	private final static int DB_PORT = 27017; //27017
	private final static String SMILE_DATABASE = "smilewebapp";
	private final static String USER = "admin";
	private final static String PASSW = "_69Kv2xwdtEb";
	private final static int MAX_NUMBER_TRIES = 3;
	

	public static MongoClientInstance getInstance(){
		if(INSTANCE == null){
			INSTANCE = init();
		}
		return INSTANCE;
	}
	private MongoClientInstance(MongoClient client){
		this.client=client;
	}
	
	private static MongoClientInstance init(){
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(HOST, DB_PORT);
			//createGeoSpatialIndex(mongoClient);
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
		MongoClientInstance clientInstance = new MongoClientInstance(mongoClient);
		
		return clientInstance;
		
	}

	public MongoClient getClient() {
		return client;
	}
	
	public DB getDB(){
		return getClient().getDB(SMILE_DATABASE);
		
	}
	
	private static void createGeoSpatialIndex(MongoClient client){
		client.getDB(SMILE_DATABASE).getCollection("profiles").ensureIndex(new BasicDBObject("location", "2d"), "geospacialIdx");
	}

	public boolean isAuth() throws Exception {
		//return isAuth(0);
		boolean isAuth = getDB().authenticate(USER, PASSW.toCharArray());
		if(!isAuth){
			throw new Exception("Authentification error");
		}
		return isAuth;
		
	}
	
	public boolean isAuth(int tryNumber) throws Exception{
		
		boolean isAuth = false;
		try{
			isAuth = getDB().authenticate(USER, PASSW.toCharArray());
			if(!isAuth){
				throw new Exception("Authentification error");
			}
		}catch(Exception ex){
			if(tryNumber>=MAX_NUMBER_TRIES){
				throw ex;
			}else{
				isAuth(tryNumber++);
			}
		}
		return isAuth;
	}
	
	

	
}
