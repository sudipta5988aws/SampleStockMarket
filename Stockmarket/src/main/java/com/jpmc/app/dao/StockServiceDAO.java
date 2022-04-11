package com.jpmc.app.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.repo.StockInfoRepository;

/**
 * DAO class to fetch stock based on sock code
 * @Param : Stock codes are always uppercase alphabetical String
 * @Method : createStocks() - Responsible to create base data when application starts
 * @Method : fetchStock() - fetches a stock based on stock code
 */
@Service
public class StockServiceDAO{
	
	@Autowired
	private StockInfoRepository infoRepository;

	@Transactional
	public List<StockInfo> createStocks(List<StockInfo> stocks) {	
	       return (List<StockInfo>) infoRepository.saveAll(stocks);
	}

	public StockInfo fetchStock(String stockCode) {
		return infoRepository.findByCode(stockCode.toUpperCase());
		
	}

}
