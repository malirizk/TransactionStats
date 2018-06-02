package com.transaction.statistics.dto;

import lombok.Data;

@Data
public class TransactionStatsDTO {

	private double sum, avg, max, min;
	
	private long count;
}
