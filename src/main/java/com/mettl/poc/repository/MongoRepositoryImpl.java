package com.mettl.poc.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Repository
public class MongoRepositoryImpl implements MongoRepository {

	//private static final Logger LOGGER = LoggerFactory.getLogger(MongoRepositoryImpl.class);
	private static final String DB = "Mettl";

	@Autowired
	private MongoClient mongoClient;

	@Override
	public Map<Integer, String> getClientCrfKeys(Long clientId) {
		MongoDatabase db = mongoClient.getDatabase(DB);
		MongoCollection<Document> collection = db.getCollection("ClientRegistrationFields");

		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("ClientID", clientId));
		obj.add(new BasicDBObject("LanguageId", 1));
		andQuery.put("$and", obj);
		
		final Document doc = collection.find(andQuery).first();
		List<Document> fields = (List<Document>) doc.get("Fields");
		Map<Integer, String> clientCrfKeysMap = new HashMap<>();
		for (Document keyDoc : fields) {
			clientCrfKeysMap.put(keyDoc.getInteger("RegistrationFieldID"), keyDoc.get("DisplayName").toString());
		}
		//LOGGER.info("----> " + clientCrfKeysMap.values());
		return clientCrfKeysMap;
	}

	@Override
	public Map<Integer, String> getCandidateCrfValues(Long cid) {
		MongoDatabase db = mongoClient.getDatabase(DB);
		MongoCollection<Document> collection = db.getCollection("CandidateInstaceDyanmicField");
		final Document doc = collection.find(Filters.eq("_id", cid)).first();
		List<List<Object>> fieldValues = (List<List<Object>>) doc.get("FieldValues");
		Map<Integer, String> candidateCrfValuesMap = new HashMap<>();
		for (List<Object> docs : fieldValues) {
			candidateCrfValuesMap.put(Integer.valueOf(docs.get(0).toString()),
					docs.get(1) == null ? null : docs.get(1).toString());
		}
		return candidateCrfValuesMap;
	}

}