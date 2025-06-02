package com.capstone.mbs.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO (
	Long transactionId,
    Long sourceAccountId,
    Long destinationAccountId,
    BigDecimal amount,
    LocalDateTime timestamp
) {
	
}
