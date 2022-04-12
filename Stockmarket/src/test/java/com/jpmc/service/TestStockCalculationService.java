package com.jpmc.service;

import com.jpmc.app.dao.StockServiceDAO;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.exception.ApplicationException;
import com.jpmc.app.service.imp.StockCalculatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TestStockCalculationService {

    @MockBean
    StockServiceDAO stoDao;

    @Test(expected = ApplicationException.class)
    public void test_pe_calculator_with_exception() throws ApplicationException {
        when(stoDao.fetchStock(any(String.class))).thenReturn(new StockInfo());
        StockCalculatorService service = new StockCalculatorService();
        service.calculatePERatio("ABC",0);
    }

}
