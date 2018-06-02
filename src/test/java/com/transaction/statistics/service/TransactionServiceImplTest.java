package com.transaction.statistics.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;

import com.transaction.statistics.dao.TransactionDAO;
import com.transaction.statistics.dao.impl.TransactionDAOImpl;
import com.transaction.statistics.dto.TransactionStatsDTO;
import com.transaction.statistics.service.impl.TransactionServiceImpl;

public class TransactionServiceImplTest {

	TransactionServiceImpl transactionService = new TransactionServiceImpl();	
	
	@Before
	public void init() {
		TransactionDAO transactionDAO = new TransactionDAOImpl();
		transactionService.setTransactionDAO(transactionDAO);
	}
	
	@Test
	public void testSaveTransaction() {
		OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
		long epochMillis = utc.toEpochSecond() * 1000;
		
		transactionService.saveTransaction(20.50, epochMillis);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSaveTransaction_IllegalArgumentException() {
		transactionService.saveTransaction(0, 0);
	}
	
	@Test
	public void testGetTransactionStatsBeforeOneMin_WithOneTXNBefore1Min() {
		OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
		transactionService.saveTransaction(20.50, utc.toEpochSecond() * 1000);
		transactionService.saveTransaction(15, utc.minusSeconds(10).toEpochSecond() * 1000);
		transactionService.saveTransaction(10, utc.minusMinutes(2).toEpochSecond() * 1000);
		
		TransactionStatsDTO transactionStatsDTO = transactionService.getTransactionStatsBeforeOneMin(); 
		assertNotNull(transactionStatsDTO);
		assertEquals(2, transactionStatsDTO.getCount());
		assertEquals(35.50d, transactionStatsDTO.getSum(), 0);
	}
}
