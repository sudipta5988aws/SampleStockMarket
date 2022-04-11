package com.jpmc.app.controller;

import com.jpmc.app.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jpmc.app.constants.ApplicationConstants;
import com.jpmc.app.dataobjects.Polling;
import com.jpmc.app.processor.AbstractProcessor;
import com.jpmc.app.service.DataProcessor;
import com.jpmc.app.service.imp.StockCalculatorService;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @What : StockInfoController responsible for calculating various stock indicators
 * All controllers based on specific stock code
 * Async jobs are Post request
 *
 */

@Slf4j
@RequestMapping("exchange")
@RestController
public class StockInfoController {
	
	@Autowired
	private StockCalculatorService stockService;
	
	@Autowired
	@Qualifier("volumeStockProcessor")
	private AbstractProcessor volumeStockProcessor;
	
	@Autowired
	@Qualifier("geometricMeanProcessor")
	private AbstractProcessor geometricMeanProcessor;
	
	
	@Autowired
	private DataProcessor dataProcessor;

	/**
	 * @param code - valid stock code
	 * @param price - valid input price
	 * @return dividend yield in double
	 */
	@RequestMapping(method = RequestMethod.GET , value = "/{code}/{price}/yield")
	public double getDividendYield(@PathVariable(value="code") String code, @PathVariable(value="price") Double price) throws ApplicationException {
		log.info("calculation dividend yield for stack code:{}",code);
		return stockService.calculateDividendYield(code,price);
	}


	/**
	 * @param code
	 * @param price
	 * @return PE Ratio for a given stock and input price
	 */
	@RequestMapping(method = RequestMethod.GET , value = "/{code}/{price}/peRatio")
	public double getPERatio(@PathVariable(value="code") String code, @PathVariable(value="price") Double price) throws ApplicationException {
		return stockService.calculatePERatio(code,price);		
	}

	/**
	 * @param code
	 * @param time
	 * @return Polling data which can be used to track the status of submitted task
	 * @How :  Submits an async task which calculates the Volume Weighted Stock Price based on given time for a stocks for which trades happened
	 */
	@RequestMapping(method = RequestMethod.POST , value = "/{code}/{time}/vwsp")
	public Polling getVolumeWeightedStockPrice(@PathVariable(value="code") String code, @PathVariable(value="time") Integer time) {
		log.info("Stated calculating VolumeWeightedStockPrice for stock code:{} and time:{}",code,time);
		return dataProcessor.doWork(code+":"+String.valueOf(time), volumeStockProcessor);
	}

	/**
	 * @return Polling data which can be used to track the status of submitted task
	 * @How: Submits an async task to calculate geometric mean of all stock indexes at their current price
	 */
	@RequestMapping(method = RequestMethod.POST , value = "/cal/gm")
	public Polling getGeometricMean() {
		log.info("Stated calculating GEOMETRIC_MEAN..");
		return dataProcessor.doWork(ApplicationConstants.GEOMETRIC_MEAN, geometricMeanProcessor);
	}
	
	
		
	

}
