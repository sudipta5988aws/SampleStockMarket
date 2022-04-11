package com.jpmc.app.controller;

import com.jpmc.app.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.app.dataobjects.StockTransaction;
import com.jpmc.app.model.StockTradeDTO;
import com.jpmc.app.service.ITradingService;

/**
 * The TradeController saves a trade for a given stock if exists with its time stamp
 * A trade once done can not be updated
 * @Throws ApplicationException in case the stock code does not exist
 * @Throws IllegalArgumentException in case bad input is provided
 * @Author: Sudipta Das
 **/

@Slf4j
@RequestMapping("exchange")
@RestController
public class TradeController {
	
	@Autowired
	ITradingService tradingService;
	
	@RequestMapping(method = RequestMethod.POST , value = "/trade")
	public StockTransaction doTrade(@RequestBody StockTradeDTO tradeInfo) throws ApplicationException {
		log.debug("Trading started : StockTradeDTO :{}",tradeInfo);
		return tradingService.doTrading(tradeInfo);
		
	}
		

}
