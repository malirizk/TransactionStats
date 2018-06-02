package com.transaction.statistics.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.statistics.dao.TransactionDAO;
import com.transaction.statistics.dto.TransactionStatsDTO;
import com.transaction.statistics.service.TransactionService;

import lombok.Setter;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Setter
	@Autowired
	private TransactionDAO transactionDAO;

	/**
	 * This method used to save new transaction
	 * 
	 * @param transactionAmount
	 *            Amount of transaction
	 * 
	 * @param timestamp
	 *            time in epoch in millis in UTC time zone
	 * 
	 * @exception IllegalArgumentException
	 * 
	 */
	@Override
	public void saveTransaction(double transactionAmount, long timestamp) throws IllegalArgumentException {
		LOGGER.trace("New transaction received with amount : {} , and timestamp : {} .",
				new Object[] { transactionAmount, timestamp });

		if (transactionAmount <= 0) {
			LOGGER.error("Invalid Transaction Amount");
			throw new IllegalArgumentException("Invalid Transaction Amount");
		}
		if (timestamp <= 0) {
			LOGGER.error("Invalid Transaction timestamp");
			throw new IllegalArgumentException("Invalid Transaction timestamp");
		}

		LOGGER.info("Saving new Transaction with amount: {} and timestamp: {} .",
				new Object[] { transactionAmount, timestamp });
		transactionDAO.save(transactionAmount, timestamp);

	}

	/**
	 * This method responsible to return statistics for executed transaction in the
	 * last 60 seconds
	 * 
	 * @param TransactionStatsDTO
	 * 
	 */
	@Override
	public TransactionStatsDTO getTransactionStatsBeforeOneMin() {
		return transactionDAO.findTransactionsStatsBeforeOneMin();
	}
}
