package com.n26.controllers.test;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.n26.controllers.TransactionStatisticsController;
import com.n26.domain.TransactionStatistics;
import com.n26.service.StatisticsService;
import com.n26.storage.TransactionStore;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionStatisticsController.class)
public class TransactionStatisticsControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
	private StatisticsService statisticsService;
    
    @MockBean
    TransactionStore transationStore;
    
    @Test
	public void getTransactionStatisticsTest() throws Exception {
    	Mockito.when(statisticsService.getTransactionStatistics()).thenReturn(new TransactionStatistics("0.00","0.00","0.00","0.00",0));
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/statistics").contentType(MediaType.APPLICATION_JSON);
    	MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    	Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    	Assert.assertEquals("{\"sum\":\"0.00\",\"avg\":\"0.00\",\"max\":\"0.00\",\"min\":\"0.00\",\"count\":0}", result.getResponse().getContentAsString());
    }
	
}
