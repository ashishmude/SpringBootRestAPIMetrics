package com.n26.service.impl;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.n26.domain.Transaction;
import com.n26.domain.TransactionStatistics;
import com.n26.service.StatisticsService;
import com.n26.storage.TransactionStore;
import com.n26.util.CommonsUtil;

@Service
@Configurable
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	TransactionStore transationStore;
	
	@Override
	public TransactionStatistics getTransactionStatistics() {
		return calculateStatistics();
	}

	private synchronized TransactionStatistics calculateStatistics() {
		Instant instant = CommonsUtil.getCurrentInstant();
		List<Transaction> eligibleTransaction = transationStore.getTransactions().parallelStream().filter(t -> CommonsUtil.checkIfDateTimeInRange(t, instant)).collect(Collectors.toList());
		
		if(null != eligibleTransaction && !eligibleTransaction.isEmpty()){
			return getStatisticValues(eligibleTransaction);
		}
		return new TransactionStatistics("0.00","0.00","0.00","0.00",0);
	}
	
	 public TransactionStatistics getStatisticValues(List<Transaction> validTransactions) {
		TransactionStatistics statistics = new TransactionStatistics();
		DecimalFormat df = new DecimalFormat("0.00");
	    final List<Double> transactionAmounts = validTransactions.parallelStream().map(t -> Double.valueOf(t.getAmount())).collect(Collectors.toList());
	    
	    statistics.setAvg(String.valueOf(df.format(transactionAmounts.parallelStream().mapToDouble(Double::doubleValue).average().getAsDouble())));
		statistics.setSum(String.valueOf(df.format(transactionAmounts.parallelStream().mapToDouble(Double::doubleValue).sum())));
		statistics.setMax(String.valueOf(df.format(transactionAmounts.parallelStream().mapToDouble(Double::doubleValue).max().getAsDouble())));
		statistics.setMin(String.valueOf(df.format(transactionAmounts.parallelStream().mapToDouble(Double::doubleValue).min().getAsDouble())));
		statistics.setCount(validTransactions.size());
		
		return statistics;
	  }


}
