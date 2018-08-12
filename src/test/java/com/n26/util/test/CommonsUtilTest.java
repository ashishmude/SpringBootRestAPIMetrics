package com.n26.util.test;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.domain.Transaction;
import com.n26.util.CommonsUtil;

@RunWith(SpringRunner.class)
public class CommonsUtilTest {
	
	@Test
	public void checkIfDateTimeInRangeTest(){
		Instant instant = Instant.now();
		Transaction transaction = new Transaction(); transaction.setAmount("2"); transaction.setTimestamp(instant.toString());
		Assert.assertTrue(CommonsUtil.checkIfDateTimeInRange(transaction, instant));
	}
	
	@Test
	public void checkIfDateTimeInRangeNegativeTest(){
		Instant instant = Instant.now();
		Transaction transaction = new Transaction(); transaction.setAmount("25.00"); transaction.setTimestamp(instant.minusSeconds(60).toString());
		Assert.assertFalse(CommonsUtil.checkIfDateTimeInRange(transaction, instant));
	}
		
	@Test
	public void checkIfTransactionDateInFutureTest(){
		Instant instant = Instant.now();
		Transaction transaction = new Transaction(); transaction.setAmount("49.56"); transaction.setTimestamp(instant.plusSeconds(1).toString());
		Assert.assertTrue(CommonsUtil.checkIfTransactionDateInFuture(transaction, instant));
	}	
	
	@Test
	public void checkIfTransactionDateInFutureNegativeTest(){
		Instant instant = Instant.now();
		Transaction transaction = new Transaction(); transaction.setAmount("49.56"); transaction.setTimestamp(instant.toString());
		Assert.assertFalse(CommonsUtil.checkIfTransactionDateInFuture(transaction, instant));
	}
	
	@Test
	public void checkIfAmountIsParsableTest(){
		Assert.assertTrue(CommonsUtil.checkIfAmountIsParsable("49.56"));
	}	
	
	@Test
	public void checkIfAmountIsParsableNegativeTest(){
		Assert.assertFalse(CommonsUtil.checkIfAmountIsParsable("amount"));
	}
	
	@Test
	public void checkIfDateIsOutsideRangeTest(){
		Instant instant = Instant.now();
		Transaction transaction = new Transaction(); transaction.setAmount("98.46"); transaction.setTimestamp(instant.minusSeconds(60).toString());
		Assert.assertTrue(CommonsUtil.checkIfDateIsOutsideRange(transaction, instant));
	}
	
	@Test
	public void checkIfDateIsOutsideRangeNegativeTest(){
		Instant instant = Instant.now();
		Transaction transaction = new Transaction(); transaction.setAmount("98.46"); transaction.setTimestamp(instant.minusSeconds(59).toString());
		Assert.assertFalse(CommonsUtil.checkIfDateIsOutsideRange(transaction, instant));
	}
	
}
