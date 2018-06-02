package com.transaction.statistics.dao;

import com.transaction.statistics.dto.TransactionStatsDTO;

public interface TransactionDAO {

	void save(double transactionAmount, long timestamp);

	TransactionStatsDTO findTransactionsStatsBeforeOneMin();

}
