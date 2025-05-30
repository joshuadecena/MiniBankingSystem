package com.capstone.mbs.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.mbs.dto.TransactionCreateDTO;
import com.capstone.mbs.dto.TransactionResponseDTO;
import com.capstone.mbs.entity.Account;
import com.capstone.mbs.entity.Transaction;
import com.capstone.mbs.repository.AccountRepository;
import com.capstone.mbs.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private AccountRepository accountRepo;
	
	private TransactionResponseDTO convertToDTO(Transaction transaction) {
	    return new TransactionResponseDTO(
	        transaction.getTransactionId(),
	        transaction.getSourceAccount() != null ? transaction.getSourceAccount().getAccountId() : null,
	        transaction.getDestinationAccount() != null ? transaction.getDestinationAccount().getAccountId() : null,
	        transaction.getAmount(),
	        transaction.getTimestamp()
	    );
	}

	@Override
	public List<TransactionResponseDTO> getAllTransactions() {
		return transactionRepo.findAll().stream()
				.map(this::convertToDTO)
				.toList();
	}

	@Override
	public List<TransactionResponseDTO> getAllAccountTransactions(Long accountId) {
		return transactionRepo.findBySourceAccountAccountIdOrDestinationAccountAccountId(accountId).stream()
				.map(this::convertToDTO)
				.toList();
	}

	@Override
	public TransactionResponseDTO createTransaction(TransactionCreateDTO transactionCreateDTO) {
		Optional<Account> sourceAccountOptional = accountRepo.findById(transactionCreateDTO.sourceAccountId());
		Account sourceAccount = sourceAccountOptional.orElseThrow(() -> 
			new IllegalArgumentException("Source account not found with ID: " + transactionCreateDTO.sourceAccountId()));
		
		Optional<Account> destinationAccountOptional = accountRepo.findById(transactionCreateDTO.destinationAccountId());
		Account destinationAccount = destinationAccountOptional.orElseThrow(() -> 
			new IllegalArgumentException("Destination account not found with ID: " + transactionCreateDTO.destinationAccountId()));
		
		Transaction transaction = new Transaction();
		transaction.setSourceAccount(sourceAccount);
		transaction.setDestinationAccount(destinationAccount);
		transaction.setAmount(transactionCreateDTO.amount());
		transaction.setTimestamp(LocalDateTime.now());
		
		TransactionResponseDTO savedTransactionDTO = convertToDTO(transactionRepo.save(transaction));
		return savedTransactionDTO;
	}

}
