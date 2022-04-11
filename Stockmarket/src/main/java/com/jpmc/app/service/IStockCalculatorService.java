package com.jpmc.app.service;


import com.jpmc.app.exception.ApplicationException;
import org.springframework.stereotype.Service;

/**
 * StockIndicatorCalculator  interface
 */
@Service
public interface IStockCalculatorService {
	
	double calculateDividendYield(String stockCode, double inputPrice) throws ApplicationException;
	
	double calculatePERatio(String stockCode, double inputPrice) throws ApplicationException;

}
