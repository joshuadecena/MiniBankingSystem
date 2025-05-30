package com.capstone.mbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.mbs.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	// transaction history of specific account
	List<Transaction> findBySourceAccountAccountIdOrDestinationAccountAccountId(Long sourceAccountId, Long destinationAccountId);

}
