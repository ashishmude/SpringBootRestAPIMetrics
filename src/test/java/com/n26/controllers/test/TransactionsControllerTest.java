package com.n26.controllers.test;

import java.time.Instant;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.n26.controllers.TransactionsController;
import com.n26.storage.TransactionStore;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsController.class)
public class TransactionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TransactionStore transationStore;

	@Test
	public void registerTransactionCreatedTest() throws Exception {
		String timestamp = Instant.now().toString();
		String transaction = "{\"amount\":\"5\",\"timestamp\":\""+ timestamp +"\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/transactions").content(transaction).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void registerTransactionNoContentTest() throws Exception {
		String timestamp = Instant.now().minusSeconds(60).toString();
		String transaction = "{\"amount\":\"5\",\"timestamp\":\""+ timestamp +"\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/transactions").content(transaction).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void registerTransactionBadRequestTest() throws Exception {
		String timestamp = Instant.now().toString();
		String transaction = "{\"amount\":\"5\",\"timestamp\":\""+ timestamp +"\"";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/transactions").content(transaction).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
	}
	
	
	@Test
	public void registerTransactionUnsupportedInputTest() throws Exception {
		String transaction = "{\"amount\":\"5\",\"timestamp\":\""+ new Date() +"\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/transactions").content(transaction).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());
	}
	

	@Test
	public void deleteTransactionsTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
				"/transactions").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		Assert.assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}

    
}