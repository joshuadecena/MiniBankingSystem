package com.capstone.mbs.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO (
	
	Long transactionId,
    Long sourceAccountId,
    Long destinationAccountId,
    BigDecimal amount,
    LocalDateTime timestamp

) {
	
}
