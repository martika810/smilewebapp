package com.madcoding.smile.store;

import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;

import com.madcoding.smile.domain.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class UserStore extends Store {
	
	private static UserStore INSTANCE = null;
	private final static String USER_TABLE_NAME ="users";
	private final static String FIELD_ID = "_id";
	private final static String FIELD_EMAIL = "email";
	private final static String FIELD_PASSWORD = "password";
	private final static String FIELD_PEPPER = "pepper";
	
	private UserStore(){}
	
	public static UserStore getInstance(){
		if(INSTANCE == null){
			INSTANCE = new UserStore();
		}
		return INSTANCE;
	}
	
	public List<User> loadAllUsers(){
		List<User> users = new LinkedList<User>();

		DBCursor cursor = getCollection(USER_TABLE_NAME).find();

		while (cursor.hasNext()) {
			
			DBObject obj = cursor.next();
			users.add(makeUser(obj));
			
		}
		
		return users;
	}
	
	public User loadByEmail(String email){
		
		BasicDBObject whereClause = new BasicDBObject();
		whereClause.put(FIELD_EMAIL, email);
		DBObject dbUserObj = getCollection(USER_TABLE_NAME).findOne(whereClause);
		User user = makeUser(dbUserObj);
		return user;
		
	}
	
	public User insertUser(User user){
		DBObject dbUserObject = makeDBUserObject(user);
		getCollection(USER_TABLE_NAME).insert(dbUserObject);
		user.setId(((ObjectId)dbUserObject.get(FIELD_ID)).toString());
		return user;
	}
	
	private User makeUser(DBObject obj){
		User user = new User();
		user.setId(((ObjectId) obj.get(FIELD_ID)).toString());
		user.setEmail((String) obj.get(FIELD_EMAIL));
		user.setPassword((String) obj.get(FIELD_PASSWORD));
		return user;
	}
	
	private DBObject makeDBUserObject(User user){
		BasicDBObject dbUser = new BasicDBObject();
		dbUser.put(FIELD_EMAIL, user.getEmail());
		dbUser.put(FIELD_PASSWORD, user.getPassword());
		dbUser.put(FIELD_PEPPER, user.getPepper());
		return dbUser;
	}

}
