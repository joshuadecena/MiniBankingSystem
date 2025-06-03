package com.capstone.mbs.service;

import org.springframework.data.domain.Pageable;

import com.capstone.mbs.dto.PagedResponseDTO;
import com.capstone.mbs.dto.TransactionCreateDTO;
import com.capstone.mbs.dto.TransactionResponseDTO;

public interface TransactionService {
	
	PagedResponseDTO<TransactionResponseDTO> getAllTransactions(Pageable pageable); // retrieve all transactions from all accounts
	PagedResponseDTO<TransactionResponseDTO> getAllAccountTransactions(Long accountId, Pageable pageable); // retrieve all transactions from a specific account
	TransactionResponseDTO createTransaction(TransactionCreateDTO transactionCreateDTO);

}
