package com.jpmc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.app.controller.PollingController;
import com.jpmc.app.controller.TradeController;
import com.jpmc.app.dataobjects.StockInfo;
import com.jpmc.app.dataobjects.StockTransaction;
import com.jpmc.app.dataobjects.TransactionType;
import com.jpmc.app.exception.ApplicationException;
import com.jpmc.app.model.StockTradeDTO;
import com.jpmc.app.service.imp.BasicTradingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BasicTradingService tradingService;

    @Test
    public void test_trade() throws Exception {
        StockTransaction transaction = getStockTransaction();
        when(tradingService.doTrading(any(StockTradeDTO.class))).thenReturn(transaction);
        mockMvc.perform(post("/exchange/trade").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(transaction)))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.stock_info_id").value(transaction.getStock_info_id()));
        
    }

    private StockTransaction getStockTransaction() {
        StockInfo stock = new StockInfo();
        stock.setId("testStockId");
        StockTradeDTO tradeDTO = new StockTradeDTO();
        tradeDTO.setPricePerUnit(100.9);
        tradeDTO.setTotalUnit(10);
        tradeDTO.setStockCode("TEA");
        tradeDTO.setType(TransactionType.BUY);
        StockTransaction transaction =  new StockTransaction.StockTransactionBuilder().withStockInfo(stock).withTradeInfo(tradeDTO).build();
        return transaction;
    }
}
