package com.jpmc.app.service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.jpmc.app.dataobjects.Polling;
import com.jpmc.app.dataobjects.PollingStatus;
import com.jpmc.app.processor.AbstractProcessor;
import com.jpmc.app.service.polling.IPollingService;
import com.jpmc.app.util.ThreadExecutor;

@Service
public class DataProcessor {
	
	@Autowired
	private ThreadExecutor executor;
	
	@Autowired
	@Qualifier("mapBasedPoller")
	private IPollingService pollingService;
	
	@Transactional
	public Polling doWork(String metadata, AbstractProcessor processor) {
		
		Polling polling = new Polling();
		polling.setId(UUID.randomUUID().toString());
		polling.setMetadata(metadata);
		polling.setPercentage(AbstractProcessor.STARTED);
		polling.setStatus(PollingStatus.STARTED);
		polling.setCreationDate(LocalDateTime.now());
		Polling savedData = pollingService.save(polling);
		CompletableFuture.runAsync(()->processor.doWork(savedData),executor.getWorker());
		return savedData;
		
	} 

}
