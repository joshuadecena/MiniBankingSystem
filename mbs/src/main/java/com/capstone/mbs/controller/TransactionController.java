package com.capstone.mbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.mbs.dto.TransactionDTO;
import com.capstone.mbs.service.TransactionServiceImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	
	@Autowired
	private final TransactionServiceImpl transactionService;

	@GetMapping("/all")
	public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
		List<TransactionDTO> transactions = transactionService.getAllTransactions();
		
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<List<TransactionDTO>> getAllAccountTransactions(Long accountId) {
		List<TransactionDTO> transactions = transactionService.getAllAccountTransactions(accountId);
		
		return ResponseEntity.ok(transactions);
	}
	
	@PostMapping("/new")
	public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
		TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);
		
		return ResponseEntity.ok(createdTransaction);
	}

}
