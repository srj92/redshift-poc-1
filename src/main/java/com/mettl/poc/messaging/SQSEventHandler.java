package com.mettl.poc.messaging;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mettl.poc.model.CandidateReport;
import com.mettl.poc.service.CandidateReportService;

@Component
public class SQSEventHandler {

	private static final String INPUT_QUEUE = "redshift_update_queue";
	private static final Logger LOG = LoggerFactory.getLogger(SQSEventHandler.class);

	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private CandidateReportService candidateReportService;
	
	@JmsListener(destination = INPUT_QUEUE)
	public void onMessage(String updateRequestJson) throws JsonParseException, JsonMappingException, IOException {
		LOG.info("Input message: " + updateRequestJson);
		CandidateReport cr = mapper.readValue(updateRequestJson, CandidateReport.class);
		candidateReportService.updateCandidateReport(cr);
	}

}