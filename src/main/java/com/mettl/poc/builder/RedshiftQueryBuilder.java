package com.mettl.poc.builder;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.mettl.poc.model.CandidateReport;

@Component
public class RedshiftQueryBuilder {

	public String buildCandidateReportUpdateSql(CandidateReport cr) {
		
		String updateQueryPrefix = "update candidate_result set ";
		String whereCondition = " where candidate_instance_id = " + cr.getCandidateInstanceId() + ";";
		
		StringBuilder cols = new StringBuilder();
		if (cr.getAssessmentId() != null) {
			cols.append("assessment_id = " + cr.getAssessmentId() + ",");
		}
		if (cr.getAssessmentMaxScore() != null) {
			cols.append("assessment_max_score = " + cr.getAssessmentMaxScore() + ",");
		}
		if (cr.getAttemptedQuestionCount() != null) {
			cols.append("attempted_question_count = " + cr.getAttemptedQuestionCount() + ",");
		}
		if (cr.getCandidateScore() != null) {
			cols.append("candidate_score = " + cr.getCandidateScore() + ",");
		}
		if (cr.getClientId() != null) {
			cols.append("client_id = " + cr.getClientId() + ",");
		}
		if (cr.getCorrectQuestionCount() != null) {
			cols.append("correct_question_count = " + cr.getCorrectQuestionCount() + ",");
		}
		if (cr.getIsCareerSuggestion() != null) {
			cols.append("is_career_suggestion = " + cr.getIsCareerSuggestion() + ",");
		}
		if (cr.getIsCognative() != null) {
			cols.append("is_cognative = " + cr.getIsCognative() + ",");
		}
		if (cr.getIsDeleted() != null) {
			cols.append("is_deleted = " + cr.getIsDeleted() + ",");
		}
		if (cr.getIsDownloaded() != null) {
			cols.append("is_downloaded = " + cr.getIsDownloaded() + ",");
		}
		if (cr.getIsNewReport() != null) {
			cols.append("is_new_report = " + cr.getIsNewReport() + ",");
		}
		if (cr.getIsPersonality() != null) {
			cols.append("is_personality = " + cr.getIsPersonality() + ",");
		}
		if (cr.getQuestionCount() != null) {
			cols.append("question_count = " + cr.getQuestionCount() + ",");
		}
		if (cr.getScheduleId() != null) {
			cols.append("schedule_id = " + cr.getScheduleId() + ",");
		}
		if (cr.getTimeTaken() != null) {
			cols.append("time_taken = " + cr.getTimeTaken() + ",");
		}
		cols.append("updated_on = '" + LocalDateTime.now() + "' ");
		return updateQueryPrefix + cols.toString() + whereCondition;
	}
	
	
}
