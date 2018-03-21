package com.mettl.poc.service;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mettl.poc.model.CRF;
import com.mettl.poc.model.CandidateReport;
import com.mettl.poc.model.TagValue;

@Service
public class S3Service {

	private static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	public void writeCandidateResultsToS3(List<CandidateReport> crReports, String yyyymmdd) {

		try {
			Path path = Files.createTempFile("candidate_result", ".csv");
			File temp = path.toFile();

			// write data on temporary file
			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

			for (CandidateReport cr : crReports) {
				bw.write(cr.getCandidateInstanceId() + "");
				bw.write(',');
				bw.write(cr.getClientId() + "");
				bw.write(',');
				bw.write(cr.getAssessmentId() + "");
				bw.write(',');
				bw.write(cr.getScheduleId() + "");
				bw.write(',');
				bw.write(cr.getAssessmentMaxScore() + "");
				bw.write(',');
				bw.write(cr.getCandidateScore() + "");
				bw.write(',');
				bw.write(cr.getTimeTaken() + "");
				bw.write(',');
				bw.write(cr.getQuestionCount() + "");
				bw.write(',');
				bw.write(cr.getAttemptedQuestionCount() + "");
				bw.write(',');
				bw.write(cr.getCorrectQuestionCount() + "");
				bw.write(',');
				bw.write(cr.getUpdatedOn() + "");
				bw.write(',');
				bw.write(cr.getIsNewReport() + "");
				bw.write(',');
				bw.write(cr.getIsPersonality() + "");
				bw.write(',');
				bw.write(cr.getIsCognative() + "");
				bw.write(',');
				bw.write(cr.getIsCareerSuggestion() + "");
				bw.write(',');
				bw.write(cr.getIsDownloaded() + "");
				bw.write(',');
				bw.write(cr.getIsDeleted() + "");
				bw.write('\n');
			}
			bw.close();
			temp.deleteOnExit();

			File output = new File(temp.getName() + ".gz");
			GzipCompressor.compressGZIP(temp, output);

			s3client.putObject(new PutObjectRequest("redshift-poc-mettl/candidate_result_dump",
					"candidate_result_" + yyyymmdd + ".csv.gz", output));

		} catch (AmazonServiceException e) {
			LOGGER.error("Exception: " + e);
		} catch (Exception ex) {
			LOGGER.error("Exception: " + ex);
		}
	}
	
	public void writeCRFKeysToS3(List<CandidateReport> crReports, String yyyymmdd) {
		try {
			Set<String> keyNames = new HashSet<>();
			for (CandidateReport cr : crReports) {
				Set<CRF> crfSet = cr.getCrfs();
				if (crfSet != null) {
					for (CRF crf: crfSet) {
						CRF.Key key = crf.getKey();
						if (!keyNames.contains(key.getKeyName())) {
							keyNames.add(key.getKeyName());
						}
					}	
				}
			}

			Path path = Files.createTempFile("crf_key_names", ".csv");
			File temp = path.toFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
			
			for (String keyName : keyNames) {
				bw.write(keyName + "");
				bw.write('\n');
			}
			bw.close();
			temp.deleteOnExit();

			File output = new File(temp.getName() + ".gz");
			GzipCompressor.compressGZIP(temp, output);

			s3client.putObject(new PutObjectRequest("redshift-poc-mettl/tag_key_dump",
					"key_names_" + yyyymmdd + ".csv.gz", output));
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception: " + e);
		} catch (Exception ex) {
			LOGGER.error("Exception: " + ex);
		}
	}
	
	public void writeCRFValuesToS3(List<CandidateReport> crReports, String yyyymmdd) {
		try {
			Set<String> tagValueNames= new HashSet<>();
			Set<TagValue> tagValueSet = new HashSet<>(); 
			
			for (CandidateReport cr : crReports) {
				Set<CRF> crfSet = cr.getCrfs();
				if (crfSet != null) {
					for (CRF crf: crfSet) {
						CRF.Key key = crf.getKey();
						CRF.Value value = crf.getValue();
						
						TagValue tagValue = new TagValue(key.getKeyId(), value.getValueName());
						if (!tagValueNames.contains(value.getValueName())) {
							tagValueSet.add(tagValue);
						}
					}	
				}
			}

			Path path = Files.createTempFile("crf_value_names", ".csv");
			File temp = path.toFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
			for (TagValue v : tagValueSet) {
				if (v!=null && !StringUtils.isEmpty(v.getValueName()) && !"null".equals(v.getValueName())) {
					bw.write(v.getValueName() + "," + v.getKeyId());
					bw.write('\n');	
				}
			}
			bw.close();
			temp.deleteOnExit();

			File output = new File(temp.getName() + ".gz");
			GzipCompressor.compressGZIP(temp, output);

			s3client.putObject(new PutObjectRequest("redshift-poc-mettl/tag_value_dump",
					"value_names_" + yyyymmdd + ".csv.gz", output));
		} catch (AmazonServiceException e) {
			LOGGER.error("Exception: " + e);
		} catch (Exception ex) {
			LOGGER.error("Exception: " + ex);
		}
	}
	
	public static byte[] gzip(String str) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out);
			gzipOutputStream.write(str.getBytes("utf-8"));
			gzipOutputStream.close();
			return out.toByteArray();
		} catch (IOException e) {
			LOGGER.error("" + e);
		}
		return str.getBytes();
	}
	
}
