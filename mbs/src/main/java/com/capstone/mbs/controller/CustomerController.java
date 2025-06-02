package com.capstone.mbs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    // For demo purposes, dummy data
    // In real app, use authentication.getName() to get logged-in user and fetch their data

    @GetMapping("/balance")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String, Object>> getBalance(Authentication authentication) {
        String username = authentication.getName();
        // TODO: Fetch real balance based on username
        Map<String, Object> balance = Map.of("username", username, "balance", 3500);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String, String>> transferFunds(@RequestBody Map<String, Object> request, Authentication authentication) {
        String fromUser = authentication.getName();
        Integer toAccountId = (Integer) request.get("toAccountId");
        Double amount = ((Number) request.get("amount")).doubleValue();

        // TODO: Implement validation and actual fund transfer logic here

        // Dummy success response
        return ResponseEntity.ok(Map.of(
                "message", "Transfer successful",
                "fromUser", fromUser,
                "toAccountId", toAccountId.toString(),
                "amount", amount.toString()
        ));
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Map<String, Object>>> getTransactionHistory(Authentication authentication) {
        String username = authentication.getName();
        // TODO: Fetch real transaction history for this user
        List<Map<String, Object>> transactions = List.of(
                Map.of("transactionId", 201, "amount", 100, "type", "debit"),
                Map.of("transactionId", 202, "amount", 200, "type", "credit")
        );
        return ResponseEntity.ok(transactions);
    }
}
