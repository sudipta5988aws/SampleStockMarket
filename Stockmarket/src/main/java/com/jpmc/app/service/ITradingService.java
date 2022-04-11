package com.jpmc.app.service;

import com.jpmc.app.exception.ApplicationException;
import org.springframework.stereotype.Service;

import com.jpmc.app.dataobjects.StockTransaction;
import com.jpmc.app.model.StockTradeDTO;


@Service
public interface ITradingService {
	
	StockTransaction doTrading(StockTradeDTO tradeInfo) throws ApplicationException;

}
