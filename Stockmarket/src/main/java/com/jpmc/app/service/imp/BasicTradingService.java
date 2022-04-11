package com.jpmc.app.service.imp;

import java.util.Objects;

import com.jpmc.app.annotation.TimeLoggable;
import com.jpmc.app.exception.ApplicationException;
import com.jpmc.app.exception.ApplicationExceptionConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.dataobjects.StockTransaction;
import com.jpmc.app.model.StockTradeDTO;
import com.jpmc.app.repo.StockInfoRepository;
import com.jpmc.app.repo.StockTransactionRepo;
import com.jpmc.app.service.ITradingService;

/**
 * Implementation class of ITradingService
 * @Output : Saves a trading into in memory DB
 * @Objective: Find the stock info whether exists inside DB or not
 *             If Exists record the trade after validating input
 *             Else @Throws Exception
 *
 * @Author: Sudipta Das
 */

@Slf4j
@Service
public class BasicTradingService implements ITradingService{
	
	@Autowired
	private StockInfoRepository stockInfoRepository;
	
	@Autowired
	private StockTransactionRepo stockTransactionRepo;

	/**
	 *
	 * @param tradeInfo
	 * @return StockTransaction which have happened
	 * @throws ApplicationException
	 */
	@Override
	@TimeLoggable
	public StockTransaction doTrading(StockTradeDTO tradeInfo) throws ApplicationException {
		if(isValidRequest(tradeInfo)) {
		   	StockInfo stock = stockInfoRepository.findByCode(tradeInfo.getStockCode().toUpperCase());
		   	if(Objects.nonNull(stock)) {
		   		   StockTransaction tradeData = new StockTransaction.StockTransactionBuilder().withStockInfo(stock).withTradeInfo(tradeInfo).build();
				   log.info("Save trading for stock Code :{}",stock.getCode());
		   		   return stockTransactionRepo.save(tradeData);
		   	}
		   	throw new ApplicationException(ApplicationExceptionConstant.NOT_FOUND,ApplicationExceptionConstant.DATA_NOT_EXISTS);
		}
		throw new ApplicationException(ApplicationExceptionConstant.BAD_DATA,ApplicationExceptionConstant.BAD_INPUT);
	}


	/**
	 *
	 * @param tradeInfo
	 * @return true if valid request else false
	 */
	private boolean isValidRequest(StockTradeDTO tradeInfo) {
		if((tradeInfo.getTotalUnit()<1) || (tradeInfo.getPricePerUnit()<1)) {
			log.warn("price or quantity can not be less than 1");
			return false;
		}
		return true;
	}

}
