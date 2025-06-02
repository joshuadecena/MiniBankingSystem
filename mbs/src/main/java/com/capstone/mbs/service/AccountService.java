package com.capstone.mbs.service;

import com.capstone.mbs.dto.AccountDTO;
import com.capstone.mbs.dto.PagedResponseDTO;

import java.util.Optional;

import org.springframework.data.domain.Pageable;


public interface AccountService {

    /**
     * Retrieves the account details by user ID.
 	* This method is used to fetch the account associated with a specific user.
     */
    
	PagedResponseDTO<AccountDTO.AccountResponse> getAllAccounts(Pageable pageable);
    
    Optional<AccountDTO.AccountResponse> getAccountByUserId(Long userId);
    
    Optional<AccountDTO.AccountResponse> getAccountByAccountId(Long accountId);
    
//    Optional<AccountDTO.AccountResponse> getAccountByAccountId(Long AccountId);


    // For future:
    // AccountDTO.AccountResponse updateAccount(Long accountId, AccountDTO.AccountRequest request);
    // AccountDTO.AccountResponse createAccount(AccountDTO.AccountRequest request);
    // void deleteAccount(Long accountId);
}