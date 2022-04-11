package com.jpmc.app.util;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmc.app.constants.ApplicationConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ThreadExecutor {
	
	private static ExecutorService executor = null;
	
	private static final int DEFAULT_POOL_SIZE=2;

	@Autowired
	private PropertyLoader propertyLoader;
	
	@PostConstruct
	public void setUPExecutor() {
		int threadSize = DEFAULT_POOL_SIZE;
		try{
			log.warn("no of threads :{}",propertyLoader.getPropertyValue(ApplicationConstants.NO_OF_THREADS));
			int threads = Integer.parseInt(propertyLoader.getPropertyValue(ApplicationConstants.NO_OF_THREADS));
			threadSize = (threads<=DEFAULT_POOL_SIZE && threads>0)?threads:DEFAULT_POOL_SIZE;
		}
		catch(Exception e) {
			log.warn("Unable to parse property:{}","DEFAULT_POOL_SIZE");
		}
				
		executor =  Executors.newFixedThreadPool(threadSize);	
	}
	
	
	public ExecutorService getWorker() {
		if(Objects.isNull(executor)) {
			setUPExecutor();
			return executor;
		}
		return executor;
	}

}
