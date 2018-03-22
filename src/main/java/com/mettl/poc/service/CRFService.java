package com.mettl.poc.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mettl.poc.model.CandidateInstanceTag;
import com.mettl.poc.repository.MongoRepository;

@Service
public class CRFService {

	@Autowired
	private MongoRepository mongoRepository;

	private Map<Long, Map<Integer, String>> clientCrfKeyMapping = new HashMap<>();

	private Set<CandidateInstanceTag> mergeCrfKeyValues(Map<Integer, String> crfKeyMap, Map<Integer, String> crfValuesMap) {
		Set<CandidateInstanceTag> crfSet = new HashSet<>();
		Set<Integer> keyIdSet = crfKeyMap.keySet();
		for (Integer keyId : keyIdSet) {
			CandidateInstanceTag crf = new CandidateInstanceTag();
			crf.setCrf(true);
			crf.setKey(crf.new Key(crfKeyMap.get(keyId)));
			crf.setValue(crf.new Value(crfValuesMap.get(keyId)));
			crfSet.add(crf);
		}
		return crfSet;
	}

	public synchronized Map<Long, Set<CandidateInstanceTag>> getCrfs(Map<Long, Set<Long>> clientAndCandidateIdMap) {
		Map<Long, Set<CandidateInstanceTag>> resultCiidCrfMap = new HashMap<>();
		Map<Long, Map<Integer, String>> crfKeysMap = new HashMap<>();
		
		for (Long clientId : clientAndCandidateIdMap.keySet()) {
			if (!crfKeysMap.containsKey(clientId)) {
				crfKeysMap.put(clientId, getCrfKeyMap(clientId));
			}
			Set<Long> ciids = clientAndCandidateIdMap.get(clientId);
			for (Long ciid : ciids) {
				Map<Integer, String> crfValuesMap = mongoRepository.getCandidateCrfValues(ciid);
				Set<CandidateInstanceTag> crfsForACiid = mergeCrfKeyValues(crfKeysMap.get(clientId), crfValuesMap);
				resultCiidCrfMap.put(ciid, crfsForACiid);
			}
		}
		// Clear cache
		clientCrfKeyMapping = new HashMap<>();
		return resultCiidCrfMap;
	}

	private Map<Integer, String> getCrfKeyMap(Long clientId) {
		Map<Integer, String> keyMap = null;
		if (clientCrfKeyMapping.containsKey(clientId)) {
			keyMap = clientCrfKeyMapping.get(clientId);
		} else {
			keyMap = mongoRepository.getClientCrfKeys(clientId);
			clientCrfKeyMapping.put(clientId, keyMap);
		}
		return keyMap;
	}

}