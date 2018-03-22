package com.mettl.poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mettl.poc.model.CandidateReport;
import com.mettl.poc.repository.RedshiftRepository;

@Service
public class CandidateReportService {

	@Autowired
	private RedshiftRepository redshiftRepository;
	
	public void updateCandidateReport(CandidateReport candidateReport) {
		redshiftRepository.updateCandidateReport(candidateReport);
	}
	
}
