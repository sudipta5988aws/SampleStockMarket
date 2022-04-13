package com.jpmc.service;

import com.jpmc.app.dao.StockServiceDAO;
import com.jpmc.app.dataobjects.Polling;
import com.jpmc.app.dataobjects.PollingStatus;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.dataobjects.StockType;
import com.jpmc.app.processor.impl.GeometricMeanProcessor;
import com.jpmc.app.service.polling.IPollingService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@ComponentScan(basePackages = {"com.jpmc.*"})
public class TestGeometricMeanProcessor {

    @Mock
    StockServiceDAO stockServiceDAO;

    @Mock
    @Qualifier("mapBasedPoller")
    private IPollingService pollingService;

    private static GeometricMeanProcessor gm = new GeometricMeanProcessor();

    @Test(expected =NullPointerException.class)
    public void calculatePreProcessWithNullPolling(){
        Mockito.mock(StockServiceDAO.class);
        Polling polling = new Polling();
        polling.setPercentage(10);
        polling.setStatus(PollingStatus.INPROGRESS);
        given(pollingService.fetch(any(String.class))).willReturn(null);
        Polling returnPoll = new Polling();
        given(pollingService.save(polling)).willReturn(polling);
        Polling p = gm.preProcess(polling);
        Assert.assertEquals(p.getStatus().name(),PollingStatus.INPROGRESS.name());
    }

    @Test
    public void testGMCalculations(){
        List<StockInfo> stocks = getData();
        Double value  = ReflectionTestUtils.invokeMethod(gm,"calculate",stocks);
        Assert.assertEquals(String.valueOf(1.0), String.valueOf(value));
    }

    private List<StockInfo> getData() {
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

        return stockInfos;
    }
}
