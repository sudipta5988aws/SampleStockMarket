package com.jpmc.service;

import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.service.imp.PrefferedTypeDividendCalculator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestPreferredDividendCalculator {

    @Test
    public void test_preferred_stock(){
        StockInfo info = new StockInfo();
        info.setLastDividend(5.0);
        info.setPer_value(100);
        info.setFixedDividend(3);
        PrefferedTypeDividendCalculator calculator = new PrefferedTypeDividendCalculator();
        double result = calculator.calculateDividendYield(info,100);
        Assert.assertEquals("Mismatch in result","3.0",String.valueOf(result));
    }
}
