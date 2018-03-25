package com.mettl.poc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
public class MongoConfig {

	@Bean
	public MongoClient mongoClient() {
		MongoClient mongoClient = new MongoClient(
				new MongoClientURI("mongodb://MettlAdmin-light-xyz:F39LKFbsdHeDCHfr@mongo-light-new.xyzpvt/Mettl"));
		return mongoClient;
	}

}
