package com.capstone.mbs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.capstone.mbs.dto.TransactionCreateDTO;
import com.capstone.mbs.dto.TransactionResponseDTO;

public interface TransactionService {
	
	Page<TransactionResponseDTO> getAllTransactions(Pageable pageable); // retrieve all transactions from all accounts
	Page<TransactionResponseDTO> getAllAccountTransactions(Long accountId, Pageable pageable); // retrieve all transactions from a specific account
	TransactionResponseDTO createTransaction(TransactionCreateDTO transactionCreateDTO);

}
