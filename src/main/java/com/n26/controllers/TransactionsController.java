package com.n26.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.domain.Transaction;
import com.n26.exceptions.InvalidTransactionInputException;
import com.n26.exceptions.UnprocessableTransactionException;
import com.n26.storage.TransactionStore;
import com.n26.util.CommonsUtil;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
	
	@Autowired
	TransactionStore transationStore;

    @ResponseStatus(value=HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void registerTransaction(@RequestBody final Transaction transaction) {
    	Instant instant = CommonsUtil.getCurrentInstant();
    	
    	if(!CommonsUtil.checkIfAmountIsParsable(transaction.getAmount()) || CommonsUtil.checkIfTransactionDateInFuture(transaction, instant)){
			throw new UnprocessableTransactionException();
		}else if(CommonsUtil.checkIfDateIsOutsideRange(transaction, instant)){
			throw new InvalidTransactionInputException();
		}
    	transationStore.storeTransaction(transaction);
    }
    
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteTransactions(@RequestBody(required=false) final Transaction transaction) {
    	transationStore.deleteTransactions();
    }

}
