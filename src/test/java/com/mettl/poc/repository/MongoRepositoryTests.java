package com.mettl.poc.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mettl.poc.DataMigrationPocApplicationTests;

public class MongoRepositoryTests extends DataMigrationPocApplicationTests {
	
	@Autowired
	private MongoRepository mongoRepository;
	
	@Test
	public void testQueryByClientId() {
		mongoRepository.getClientCrfKeys(10785l);
	}
	
	@Test
	public void testQueryByCiid() {
		mongoRepository.getCandidateCrfValues(1454710l);
	}
}
