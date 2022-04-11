package com.jpmc.service;

import com.jpmc.app.dao.StockServiceDAO;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.dataobjects.StockType;
import com.jpmc.app.repo.PollingRepo;
import com.jpmc.app.repo.StockInfoRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TestStockService {

    @MockBean
    StockInfoRepository stockInfoRepository;

    @Autowired
    StockServiceDAO dao;

    @Test
    public void test_fetch_stockinfo(){
        StockInfo data = new StockInfo();
        data.setCurrentPrice(100.0);
        data.setStockType(StockType.COMMON);
        data.setFixedDividend(3);
        data.setId("ABC");
        data.setPer_value(100);
        data.setLastDividend(30.0);

        when(stockInfoRepository.getById(any(String.class))).thenReturn(data);
        StockInfo result = dao.fetchStock("ABC");
        Assert.assertEquals("Data are not equal","ABC",result.getId());
    }
}
