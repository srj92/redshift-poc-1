package com.mettl.poc.repository;

import java.util.Map;
import java.util.Set;

public interface MongoRepository {

	Map<Integer, String> getClientCrfKeys(Long clientId);

	Map<Integer, String> getCandidateCrfValues(Long cid);

}
