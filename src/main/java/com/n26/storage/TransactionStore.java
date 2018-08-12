package com.n26.storage;

import java.util.Vector;

import com.n26.domain.Transaction;

public interface TransactionStore {
	public void storeTransaction(Transaction transaction);
	public Vector<Transaction> getTransactions();
	public void deleteTransactions();
}
