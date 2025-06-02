package com.capstone.mbs.service;

import com.capstone.mbs.dto.AccountDTO;

import java.util.List;
import java.util.Optional;


public interface AccountService {

    /**
     * Retrieves the account details by user ID.
 	* This method is used to fetch the account associated with a specific user.
     */
    
    List<AccountDTO.AccountResponse> getAllAccounts();
    
    Optional<AccountDTO.AccountResponse> getAccountByUserId(Long userId);
    
    Optional<AccountDTO.AccountResponse> getAccountByAccountId(Long accountId);
    
//    Optional<AccountDTO.AccountResponse> getAccountByAccountId(Long AccountId);


    // For future:
    // AccountDTO.AccountResponse updateAccount(Long accountId, AccountDTO.AccountRequest request);
    // AccountDTO.AccountResponse createAccount(AccountDTO.AccountRequest request);
    // void deleteAccount(Long accountId);
}