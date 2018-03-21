package com.mettl.poc.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "candidate_result")
public class CandidateResult {

	@Id
	private Long id;
	private Long candidateInstanceId;
	private String candidateName;
	private String candidateEmail;
	private Long clientId;
	private Long assessmentId;
	private Long scheduleId;
	private String scheduleName;
	private String scheduleKey;
	private LocalDateTime testStartTime;
	private LocalDateTime testFinishTime;
	private Integer assessmentMaxScore;
	private Integer candidateScore;
	private Integer testFinishMode;
	private Integer blurCount;
	private Integer timeTaken;
	private Integer questionCount;
	private Integer resumeCount;
	private Integer attemptedQuestionCount;
	private Integer correctQuestionCount;
	private Integer isDeleted;
	private LocalDateTime updatedOn;
	private Integer isNewReport;
	private Integer isPersonality;
	private Integer isCognative;
	private Integer isCareerSuggestion;
	private Integer isDownloaded;
	private Integer isStarred;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCandidateInstanceId() {
		return candidateInstanceId;
	}
	public void setCandidateInstanceId(Long candidateInstanceId) {
		this.candidateInstanceId = candidateInstanceId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getCandidateEmail() {
		return candidateEmail;
	}
	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
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
	public String getScheduleName() {
		return scheduleName;
	}
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	public String getScheduleKey() {
		return scheduleKey;
	}
	public void setScheduleKey(String scheduleKey) {
		this.scheduleKey = scheduleKey;
	}
	public LocalDateTime getTestStartTime() {
		return testStartTime;
	}
	public void setTestStartTime(LocalDateTime testStartTime) {
		this.testStartTime = testStartTime;
	}
	public LocalDateTime getTestFinishTime() {
		return testFinishTime;
	}
	public void setTestFinishTime(LocalDateTime testFinishTime) {
		this.testFinishTime = testFinishTime;
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
	public Integer getTestFinishMode() {
		return testFinishMode;
	}
	public void setTestFinishMode(Integer testFinishMode) {
		this.testFinishMode = testFinishMode;
	}
	public Integer getBlurCount() {
		return blurCount;
	}
	public void setBlurCount(Integer blurCount) {
		this.blurCount = blurCount;
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
	public Integer getResumeCount() {
		return resumeCount;
	}
	public void setResumeCount(Integer resumeCount) {
		this.resumeCount = resumeCount;
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
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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
	public Integer getIsStarred() {
		return isStarred;
	}
	public void setIsStarred(Integer isStarred) {
		this.isStarred = isStarred;
	}

	@Override
	public String toString() {
		return "CandidateResult [id=" + id + ", candidateInstanceId=" + candidateInstanceId + ", candidateName="
				+ candidateName + ", candidateEmail=" + candidateEmail + ", clientId=" + clientId + ", assessmentId="
				+ assessmentId + ", scheduleId=" + scheduleId + ", scheduleName=" + scheduleName + ", scheduleKey="
				+ scheduleKey + ", testStartTime=" + testStartTime + ", testFinishTime=" + testFinishTime
				+ ", assessmentMaxScore=" + assessmentMaxScore + ", candidateScore=" + candidateScore
				+ ", testFinishMode=" + testFinishMode + ", blurCount=" + blurCount + ", timeTaken=" + timeTaken
				+ ", questionCount=" + questionCount + ", resumeCount=" + resumeCount + ", attemptedQuestionCount="
				+ attemptedQuestionCount + ", correctQuestionCount=" + correctQuestionCount + ", isDeleted=" + isDeleted
				+ ", updatedOn=" + updatedOn + ", isNewReport=" + isNewReport + ", isPersonality=" + isPersonality
				+ ", isCognative=" + isCognative + ", isCareerSuggestion=" + isCareerSuggestion + ", isDownloaded="
				+ isDownloaded + ", isStarred=" + isStarred + "]";
	}
	
}