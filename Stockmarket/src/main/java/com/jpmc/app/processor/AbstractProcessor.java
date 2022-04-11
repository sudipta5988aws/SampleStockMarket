package com.jpmc.app.processor;

import com.jpmc.app.annotation.TimeLoggable;
import com.jpmc.app.service.polling.IPollingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpmc.app.dataobjects.Polling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;

@Slf4j
@Component
public abstract class AbstractProcessor {

	@Autowired
	private IPollingService pollingService;

	public static final int INPROGRESS_PROCESS_PERCENTAGE = 20;
	public static final int SUCCESS_PROCESS_PERCENTAGE = 80;
	public static final int FAILED_PROCESS_PERCENTAGE = 99;
	public static final int COMPLETE = 100;
	public static final int STARTED = 10;
	
	public abstract Polling preProcess(Polling poll);
	
	public abstract Polling process(Polling poll);
	
	public abstract Polling postProcess(Polling poll);
	
	public abstract Polling handleException(Exception e,Polling polling);

	@TimeLoggable
	@Transactional
	public void doWork(Polling poll) {
		Polling resultPoll = null;
		try {
			log.info("Worker started. Poll Id :{}",poll.getId());
			resultPoll = preProcess(poll);
			resultPoll = process(resultPoll);
			resultPoll = postProcess(resultPoll);
		}catch(Exception e) {
			resultPoll = handleException(e,resultPoll);
		}finally {
			pollingService.save(resultPoll);
		}
	}
	
}
