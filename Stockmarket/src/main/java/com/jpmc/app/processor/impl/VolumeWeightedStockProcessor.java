package com.jpmc.app.processor.impl;

import com.jpmc.app.dao.StockServiceDAO;
import com.jpmc.app.dataobjects.PollingStatus;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.dataobjects.StockTransaction;
import com.jpmc.app.repo.StockTransactionRepo;
import com.jpmc.app.service.polling.IPollingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jpmc.app.dataobjects.Polling;
import com.jpmc.app.processor.AbstractProcessor;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component("volumeStockProcessor")
public class VolumeWeightedStockProcessor extends AbstractProcessor {

	@Autowired
	@Qualifier("mapBasedPoller")
	private IPollingService pollingService;

	@Autowired
	private StockServiceDAO stockServiceDAO;

	@Autowired
	private StockTransactionRepo stockTransactionRepo;

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
		Polling poll = pollingService.fetch(polling.getId());
		if(Objects.nonNull(poll)){
			log.info("Processing volumeStockProcessor :{}",poll.getId());
			String stockCode = poll.getMetadata().split(":")[0];
			long duration  = Long.parseLong(poll.getMetadata().split(":")[1]);
			StockInfo stockInfo = stockServiceDAO.fetchStock(stockCode);
			if(Objects.nonNull(stockInfo)){
				LocalDateTime last5Minute = LocalDateTime.now().minusMinutes(duration);
				List<StockTransaction> transactions = stockTransactionRepo.findByStockIdAndCreatedDuration(stockInfo.getId(),last5Minute);
				Double result = calculate(transactions);
				poll.setStatus(PollingStatus.PROCESSED);
				poll.setPercentage(SUCCESS_PROCESS_PERCENTAGE);
				poll.setResult(String.valueOf(result));
			}
			else{
				log.error("No Stock data found..");
				poll.setStatus(PollingStatus.PROCESSED);
				poll.setPercentage(FAILED_PROCESS_PERCENTAGE);
			}
			poll.setModifiedDate(LocalDateTime.now());
			return pollingService.save(poll);
		}
		return null;
	}

	private Double calculate(List<StockTransaction> transactions) {
		Double intriemResult = transactions.stream().map(currentStock -> currentStock.getTotalStocks()*currentStock.getPricePerUnit()).reduce(0.0, (a, b) -> a+b);
		int totalQty= transactions.stream().mapToInt(currentStock ->currentStock.getTotalStocks()).sum();
		return intriemResult/Double.valueOf(totalQty);
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Polling postProcess(Polling polling) {
		Polling poll = pollingService.fetch(polling.getId());
		if(Objects.nonNull(poll)){
			if(Integer.valueOf(SUCCESS_PROCESS_PERCENTAGE).equals(poll.getPercentage())){
				poll.setStatus(PollingStatus.SUCCESS);
				poll.setPercentage(COMPLETE);
			}
			else if(Integer.valueOf(FAILED_PROCESS_PERCENTAGE).equals(poll.getPercentage())){
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
		log.error("Exception occured while calculating. Ex = ",e);
		Polling poll = pollingService.fetch(polling.getId());
		if(Objects.nonNull(poll)){
			polling.setStatus(PollingStatus.FAILED);
			polling.setPercentage(100);
			polling.setModifiedDate(LocalDateTime.now());
			return pollingService.save(poll);
		}
		return null;
	}

}
