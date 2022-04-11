package com.jpmc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.app.controller.StockInfoController;
import com.jpmc.app.dataobjects.StockTransaction;
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

    @Test
    public void test_pe_ratio() throws Exception {
        when(stockCalculatorService.calculatePERatio(any(String.class),any(Double.class))).thenReturn(2.9);
        String returnValue = mockMvc.perform(get("/exchange/TEA/150/peRatio"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("2.9", returnValue);

    }
}
