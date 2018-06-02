package com.transaction.statistics.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * This class represent the request sent to endpoint POST /transactions
 * 
 * @author Mohamed
 *
 */

@Data
public class TransactionRq {

	// Transaction amount
	@NotNull
	private Double amount;

	// Transaction time in epoch in millis in UTC time zone
	@NotNull
	private Long timestamp;
}
