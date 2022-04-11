package com.jpmc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.app.controller.StockInfoController;
import com.jpmc.app.dataobjects.Polling;
import com.jpmc.app.dataobjects.PollingStatus;
import com.jpmc.app.dataobjects.StockTransaction;
import com.jpmc.app.exception.ApplicationException;
import com.jpmc.app.model.StockTradeDTO;
import com.jpmc.app.processor.AbstractProcessor;
import com.jpmc.app.processor.impl.GeometricMeanProcessor;
import com.jpmc.app.processor.impl.VolumeWeightedStockProcessor;
import com.jpmc.app.service.DataProcessor;
import com.jpmc.app.service.imp.StockCalculatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@RunWith(SpringRunner.class)
@WebMvcTest(StockInfoController.class)
public class StockInfoConntrollerTest {
    @MockBean
    StockCalculatorService stockCalculatorService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("volumeStockProcessor")
    private AbstractProcessor volumeStockProcessor;

    @MockBean
    @Qualifier("geometricMeanProcessor")
    private AbstractProcessor geometricMeanProcessor;

    @MockBean
    private DataProcessor dataProcessor;

    @Test
    public void test_dividend_yield() throws Exception {
        when(stockCalculatorService.calculateDividendYield(any(String.class),any(Double.class))).thenReturn(2.0);
        String returnValue = mockMvc.perform(get("/exchange/TEA/150/yield"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("2.0", returnValue);

    }

    @Test(expected = ApplicationException.class)
    public void test_dividend_yield_withException() throws Exception {
        when(stockCalculatorService.calculateDividendYield(any(String.class),any(Double.class))).thenThrow(new ApplicationException(500,"INTERNAL SERVER_ERROR"));
        String returnValue = mockMvc.perform(get("/exchange/TEA/150/yield"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void test_volume_weighted_price() throws Exception {
        Polling polling = new Polling();
        polling.setStatus(PollingStatus.INPROGRESS);
        polling.setPercentage(10);
        when(dataProcessor.doWork(any(String.class),volumeStockProcessor)).thenReturn(polling );
       mockMvc.perform(post("/exchange/cal/gm"))
                .andExpect(status().isOk())
                        .andExpect(jsonPath("$.status").value("INPROGRESS"));
    }


    @Test
    public void test_pe_ratio() throws Exception {
        when(stockCalculatorService.calculatePERatio(any(String.class),any(Double.class))).thenReturn(2.9);
        String returnValue = mockMvc.perform(get("/exchange/TEA/150/peRatio"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("2.9", returnValue);

    }

    @Test(expected = ApplicationException.class)
    public void test_pe_ratio_with_exception() throws Exception {
        when(stockCalculatorService.calculatePERatio(any(String.class),any(Double.class))).thenThrow(new ApplicationException(400,"BAD REQUEST"));
        String returnValue = mockMvc.perform(get("/exchange/TEA/150/peRatio"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

    }


}
