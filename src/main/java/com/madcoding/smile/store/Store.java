package com.madcoding.smile.store;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class Store {
	
	public static DBCollection getCollection(String tableName){
		
		DBCollection table = null;
		try{
		boolean auth = MongoClientInstance.getInstance().isAuth();
		if (auth) {
			table = MongoClientInstance.getInstance().getDB().getCollection(tableName);
			
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return table;
		
	}
	
	public BasicDBObject buildOrClause(String field,List<String> ids){
		BasicDBObject finalWhereClause = new BasicDBObject();
		
		if(ids == null ) return finalWhereClause;
		BasicDBList orList = new BasicDBList();
		for(String id : ids){
			BasicDBObject ownerClause = new BasicDBObject(field,id);
			orList.add(ownerClause);
		}
		
		if(!ids.isEmpty()){
			finalWhereClause.put("$or",orList);
		}
		return finalWhereClause;
	}
	
	public BasicDBObject buildIdsOrClause(String field,List<String> ids){
		BasicDBObject finalWhereClause = new BasicDBObject();
		
		if(ids == null ) return finalWhereClause;
		BasicDBList orList = new BasicDBList();
		for(String id : ids){
			BasicDBObject ownerClause = new BasicDBObject(field,new ObjectId(id));
			orList.add(ownerClause);
		}
		
		if(!ids.isEmpty()){
			finalWhereClause.put("$or",orList);
		}
		return finalWhereClause;
	}
}
