package com.jpmc.app.service.imp;

import java.util.Objects;

import com.jpmc.app.annotation.TimeLoggable;
import com.jpmc.app.exception.ApplicationException;
import com.jpmc.app.exception.ApplicationExceptionConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmc.app.dao.StockServiceDAO;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.service.factory.DividendCalculatorFactory;
import com.jpmc.app.service.IDividendYieldCalculator;
import com.jpmc.app.service.IStockCalculatorService;

@Service
@Slf4j
public class StockCalculatorService implements IStockCalculatorService{
	
	@Autowired
	private StockServiceDAO stoDao;
	
	@Autowired
	private DividendCalculatorFactory calculatorFactory;

	@Override
	@TimeLoggable
	public double calculateDividendYield(String stockCode, double inputPrice) throws ApplicationException {
		StockInfo stkInfo = stoDao.fetchStock(stockCode);
		validate(inputPrice);
		if(Objects.nonNull(stkInfo)) {
			log.info("Calculating dividend yield for stock code :{}",stkInfo.getCode());
			IDividendYieldCalculator dividendYieldCalculator = calculatorFactory.findCalculator(stkInfo.getStockType());
			return dividendYieldCalculator.calculateDividendYield(stkInfo, inputPrice);
		}
		log.warn("Stock with stock code {} not found!",stockCode);
		return -1.0;
	}

	private void validate(double inputPrice) throws ApplicationException {
		if(inputPrice<1.0){
			throw new ApplicationException(ApplicationExceptionConstant.BAD_DATA,ApplicationExceptionConstant.BAD_INPUT);
		}
	}

	@Override
	public double calculatePERatio(String stockCode, double inputPrice) throws ApplicationException {
		log.info("Calculating PE Ratio for stock code:{}",stockCode);
		validate(inputPrice);
		StockInfo stkInfo = stoDao.fetchStock(stockCode);
		if(Objects.nonNull(stkInfo)) {
			try {
				return inputPrice/stkInfo.getLastDividend();
			}catch (Exception e) {
				log.error("Exception occured while calculating PE Ratio -",e);
				return 0;
			}
		}
		return 0;
	}


}
