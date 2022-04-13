package com.jpmc;

import com.jpmc.app.controller.PollingController;
import com.jpmc.app.dataobjects.Polling;
import com.jpmc.app.dataobjects.PollingStatus;
import com.jpmc.app.exception.ApplicationException;
import com.jpmc.app.repo.ApplicationPropertyRepo;
import com.jpmc.app.repo.PollingRepo;
import com.jpmc.app.repo.StockInfoRepository;
import com.jpmc.app.repo.StockTransactionRepo;
import com.jpmc.app.service.polling.impl.MapPollingService;
import com.jpmc.app.service.polling.impl.PollingService;
import org.apache.tomcat.jni.Poll;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PollingController.class)
@ComponentScan(basePackages = "com.jpmc")
public class PollingControllerTest {

    @MockBean
    MapPollingService pollingService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PollingRepo pollingRepo;

    @MockBean
    StockInfoRepository stockInfoRepository;

    @MockBean
    StockTransactionRepo stockTransactionRepo;

    @MockBean
    ApplicationPropertyRepo applicationPropertyRepo;

    @Test
    public void fetch_polling_data_test() throws Exception {
        Polling polling = getPolling();
        when(pollingService.fetch(any(String.class))).thenReturn(polling);
        mockMvc.perform(get("/poller/testID/poll")).andExpect(status().isOk()).
                andExpect(jsonPath("$.id").value(polling.getId()));
    }


    private static Polling getPolling() {
        Polling polling = new Polling();
        polling.setId("testID");
        polling.setResult("TestResult");
        polling.setPercentage(100);
        polling.setStatus(PollingStatus.SUCCESS);
        polling.setMetadata("TestMetadata");
        return polling;
    }
}
