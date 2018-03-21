package com.mettl.poc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mettl.poc.model.CandidateResult;

@Repository
public interface CandidateResultRepository extends CrudRepository<CandidateResult, Long> {

	Optional<CandidateResult> findById(Long id);
	
	
	@Query(value = "SELECT * FROM candidate_result WHERE (DATEPART(yy, updated_on) = ?1 AND DATEPART(mm, updated_on) = ?2 AND DATEPART(dd, updated_on) = ?3)", nativeQuery = true)
	List<CandidateResult> findByUpdatedOn(String year, String month, String day);
	
}