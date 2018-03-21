package com.mettl.poc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mettl.poc.model.CRF;
import com.mettl.poc.model.CandidateReport;
import com.mettl.poc.model.CandidateResult;
import com.mettl.poc.repository.CandidateResultRepository;
import com.mettl.poc.repository.RedshiftRepository;

@Service
public class MigrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MigrationService.class);

	@Autowired
	private CandidateResultRepository candidateResultRepository;

	@Autowired
	private RedshiftRepository redshiftRepository;

	@Autowired
	private CRFService crfService;

	@Autowired
	private S3Service s3Service;

	public void migrate(String year, String month, String day) {
		try {
			List<CandidateResult> candidateResults = (List<CandidateResult>) candidateResultRepository.findByUpdatedOn(year, month, day);

			List<CandidateReport> crReports = new ArrayList<>();
			Iterator<CandidateResult> i = candidateResults.iterator();
			while (i.hasNext()) {
				CandidateResult cr = i.next();
				CandidateReport report = adaptReportFromCandidateResult(cr);
				crReports.add(report);
				i.remove();
			}
			
			Map<Long, Set<Long>> clientAndCandidateIidMap = new HashMap<>();
			for (CandidateReport cr : crReports) {
				Set<Long> cids = clientAndCandidateIidMap.get(cr.getClientId());
				if (cids == null || cids.isEmpty()) {
					cids = new HashSet<>();
					clientAndCandidateIidMap.put(cr.getClientId(), cids);
				} 
				cids.add(cr.getCandidateInstanceId());
			}
			
			Map<Long, Set<CRF>> candidateCRFMap = crfService.getCrfs(clientAndCandidateIidMap);
			for (CandidateReport cr: crReports) {
				cr.setCrfs(candidateCRFMap.get(cr.getCandidateInstanceId()));
			}
			
			this.migrateCandidateResults(crReports);
			this.migrateCRFKeys(crReports);
			this.migrateCRFValues(crReports);
			this.migrateCandidateTagMapping(crReports);

		} catch (Exception e) {
			LOGGER.error("Exception " + e.toString());
		}
	}

	private void migrateCandidateTagMapping(List<CandidateReport> crReports) {
		// TODO Auto-generated method stub
	}

	private void migrateCRFValues(List<CandidateReport> crReports) {
		// TODO Auto-generated method stub
	}

	private void migrateCRFKeys(List<CandidateReport> crReports) {
		s3Service.writeCRFKeysToS3(crReports, getPreviousDate());
		redshiftRepository.copyToTagKey("key_names_" + getPreviousDate() + ".csv.gz");
		redshiftRepository.insertFromStagingTagKey();
		redshiftRepository.clearStagingTable("tag_key", "staging_tag_key");
	}

	private void migrateCandidateResults(List<CandidateReport> crReports) {
		s3Service.writeCandidateResultsToS3(crReports, getPreviousDate());
		redshiftRepository.copyToCandidateResultStaging("candidate_result_" + getPreviousDate() + ".csv.gz");
		redshiftRepository.upsertFromStagingCandidateResult();
		redshiftRepository.clearStagingTable("staging_candidate_result", "candidate_result");
	}

	private CandidateReport adaptReportFromCandidateResult(CandidateResult cr) {
		CandidateReport report = new CandidateReport();
		report.setAssessmentId(cr.getAssessmentId());
		report.setAssessmentMaxScore(cr.getAssessmentMaxScore());
		report.setCandidateInstanceId(cr.getCandidateInstanceId());
		report.setCandidateScore(cr.getCandidateScore() == null ? 0 : cr.getCandidateScore());
		report.setClientId(cr.getClientId());
		report.setCorrectQuestionCount(cr.getCorrectQuestionCount() == null ? 0 : cr.getCorrectQuestionCount());
		report.setAttemptedQuestionCount(cr.getAttemptedQuestionCount() == null ? 0 : cr.getAttemptedQuestionCount());
		report.setIsCareerSuggestion(cr.getIsCareerSuggestion());
		report.setIsCognative(cr.getIsCognative());
		report.setIsDeleted(cr.getIsDeleted());
		report.setIsDownloaded(cr.getIsDownloaded());
		report.setIsNewReport(cr.getIsNewReport());
		report.setIsPersonality(cr.getIsPersonality());
		report.setIsStarred(cr.getIsStarred());
		report.setQuestionCount(cr.getQuestionCount() == null ? 0 : cr.getQuestionCount());
		report.setScheduleId(cr.getScheduleId());
		report.setTimeTaken(cr.getTimeTaken() == null ? 0 : cr.getTimeTaken());
		report.setUpdatedOn(cr.getUpdatedOn());
		report.setIsCareerSuggestion(cr.getIsCareerSuggestion());
		return report;
	}

	public static String getPreviousDate() {
		Date currentDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.add(Calendar.DAY_OF_MONTH, -1);
		Date currentDateMinusOne = c.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(currentDateMinusOne);
	}
}