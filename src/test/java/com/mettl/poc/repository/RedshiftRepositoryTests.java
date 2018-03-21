package com.mettl.poc.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mettl.poc.DataMigrationPocApplicationTests;

public class RedshiftRepositoryTests extends DataMigrationPocApplicationTests {

	@Autowired
	private RedshiftRepository redshiftRepository;

	@Test
	public void testRedshiftCopy() {
		redshiftRepository.copyToCandidateResultStaging("candidate_result_2018-03-20.csv.gz");
	}

	@Test
	public void testUpsertCandidateResults() {
		redshiftRepository.upsertFromStagingCandidateResult();
	}

	@Test
	public void testClearTable() {
		redshiftRepository.clearStagingTable("staging_candidate_result", "candidate_result");
	}

	@Test
	public void testRedshiftCopyKeys() {
		redshiftRepository.copyToTagKey("key_names_2018-03-20.csv.gz");
	}
	
	@Test
	public void testInsertTagKeys() {
		redshiftRepository.insertFromStagingTagKey();
	}
	
	@Test
	public void testFetchTagKeyName() {
		System.out.println("--->" + redshiftRepository.fetchKeyIdByKeyName("Age"));
	}

}