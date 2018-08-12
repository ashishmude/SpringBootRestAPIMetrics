package com.n26.service.test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionStatistics;
import com.n26.service.StatisticsService;
import com.n26.service.impl.StatisticsServiceImpl;
import com.n26.storage.TransactionStore;
import com.n26.storage.impl.TransactionStoreImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class StatisticsServiceTest {
	@Configuration
	static class StatisticsServiceTestTestContextConfiguration {
		@Bean
		public StatisticsService statisticsService() {
			return new StatisticsServiceImpl();
		}
	}
	
	@Autowired
	StatisticsService statisticsService;
	
	@MockBean
	TransactionStore transationStore;

	private Vector<Transaction> setupTransactions(){
		Vector<Transaction> transactionList = new Vector<Transaction>();
		Instant instant = Instant.now();
		Transaction tr = new Transaction(); tr.setAmount("2"); tr.setTimestamp(instant.toString());
		Transaction tr1 = new Transaction(); tr1.setAmount("4.557"); tr1.setTimestamp(instant.minusSeconds(20).toString());
		Transaction tr2 = new Transaction(); tr2.setAmount("1"); tr2.setTimestamp(instant.minusSeconds(48).toString());
		Transaction tr3 = new Transaction(); tr3.setAmount("5"); tr3.setTimestamp(instant.minusSeconds(60).toString());
		transactionList.add(tr);transactionList.add(tr1);transactionList.add(tr2);transactionList.add(tr3);
		return transactionList;
	}
	
	@Test
	public void getTransactionStatisticsTest() throws JsonProcessingException{
		Mockito.when(transationStore.getTransactions()).thenReturn(setupTransactions());
		TransactionStatistics transactionStatistics = statisticsService.getTransactionStatistics();
		Assert.assertNotNull(transactionStatistics);
		Assert.assertEquals("{\"sum\":\"7.56\",\"avg\":\"2.52\",\"max\":\"4.56\",\"min\":\"1.00\",\"count\":3}", new ObjectMapper().writeValueAsString(transactionStatistics));
	}
	
	@Test
	public void getTransactionStatisticsAfterDeleteTest() throws JsonProcessingException{
		Mockito.when(transationStore.getTransactions()).thenReturn(new Vector<Transaction>());
		TransactionStatistics transactionStatistics = statisticsService.getTransactionStatistics();
		Assert.assertNotNull(transactionStatistics);
		Assert.assertEquals("{\"sum\":\"0.00\",\"avg\":\"0.00\",\"max\":\"0.00\",\"min\":\"0.00\",\"count\":0}", new ObjectMapper().writeValueAsString(transactionStatistics));
	}
	
}
