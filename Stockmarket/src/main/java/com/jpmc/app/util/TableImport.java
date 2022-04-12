package com.jpmc.app.util;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.jpmc.app.constants.ApplicationConstants;
import com.jpmc.app.dao.StockServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpmc.app.dataobjects.ApplicationProperty;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.dataobjects.StockType;
import com.jpmc.app.repo.ApplicationPropertyRepo;

@Component
public class TableImport {
	
	@Autowired
	private StockServiceDAO stockServiceDAO;
	
	@Autowired
	private ApplicationPropertyRepo applicationPropertyRepo;
	
	@Transactional
	public void fillTableData() {
		
		List<StockInfo> stockInfos = new ArrayList();
		
		StockInfo info = new StockInfo();
		info.setCode("TEA");
		info.setFixedDividend(null);
		info.setLastDividend(0.0);
		info.setPer_value(100);
		info.setStockType(StockType.COMMON);
		info.setCurrentPrice(1000.0);
		stockInfos.add(info);
		
		
		info = new StockInfo();
		info.setCode("POP");
		info.setFixedDividend(null);
		info.setLastDividend(8.0);
		info.setPer_value(100);
		info.setStockType(StockType.COMMON);
		info.setCurrentPrice(987.32);
		stockInfos.add(info);
		
		
		info = new StockInfo();
		info.setCode("ALE");
		info.setFixedDividend(null);
		info.setLastDividend(23.0);
		info.setPer_value(100);
		info.setCurrentPrice(321.45);
		info.setStockType(StockType.COMMON);
		stockInfos.add(info);
		
		info = new StockInfo();
		info.setCode("GIN");
		info.setFixedDividend(2);
		info.setLastDividend(8.0);
		info.setPer_value(100);
		info.setCurrentPrice(99.65);
		info.setStockType(StockType.PREFFERED);
		stockInfos.add(info);
		stockServiceDAO.createStocks(stockInfos);
		
		
		ApplicationProperty property = new ApplicationProperty();
		property.setKey(ApplicationConstants.NO_OF_THREADS);
		property.setValue("1");

		applicationPropertyRepo.save(property);
		
	}
	

}
