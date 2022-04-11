package com.jpmc.service;

import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.service.imp.CommonTypeDividendCalculator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestCommonTypeDividendCalculator {

    @Test
    public void test_dividend(){
        CommonTypeDividendCalculator commonTypeDividendCalculator = new CommonTypeDividendCalculator();
        StockInfo info = new StockInfo();
        info.setLastDividend(5.0);
        info.setPer_value(100);
        double result =  commonTypeDividendCalculator.calculateDividendYield(info,100);
        Assert.assertEquals("Mismatch in result","0.05",String.valueOf(result));
    }
}
