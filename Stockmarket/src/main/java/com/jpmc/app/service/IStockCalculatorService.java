package com.jpmc.app.service;


import org.springframework.stereotype.Service;

/**
 * StockIndicatorCalculator  interface
 */
@Service
public interface IStockCalculatorService {
	
	double calculateDividendYield(String stockCode, double inputPrice);
	
	double calculatePERatio(String stockCode, double inputPrice);

}
