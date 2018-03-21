package com.mettl.poc.repository;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mettl.poc.DataMigrationPocApplicationTests;
import com.mettl.poc.model.CandidateResult;

public class CandidateRepositoryTests extends DataMigrationPocApplicationTests {

	@Autowired
	private CandidateResultRepository candidateResultRepository;
	
	@Test
	public void testFindCR() {
		Optional<CandidateResult> cr = candidateResultRepository.findById(3L);
		if (cr.isPresent()) {
			System.out.println(cr.get());
		}
		System.out.println("----------");
	}
	
	@Test
	public void testFindByDate() {
		List<CandidateResult> cr = candidateResultRepository.findByUpdatedOn("2018","03","16");
		System.out.println("----------" + cr.size());
	}
	
	
}