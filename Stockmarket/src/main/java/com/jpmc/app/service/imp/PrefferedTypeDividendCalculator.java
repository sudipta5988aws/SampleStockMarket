package com.jpmc.app.service.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.dataobjects.StockType;
import com.jpmc.app.service.IDividendYieldCalculator;

@Slf4j
@Component
public class PrefferedTypeDividendCalculator implements IDividendYieldCalculator{

	@Override
	public double calculateDividendYield(StockInfo stockInfo, double inputPrice) {
		log.info("Preffered Type Dividend for stockCode = {}",stockInfo.getCode());
		return (stockInfo.getFixedDividend()*stockInfo.getPer_value())/inputPrice;
	}

	@Override
	public StockType getStockType() {
		return StockType.PREFFERED;
	}

}
