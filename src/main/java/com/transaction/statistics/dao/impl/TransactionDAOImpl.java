package com.transaction.statistics.dao.impl;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.transaction.statistics.dao.TransactionDAO;
import com.transaction.statistics.dto.TransactionStatsDTO;
import com.transaction.statistics.util.DateTimeUtil;

@Component
public class TransactionDAOImpl implements TransactionDAO {

	private static Map<Long, Double> TRANSACTIONS_DATA = new TreeMap<Long, Double>();

	@Override
	public void save(double transactionAmount, long timestamp) {
		TRANSACTIONS_DATA.put(timestamp, transactionAmount);
	}

	@Override
	public TransactionStatsDTO findTransactionsStatsBeforeOneMin() {
		Long timestampBefore1Min = DateTimeUtil.getTimestampBefore60SecondsInUTC();

		TransactionStatsDTO transStatDTO = new TransactionStatsDTO();

		if (TRANSACTIONS_DATA.isEmpty()) return transStatDTO;
		
		TRANSACTIONS_DATA.forEach((k, v) -> {
			if (k.longValue() >= timestampBefore1Min) {
				transStatDTO.setSum(transStatDTO.getSum() + v.doubleValue());
				transStatDTO.setCount(transStatDTO.getCount() + 1);
				if (transStatDTO.getMin() == 0 || v.doubleValue() < transStatDTO.getMin()) {
					transStatDTO.setMin(v.doubleValue());
				} else if (v.doubleValue() > transStatDTO.getMax()) {
					transStatDTO.setMax(v.doubleValue());
				}
			}
		});
		
		if (transStatDTO.getSum() != 0 && transStatDTO.getCount() != 0) {
			transStatDTO.setAvg(transStatDTO.getSum() / transStatDTO.getCount());
		}
		
		return transStatDTO;
	}
}
