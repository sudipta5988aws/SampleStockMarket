package com.jpmc.app.service;

import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.dataobjects.StockType;

public interface IDividendYieldCalculator {
	
	double calculateDividendYield(StockInfo stockInfo, double inputPrice);
	
	StockType getStockType();

}
