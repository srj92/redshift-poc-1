package com.mettl.poc.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mettl.poc.DataMigrationPocApplicationTests;

public class MigrationServiceTests extends DataMigrationPocApplicationTests {

	@Autowired
	private MigrationService migrationService;
	
	@Test
	public void testMigrateService() {	
		migrationService.migrate("2018", "03", "20");
	}
}
