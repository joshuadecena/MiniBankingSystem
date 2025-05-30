package com.capstone.mbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.mbs.dto.TransactionCreateDTO;
import com.capstone.mbs.dto.TransactionResponseDTO;
import com.capstone.mbs.service.TransactionServiceImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	
	@Autowired
	private final TransactionServiceImpl transactionService;

	@GetMapping("/all")
	public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
		List<TransactionResponseDTO> transactions = transactionService.getAllTransactions();
		
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<List<TransactionResponseDTO>> getAllAccountTransactions(Long accountId) {
		List<TransactionResponseDTO> transactions = transactionService.getAllAccountTransactions(accountId);
		
		return ResponseEntity.ok(transactions);
	}
	
	@PostMapping("/new")
	public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionCreateDTO transactionCreateDTO) {
		TransactionResponseDTO createdTransaction = transactionService.createTransaction(transactionCreateDTO);
		
		return ResponseEntity.ok(createdTransaction);
	}

}
