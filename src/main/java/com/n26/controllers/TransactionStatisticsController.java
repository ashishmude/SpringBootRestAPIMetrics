package com.n26.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.domain.TransactionStatistics;
import com.n26.service.StatisticsService;

@RestController
@RequestMapping("/statistics")
public class TransactionStatisticsController {
	
	@Autowired
	private StatisticsService statisticsService;

    @GetMapping(produces = "application/json")
    @ResponseStatus(value=HttpStatus.OK)
    public TransactionStatistics getTransactionStatistics(){
        return statisticsService.getTransactionStatistics();
    }
}
