package com.mettl.poc.config.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSConfig {

	@Value("${access-key}")
	private String accessKey;

	@Value("${secret-key}")
	private String secretKey;

	@Value("${region}")
	private String region;
	
	@Bean
	public String accessKey() {
		return accessKey;
	}

	@Bean
	public String secretKey() {
		return secretKey;
	}
	
	@Bean
	public AWSCredentials awsCredentials() {
		AWSCredentials credentials = null;
		try {
			credentials = new BasicAWSCredentials(accessKey(), secretKey());
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential properties. ", e);
		}
		return credentials;
	}
	
	@Bean
	public AmazonS3 s3client() {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials())).build();
		return s3Client;
	}

}