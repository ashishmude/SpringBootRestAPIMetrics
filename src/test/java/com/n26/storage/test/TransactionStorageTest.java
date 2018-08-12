package com.n26.storage.test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.n26.domain.Transaction;
import com.n26.storage.TransactionStore;
import com.n26.storage.impl.TransactionStoreImpl;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class TransactionStorageTest {
	@Configuration
	static class TransactionStorageTestContextConfiguration {
		@Bean
		public TransactionStore transactionStorageService() {
			return new TransactionStoreImpl();
		}
	}
	
	@Autowired
	TransactionStore transactionStorage;

	@Before
	public void storeTransactions(){
		List<Transaction> transactionList = new ArrayList<Transaction>();
		Instant instant = Instant.now();
		Transaction tr = new Transaction(); tr.setAmount("2"); tr.setTimestamp(instant.toString());
		Transaction tr1 = new Transaction(); tr1.setAmount("4.557"); tr1.setTimestamp(instant.minusSeconds(20).toString());
		Transaction tr2 = new Transaction(); tr2.setAmount("1"); tr2.setTimestamp(instant.minusSeconds(48).toString());
		Transaction tr3 = new Transaction(); tr3.setAmount("5"); tr3.setTimestamp(instant.minusSeconds(60).toString());
		transactionList.add(tr);transactionList.add(tr1);transactionList.add(tr2);transactionList.add(tr3);
		
		transactionList.forEach(t -> transactionStorage.storeTransaction(t));
	}
	
	@Test
	public void getTransactionsTest() throws JsonProcessingException{
		Vector<Transaction> transactions = transactionStorage.getTransactions();
		Assert.assertNotNull(transactions);
		Assert.assertEquals(4,transactions.size());
	}
	
	@Test
	public void getTransactionsAfterDeleteTest() throws JsonProcessingException{
		transactionStorage.deleteTransactions();
		Vector<Transaction> transactions = transactionStorage.getTransactions();
		Assert.assertNotNull(transactions);
		Assert.assertTrue(transactions.isEmpty());
	}
	
}
