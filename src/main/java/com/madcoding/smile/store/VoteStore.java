package com.madcoding.smile.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.madcoding.smile.domain.Vote;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class VoteStore extends Store {

	private static VoteStore INSTANCE = null;

	private final static String VOTE_TABLE_NAME = "votes";
	private final static String FIELD_ID = "_id";
	private final static String FIELD_VOTER_USER_ID = "voter_user_id";
	private final static String FIELD_VOTED_PHOTO_ID = "voted_photo_id";

	private VoteStore() {
	}

	public static VoteStore getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VoteStore();
		}
		return INSTANCE;
	}

	public Vote loadVote(String voteId) {
		BasicDBObject whereClause = new BasicDBObject(FIELD_ID, new ObjectId(voteId));
		DBObject vote = getCollection(VOTE_TABLE_NAME).findOne(whereClause);
		return makeVote(vote);
	}

	public int findVotesByPhotoId(String photoId) {

		BasicDBObject whereQueryByPhotoId = new BasicDBObject();
		whereQueryByPhotoId.put(FIELD_VOTED_PHOTO_ID, photoId);
		DBCursor dbVotesCursor = getCollection(VOTE_TABLE_NAME).find(whereQueryByPhotoId);
		return dbVotesCursor.size();
	}

	public List<Vote> findVotesByUserId(String userId) {

		BasicDBObject whereQueryByUserId = new BasicDBObject();
		whereQueryByUserId.put(FIELD_VOTER_USER_ID, userId);
		DBCursor dbVotesCursor = getCollection(VOTE_TABLE_NAME).find(whereQueryByUserId);
		return makeVotes(dbVotesCursor);

	}

	public List<Vote> findVotedPhotos(List<String> photoIds, String userId) {
		BasicDBObject whereClause = buildFullVotedPhotosWhereClause(photoIds, userId);
		DBCursor dbVoteCursor = getCollection(VOTE_TABLE_NAME).find(whereClause);
		return makeVotes(dbVoteCursor);

	}

	public Map<String, Integer> countVotesForPhotos(List<String> photoIds) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		return map;
	}

	public Vote insert(Vote vote) {
		try {
			BasicDBObject dbVoteObject = makeDBVoteObject(vote);
			getCollection(VOTE_TABLE_NAME).insert(dbVoteObject);
			vote.setId(((ObjectId) dbVoteObject.get(FIELD_ID)).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vote;

	}

	public void delete(String voteId) {
		BasicDBObject whereClause = new BasicDBObject(FIELD_ID, new ObjectId(voteId));
		getCollection(VOTE_TABLE_NAME).remove(whereClause);
	}

	private BasicDBList buildPhotosWhereClause(List<String> photos) {
		BasicDBList orList = new BasicDBList();
		for (String photoId : photos) {
			BasicDBObject photoClause = new BasicDBObject(FIELD_VOTED_PHOTO_ID, photoId);
			orList.add(photoClause);
		}
		// BasicDBObject whereClause = new BasicDBObject("$or",orList);
		return orList;
	}

	private BasicDBObject buildFullVotedPhotosWhereClause(List<String> photos, String userId) {
		BasicDBList orList = buildPhotosWhereClause(photos);
		BasicDBObject whereClause = new BasicDBObject();
		if (!orList.isEmpty()) {
			whereClause.put("$or", orList);
		}
		whereClause.put(FIELD_VOTER_USER_ID, userId);
		return whereClause;
	}

	private List<Vote> makeVotes(DBCursor dbVoteCursor) {
		List<Vote> voteList = new ArrayList<Vote>();
		while (dbVoteCursor.hasNext()) {
			DBObject dbVoteObj = dbVoteCursor.next();
			voteList.add(makeVote(dbVoteObj));
		}
		return voteList;
	}

	private Vote makeVote(DBObject obj) {

		Vote vote = Vote.makeInstance();
		if (obj == null)
			return null;
		vote.setId(((ObjectId) obj.get(FIELD_ID)).toString());
		vote.setVoterUserId((String) obj.get(FIELD_VOTER_USER_ID));
		vote.setVotedPhotoId((String) obj.get(FIELD_VOTED_PHOTO_ID));
		return vote;

	}

	private BasicDBObject makeDBVoteObject(Vote vote) {

		BasicDBObject dbVoteObject = new BasicDBObject();
		dbVoteObject.put(FIELD_VOTER_USER_ID, vote.getVoterUserId());
		dbVoteObject.put(FIELD_VOTED_PHOTO_ID, vote.getVotedPhotoId());
		return dbVoteObject;

	}
}
