package com.capstone.mbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.mbs.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	// transaction history of specific account
	Page<Transaction> findBySourceAccountAccountIdOrDestinationAccountAccountId(Long sourceAccountId, Long destinationAccountId, Pageable pageable);

}
