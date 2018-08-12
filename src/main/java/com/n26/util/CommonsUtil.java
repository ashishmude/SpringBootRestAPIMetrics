package com.n26.util;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.TimeZone;

import javax.validation.Valid;

import com.n26.domain.Transaction;
import com.n26.exceptions.UnprocessableTransactionException;

public class CommonsUtil {
	
	private final static int timeLimit = 60;
	
	private static Instant convertStringToInstant(String date){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Instant parsedDate = null;
		try{
			parsedDate = Instant.parse(date);
		}catch(DateTimeParseException exception){
			throw new UnprocessableTransactionException();
		}
		return parsedDate;
	}
	
	public static Instant getCurrentInstant(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		return ZonedDateTime.now().toInstant();
	}
	
	public static boolean checkIfDateTimeInRange(Transaction transaction, Instant instant){
		return Duration.between(convertStringToInstant(transaction.getTimestamp()), instant).getSeconds() < timeLimit
				&& Duration.between(convertStringToInstant(transaction.getTimestamp()), instant).getSeconds() >=0;
	}

	public static boolean checkIfTransactionDateInFuture(
			@Valid Transaction transaction, Instant instant) {
		
		return Duration.between(convertStringToInstant(transaction.getTimestamp()), instant).getSeconds() < 0;
	}

	public static boolean checkIfAmountIsParsable(String amount) {
		try{
			new BigDecimal(amount);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public static boolean checkIfDateIsOutsideRange(Transaction transaction,
			Instant instant) {
		return Duration.between(convertStringToInstant(transaction.getTimestamp()), instant).getSeconds() - timeLimit >= 0;
	}
}
