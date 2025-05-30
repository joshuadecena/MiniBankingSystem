package com.capstone.mbs.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {
	
	/*
	 * Primary key.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;
	
	/*
	 * Foreign key to the source account.
	 * 
	 * If this is null and the destination account is not null, the transaction type is DEPOSIT.
	 */
	@ManyToOne // many transactions to one account
	@JoinColumn(name = "source_account_id", referencedColumnName = "account_id", nullable = true)
	private Account sourceAccount;
	
	/*
	 * Foreign key to the destination account.
	 * 
	 * If this is null and the source account is not null, the transaction type is WITHDRAWAL.
	 */
	@ManyToOne // many transactions to one account
	@JoinColumn(name = "destination_account_id", referencedColumnName = "account_id", nullable = true)
	private Account destinationAccount;
	
	/*
	 * The amount to be deposited, withdrawn, or transferred.
	 */
	@Column(name = "amount")
	private BigDecimal amount;
	
	/*
	 * Date and time of transaction.
	 */
	@Column(name = "timestamp", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime timestamp;

}
