package com.mettl.poc.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mettl.poc.DataMigrationPocApplicationTests;
import com.mettl.poc.model.CandidateReport;

public class RedshiftRepositoryTests extends DataMigrationPocApplicationTests {

	@Autowired
	private RedshiftRepository redshiftRepository;

	@Test
	public void testRedshiftCopy() {
		redshiftRepository.copyToCandidateResultStaging("candidate_result_2018-03-20.csv.gz");
	}
	
	@Test
	public void testRedshiftCopyTagKVMapping() {
		redshiftRepository.copyToStagingTagKVMapping("tag_key_value_mapping_2018-03-21.csv.gz");
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
		redshiftRepository.copyToStagingTagKey("key_names_2018-03-20.csv.gz");
	}
	
	@Test
	public void testInsertTagKeys() {
		redshiftRepository.insertFromStagingTagKey();
	}
	
	@Test
	public void testUpdateCRQuery() throws JsonProcessingException {
		CandidateReport cr = new CandidateReport();
		cr.setCandidateInstanceId(1454812l);
		cr.setClientId(10906l);
		cr.setAssessmentMaxScore(30);
		
		/*ObjectMapper map = new ObjectMapper();
		System.out.println(map.writeValueAsString(cr));
		*/
		redshiftRepository.updateCandidateReport(cr);
	}
	
	@Test
	public void testFetchTagKeyName() {
		//System.out.println("--->" + redshiftRepository.fetchKeyIdByKeyName("Age"));
	}

}