package com.jpmc.app.service.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jpmc.app.service.IDividendYieldCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpmc.app.dataobjects.StockType;

@Component
public class DividendCalculatorFactory {
	
	private Map<StockType, IDividendYieldCalculator> yieldCalculators;
	
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
