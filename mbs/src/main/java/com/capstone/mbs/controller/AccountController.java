package com.capstone.mbs.controller;

import com.capstone.mbs.dto.AccountDTO;
import com.capstone.mbs.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Optional: For role-based access control (requires Spring Security setup)
// import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Get account details by user ID.
     * Accessible by the user themselves or admin.
     *
     * @param userId The ID of the user.
     * @return Account response DTO or 404 if not found.
     */
    @GetMapping("/user/{userId}")
    // @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // optional security
    public ResponseEntity<AccountDTO.AccountResponse> getAccountByUserId(@PathVariable Long userId) {
        Optional<AccountDTO.AccountResponse> accountOpt = accountService.getAccountByUserId(userId);
        return accountOpt.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/account/id/{accountId}")
    public ResponseEntity<AccountDTO.AccountResponse> getAccountById(@PathVariable Long accountId) {
        return accountService.getAccountByAccountId(accountId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * Get all accounts.
     * Intended for admin use.
     *
     * @return List of all account response DTOs.
     */
    @GetMapping
    // @PreAuthorize("hasRole('ADMIN')") // optional security
    public ResponseEntity<List<AccountDTO.AccountResponse>> getAllAccounts() {
        // You need to add a method in your service layer that fetches all accounts
        List<AccountDTO.AccountResponse> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // For future: Create new account
    /*
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO.AccountResponse> createAccount(@RequestBody AccountDTO.AccountRequest request) {
        AccountDTO.AccountResponse created = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    */

    // For future: Update existing account
    /*
    @PutMapping("/{accountId}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AccountDTO.AccountResponse> updateAccount(
            @PathVariable Long accountId,
            @RequestBody AccountDTO.AccountRequest request) {
        AccountDTO.AccountResponse updated = accountService.updateAccount(accountId, request);
        return ResponseEntity.ok(updated);
    }
    */

    // For future: Delete account
    /*
    @DeleteMapping("/{accountId}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
    */
}