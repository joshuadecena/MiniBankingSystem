package com.capstone.mbs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.mbs.dto.TransactionDTO;
import com.capstone.mbs.entity.Account;
import com.capstone.mbs.entity.Transaction;
import com.capstone.mbs.repository.AccountRepository;
//import com.capstone.mbs.entity.TransactionType;
import com.capstone.mbs.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private AccountRepository accountRepo;
	
//	private Sort defaultSort = Sort.by(Sort.Direction.DESC, "timestamp"); // default sorting to show most recent transactions first
	
	/*
	 * Convert a Transaction entity to a TransactionDTO.
	 */
	private TransactionDTO convertToDTO(Transaction transaction) {
	    return new TransactionDTO(
	        transaction.getTransactionId(),
	        transaction.getSourceAccount() != null ? transaction.getSourceAccount().getAccountId() : null,
	        transaction.getDestinationAccount() != null ? transaction.getDestinationAccount().getAccountId() : null,
	        transaction.getAmount(),
	        transaction.getTimestamp()
	    );
	}
	
//	/*
//	 * Retrieve the corresponding Transaction entity from the database based on the DTO's transaction ID.
//	 */
//	private Transaction retrieveEntityOfDTO(TransactionDTO transactionDTO) {
//	    return transactionRepo.findById(transactionDTO.transactionId())
//	            .orElseThrow(() -> new IllegalArgumentException("Transaction not found with ID: " + transactionDTO.transactionId()));
//	}

	@Override
	public List<TransactionDTO> getAllTransactions() {
		return transactionRepo.findAll().stream()
				.map(this::convertToDTO)
				.toList();
	}

	@Override
	public List<TransactionDTO> getAllAccountTransactions(Long accountId) {
		return transactionRepo.findBySourceAccountAccountIdOrDestinationAccountAccountId(accountId).stream()
				.map(this::convertToDTO)
				.toList();
	}

	@Override
	public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
		Optional<Account> sourceAccountOptional = accountRepo.findById(transactionDTO.sourceAccountId());
		Account sourceAccount = sourceAccountOptional.orElseThrow(() -> 
			new IllegalArgumentException("Source account not found with ID: " + transactionDTO.sourceAccountId()));
		
		Optional<Account> destinationAccountOptional = accountRepo.findById(transactionDTO.destinationAccountId());
		Account destinationAccount = destinationAccountOptional.orElseThrow(() -> 
			new IllegalArgumentException("Destination account not found with ID: " + transactionDTO.destinationAccountId()));
		
		Transaction transaction = new Transaction();
		transaction.setSourceAccount(sourceAccount);
		transaction.setDestinationAccount(destinationAccount);
		transaction.setAmount(transactionDTO.amount());
		transaction.setTimestamp(transactionDTO.timestamp());
		
		TransactionDTO savedTransactionDTO = convertToDTO(transactionRepo.save(transaction));
		return savedTransactionDTO;
	}

}
