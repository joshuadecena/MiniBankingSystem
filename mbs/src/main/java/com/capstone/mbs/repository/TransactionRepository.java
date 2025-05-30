package com.capstone.mbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.mbs.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	/*
	 * Get all transactions (history) of specific account.
	 * 
	 * USE: For displaying transaction history of a Customer on that Customer's dashboard.
	 */
	List<Transaction> findBySourceAccountAccountIdOrDestinationAccountAccountId(Long accountId);
	
//	/*
//	 * Filter by Transaction Type and Timestamp.
//	 * 
//	 * USE: For filtering transaction history as Admin.
//	 */
//	List<Transaction> findByTransactionTypeContainsAndTimestampBetween(
//			String transactionType,
//			LocalDateTime minDate,
//			LocalDateTime maxDate,
//			Sort sort
//	);
	
//	/*
//	 * Filter by Account ID, Transaction Type, and Timestamp.
//	 * 
//	 * USE: For filtering transaction history as Admin or Customer.
//	 */
//	List<Transaction> findBySourceAccountAccountIdOrDestinationAccountAccountIdAndTransactionTypeContainsAndTimestampBetween(
//			Long accountId,
//			String transactionType,
//			LocalDateTime minDate,
//			LocalDateTime maxDate,
//			Sort sort
//	);

}
