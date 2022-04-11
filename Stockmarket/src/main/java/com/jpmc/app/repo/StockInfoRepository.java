package com.jpmc.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jpmc.app.dataobjects.StockInfo;

@Repository
public interface StockInfoRepository extends JpaRepository<StockInfo, String>{

	StockInfo findByCode( String stockCode);

}
