package com.capstone.mbs.service;

import com.capstone.mbs.dto.AccountDTO;
import com.capstone.mbs.entity.Account;
import com.capstone.mbs.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	// Get account by ID
	// This method retrieves the account associated with a specific account ID.
	// Convert Entity to DTO response
	private AccountDTO.AccountResponse mapToDto(Account account) {
		AccountDTO.AccountResponse response = new AccountDTO.AccountResponse();
		response.setAccountId(account.getAccountId());
		response.setUserId(account.getUser().getUserId()); // Assuming non-null user
		response.setName(account.getName());
		response.setEmail(account.getEmail());
		response.setAddress(account.getAddress());
		response.setDateOfBirth(account.getDateOfBirth());
		response.setBalance(account.getBalance());
		return response;
	}

	// Get account by user ID (one-to-one relationship)
	// This method retrieves the account associated with a specific user.
	@Override
	public Optional<AccountDTO.AccountResponse> getAccountByUserId(Long userId) {
		return accountRepository.findByUserUserId(userId)
				.map(this::mapToDto);
	}

	
	 //Get account by Account ID with DTO response
	@Override
	public Optional<AccountDTO.AccountResponse> getAccountByAccountId(Long accountId) {
		return accountRepository.findById(accountId)
				.map(this::mapToDto);
	}
	
	// Get all accounts
	@Override
	public List<AccountDTO.AccountResponse> getAllAccounts() {
	    return accountRepository.findAll()
	        .stream()
	        .map(this::mapToDto)
	        .collect(Collectors.toList());
	}
	
//  Convert DTO request to Entity (for updates)
//	private void updateEntityFromDto(Account account, AccountDTO.AccountRequest request) {
//		account.setName(request.getName());
//		account.setEmail(request.getEmail());
//		account.setAddress(request.getAddress());
//		account.setDateOfBirth(request.getDateOfBirth());
//		account.setBalance(request.getBalance()); 
// 		Assuming balance is part of the request		
// 		Assuming balance is not updated via request, if needed, add it to the request DTO
	
//	}


	// TODO: For future use - Create new account
	/*
	@Override
	public AccountDTO.AccountResponse createAccount(AccountDTO.AccountRequest request) {
		Account account = new Account();
		updateEntityFromDto(account, request);

		// You may need to set user if not null, depending on your design
		// account.setUser(userRepository.findById(request.getUserId()).orElseThrow(...));

		Account savedAccount = accountRepository.save(account);
		return mapToDto(savedAccount);
	}
	*/

	// TODO: For future use - Update existing account
	/*
	@Override
	public AccountDTO.AccountResponse updateAccount(Long accountId, AccountDTO.AccountRequest request) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));

		updateEntityFromDto(account, request);
		accountRepository.save(account);

		return mapToDto(account);
	}
	*/

	// TODO: For future use - Delete account
	/*
	@Override
	public void deleteAccount(Long accountId) {
		if (!accountRepository.existsById(accountId)) {
			throw new RuntimeException("Account not found with ID: " + accountId);
		}
		accountRepository.deleteById(accountId);
	}
	*/
}