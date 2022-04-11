package com.jpmc.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpmc.app.dataobjects.StockType;

@Component
public class DividendCalculatorFactory {
	
	private Map<StockType,IDividendYieldCalculator> yieldCalculators;
	
	@Autowired
	public DividendCalculatorFactory(Set<IDividendYieldCalculator> calculators) {
		createCalculators(calculators);
	}

	private void createCalculators(Set<IDividendYieldCalculator> calculators) {
		yieldCalculators = new HashMap<>();		
		calculators.forEach(calculator->yieldCalculators.put(calculator.getStockType(), calculator));
	}
	
	public IDividendYieldCalculator findCalculator(StockType stockType) {
	     return yieldCalculators.get(stockType);
	  }


}
