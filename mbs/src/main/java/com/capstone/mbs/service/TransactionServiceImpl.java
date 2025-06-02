package com.capstone.mbs.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
    @Autowired
    private MessageSource messageSource;
	
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
	public Page<TransactionResponseDTO> getAllTransactions(Pageable pageable) {
		return transactionRepo.findAll(pageable).map(this::convertToDTO);
	}

	@Override
	public Page<TransactionResponseDTO> getAllAccountTransactions(Long accountId, Pageable pageable) {
		return transactionRepo.findBySourceAccountAccountIdOrDestinationAccountAccountId(accountId, accountId, pageable).map(this::convertToDTO);
	}

	@Override
	public TransactionResponseDTO createTransaction(TransactionCreateDTO transactionCreateDTO) {
		Optional<Account> sourceAccountOptional = accountRepo.findById(transactionCreateDTO.sourceAccountId());
		Account sourceAccount = sourceAccountOptional.orElseThrow(() -> 
			new IllegalArgumentException( // TODO: change to AccountNotFoundException
				messageSource.getMessage("account.notfound", new Object[]{transactionCreateDTO.sourceAccountId()}, LocaleContextHolder.getLocale())
			)
		); 
		
		if (sourceAccount.getBalance().compareTo(transactionCreateDTO.amount()) < 0) {
			throw new IllegalArgumentException( // TODO: change to InsufficientBalanceException
				messageSource.getMessage("account.balance.insufficient", new Object[]{}, LocaleContextHolder.getLocale())); 
		}
		
		Optional<Account> destinationAccountOptional = accountRepo.findById(transactionCreateDTO.destinationAccountId());
		Account destinationAccount = destinationAccountOptional.orElseThrow(() -> 
			new IllegalArgumentException( // TODO: change to AccountNotFoundException
				messageSource.getMessage("account.notfound", new Object[]{transactionCreateDTO.destinationAccountId()}, LocaleContextHolder.getLocale())
			)
		);
		
		sourceAccount.setBalance(sourceAccount.getBalance().subtract(transactionCreateDTO.amount()));
		accountRepo.save(sourceAccount);
		
		destinationAccount.setBalance(destinationAccount.getBalance().add(transactionCreateDTO.amount()));
		accountRepo.save(destinationAccount);
		
		Transaction transaction = new Transaction();
		transaction.setSourceAccount(sourceAccount);
		transaction.setDestinationAccount(destinationAccount);
		transaction.setAmount(transactionCreateDTO.amount());
		transaction.setTimestamp(LocalDateTime.now());
		
		TransactionResponseDTO savedTransactionDTO = convertToDTO(transactionRepo.save(transaction));
		return savedTransactionDTO;
	}

}
