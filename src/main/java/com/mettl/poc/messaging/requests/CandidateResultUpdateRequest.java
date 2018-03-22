package com.mettl.poc.messaging.requests;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateResultUpdateRequest {

	private Long candidateInstanceId;
	private Long clientId;
	private Long assessmentId;
	private Long scheduleId;
	private Integer assessmentMaxScore;
	private Integer candidateScore;
	private Integer timeTaken;
	private Integer questionCount;
	private Integer attemptedQuestionCount;
	private Integer correctQuestionCount;
	private LocalDateTime updatedOn;
	private Integer isNewReport;
	private Integer isPersonality;
	private Integer isCognative;
	private Integer isCareerSuggestion;
	private Integer isDownloaded;
	private Integer isDeleted;
	private Integer isStarred;

	public Long getCandidateInstanceId() {
		return candidateInstanceId;
	}
	public void setCandidateInstanceId(Long candidateInstanceId) {
		this.candidateInstanceId = candidateInstanceId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Integer getAssessmentMaxScore() {
		return assessmentMaxScore;
	}
	public void setAssessmentMaxScore(Integer assessmentMaxScore) {
		this.assessmentMaxScore = assessmentMaxScore;
	}
	public Integer getCandidateScore() {
		return candidateScore;
	}
	public void setCandidateScore(Integer candidateScore) {
		this.candidateScore = candidateScore;
	}
	public Integer getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(Integer timeTaken) {
		this.timeTaken = timeTaken;
	}
	public Integer getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}
	public Integer getAttemptedQuestionCount() {
		return attemptedQuestionCount;
	}
	public void setAttemptedQuestionCount(Integer attemptedQuestionCount) {
		this.attemptedQuestionCount = attemptedQuestionCount;
	}
	public Integer getCorrectQuestionCount() {
		return correctQuestionCount;
	}
	public void setCorrectQuestionCount(Integer correctQuestionCount) {
		this.correctQuestionCount = correctQuestionCount;
	}
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Integer getIsNewReport() {
		return isNewReport;
	}
	public void setIsNewReport(Integer isNewReport) {
		this.isNewReport = isNewReport;
	}
	public Integer getIsPersonality() {
		return isPersonality;
	}
	public void setIsPersonality(Integer isPersonality) {
		this.isPersonality = isPersonality;
	}
	public Integer getIsCognative() {
		return isCognative;
	}
	public void setIsCognative(Integer isCognative) {
		this.isCognative = isCognative;
	}
	public Integer getIsCareerSuggestion() {
		return isCareerSuggestion;
	}
	public void setIsCareerSuggestion(Integer isCareerSuggestion) {
		this.isCareerSuggestion = isCareerSuggestion;
	}
	public Integer getIsDownloaded() {
		return isDownloaded;
	}
	public void setIsDownloaded(Integer isDownloaded) {
		this.isDownloaded = isDownloaded;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Integer getIsStarred() {
		return isStarred;
	}
	public void setIsStarred(Integer isStarred) {
		this.isStarred = isStarred;
	}
	
}
