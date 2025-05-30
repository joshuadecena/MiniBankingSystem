package com.capstone.mbs.service;

import java.util.List;

import com.capstone.mbs.dto.TransactionDTO;

public interface TransactionService {
	
	/*
	 * Get all of the transactions of all accounts. 
	 * Calls getAllTransactions(Sort),
	 * with sorting defaulted to descending timestamp (most recent first).
	 */
	List<TransactionDTO> getAllTransactions();
	
	/*
	 * Get all transactions of specific account.
	 * Calls getAllAccountTransactions(Long, Sort),
	 * with sorting defaulted to descending timestamp (most recent first).
	 */
	List<TransactionDTO> getAllAccountTransactions(Long accountId);
	
	/*
	 * Add a new transaction.
	 */
	TransactionDTO createTransaction(TransactionDTO transactionDTO);

}
