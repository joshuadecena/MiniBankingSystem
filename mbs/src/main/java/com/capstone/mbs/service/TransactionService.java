package com.capstone.mbs.service;

import java.util.List;

import com.capstone.mbs.dto.TransactionCreateDTO;
import com.capstone.mbs.dto.TransactionResponseDTO;

public interface TransactionService {
	
	List<TransactionResponseDTO> getAllTransactions(); // retrieve all transactions from all accounts
	List<TransactionResponseDTO> getAllAccountTransactions(Long accountId); // retrieve all transactions from a specific account
	TransactionResponseDTO createTransaction(TransactionCreateDTO transactionCreateDTO);

}
