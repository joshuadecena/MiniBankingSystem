package com.capstone.mbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.mbs.dto.PagedResponseDTO;
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
	public ResponseEntity<PagedResponseDTO<TransactionResponseDTO>> getAllTransactions(Pageable pageable) {
		PagedResponseDTO<TransactionResponseDTO> transactions = transactionService.getAllTransactions(pageable);
		
		return ResponseEntity.ok(transactions);
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<PagedResponseDTO<TransactionResponseDTO>> getAccountTransactions(@PathVariable Long accountId, Pageable pageable) {
		PagedResponseDTO<TransactionResponseDTO> transactions = transactionService.getAllAccountTransactions(accountId, pageable);
		
		return ResponseEntity.ok(transactions);
	}
	
	@PostMapping("/new")
	public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionCreateDTO transactionCreateDTO) {
		TransactionResponseDTO createdTransaction = transactionService.createTransaction(transactionCreateDTO);
		
		return ResponseEntity.ok(createdTransaction);
	}

}
