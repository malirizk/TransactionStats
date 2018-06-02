package com.transaction.statistics.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.statistics.dto.TransactionRq;
import com.transaction.statistics.dto.TransactionStatsDTO;
import com.transaction.statistics.service.TransactionService;
import com.transaction.statistics.util.DateTimeUtil;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(value = "/transactions", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> saveTransaction(@Valid @RequestBody TransactionRq transactionRq) {
		transactionService.saveTransaction(transactionRq.getAmount(), transactionRq.getTimestamp());
		if (DateTimeUtil.getTimestampBefore60SecondsInUTC() > transactionRq.getTimestamp()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@GetMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<TransactionStatsDTO> getTransactions() {
		return new ResponseEntity<>(transactionService.getTransactionStatsBeforeOneMin(), HttpStatus.OK);
	}
}
