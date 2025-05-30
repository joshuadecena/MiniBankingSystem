package com.capstone.mbs.dto;

import java.math.BigDecimal;

public record TransactionCreateDTO(
	Long sourceAccountId,
	Long destinationAccountId,
	BigDecimal amount
) {

}
