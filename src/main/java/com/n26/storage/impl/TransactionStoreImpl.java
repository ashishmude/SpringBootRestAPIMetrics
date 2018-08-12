package com.n26.storage.impl;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.n26.domain.Transaction;
import com.n26.storage.TransactionStore;

@Service
@Configurable
public class TransactionStoreImpl implements TransactionStore {
	private static volatile Vector<Transaction> transactionStore;
	
	public TransactionStoreImpl() {
		if(null == transactionStore){
			transactionStore = new Vector<Transaction>();
		}
	}
	
	@Override
	public synchronized void storeTransaction(Transaction transaction){
		transactionStore.add(transaction);
	}

	@Override
	public Vector<Transaction> getTransactions() {
		return transactionStore;
	}
	
	@Override
	public void deleteTransactions() {
		transactionStore.removeAllElements();
	}

	

}
