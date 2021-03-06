package com.jpmc.app.processor.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.jpmc.app.dao.StockServiceDAO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jpmc.app.dataobjects.Polling;
import com.jpmc.app.dataobjects.PollingStatus;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.processor.AbstractProcessor;
import com.jpmc.app.service.polling.IPollingService;

import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@Component("geometricMeanProcessor")
public class GeometricMeanProcessor extends AbstractProcessor {
	
	@Autowired
	private StockServiceDAO stockServiceDAO;
	
	@Autowired
	@Qualifier("mapBasedPoller")
	private IPollingService pollingService;

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Polling preProcess(Polling poll) {
		log.info("Preprocess on the polling request:{}",poll.getId());
		Polling polling = pollingService.fetch(poll.getId());
		if(Objects.nonNull(polling)){
			polling.setStatus(PollingStatus.INPROGRESS);
			polling.setPercentage(INPROGRESS_PROCESS_PERCENTAGE);
			polling.setModifiedDate(LocalDateTime.now());
			return pollingService.save(polling);
		}
		return null;
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Polling process(Polling polling) {
		log.info("Start processing geometricMeanProcessor");
		Polling poll = pollingService.fetch(polling.getId());
		if(Objects.nonNull(poll)){
			List<StockInfo> allStocks = stockServiceDAO.getAllStocks();
			if(CollectionUtils.isNotEmpty(allStocks)){
				Double result = calculate(allStocks);
				log.info("Geometric mean :{}",result);
				poll.setStatus(PollingStatus.PROCESSED);
				poll.setPercentage(SUCCESS_PROCESS_PERCENTAGE);
				poll.setResult(String.valueOf(result));
			}
			else{
				log.error("No Stock data found");
				poll.setStatus(PollingStatus.PROCESSED);
				poll.setPercentage(FAILED_PROCESS_PERCENTAGE);
			}
			polling.setModifiedDate(LocalDateTime.now());
			return pollingService.save(poll);
		}
		return null;

	}

	private Double calculate(List<StockInfo> allStocks) {
		Double product = Double.valueOf(allStocks.stream().map(stock->stock.getCurrentPrice()).reduce(1.0,(item1, item2)->item1*item2));
		return Math.pow(product,1/ allStocks.size());
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Polling postProcess(Polling polling) {
		Polling poll = pollingService.fetch(polling.getId());
		if(Objects.nonNull(poll)){
			if(Integer.valueOf(INPROGRESS_PROCESS_PERCENTAGE).equals(poll.getPercentage())){
				poll.setStatus(PollingStatus.SUCCESS);
				poll.setPercentage(COMPLETE);
			}
			else if(Integer.valueOf(99).equals(poll.getPercentage())){
				poll.setStatus(PollingStatus.CANCELLED);
				poll.setPercentage(COMPLETE);

			}
			poll.setModifiedDate(LocalDateTime.now());
			return pollingService.save(poll);
		}
		return null;
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Polling handleException(Exception e,Polling polling) {
		Polling poll = pollingService.fetch(polling.getId());
		if(Objects.nonNull(poll)){
			log.error("Exception occured while calculating. Ex = ",e);
			poll.setStatus(PollingStatus.FAILED);
			poll.setPercentage(COMPLETE);
			polling.setModifiedDate(LocalDateTime.now());
			return pollingService.save(polling);
		}
		return null;
	}


	
	

}
