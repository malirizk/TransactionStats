package com.transaction.statistics.service;

import com.transaction.statistics.dto.TransactionStatsDTO;

public interface TransactionService {

	void saveTransaction(double transactionAmount, long timestamp);

	TransactionStatsDTO getTransactionStatsBeforeOneMin();

}
