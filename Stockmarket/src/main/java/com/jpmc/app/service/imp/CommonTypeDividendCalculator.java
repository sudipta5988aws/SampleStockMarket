package com.jpmc.app.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.dataobjects.StockType;
import com.jpmc.app.service.IDividendYieldCalculator;

@Slf4j
@Component
public class CommonTypeDividendCalculator implements IDividendYieldCalculator{

	@Override
	public double calculateDividendYield(StockInfo stockInfo, double inputPrice) {
		log.info("calculateDividendYield for stockCode = {}",stockInfo.getCode());
		return stockInfo.getLastDividend()/inputPrice;
	}

	@Override
	public StockType getStockType() {
		return StockType.COMMON;
	}

}
