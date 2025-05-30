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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;
	
	@ManyToOne // many transactions to one account
	@JoinColumn(name = "source_account_id", referencedColumnName = "account_id", nullable = true)
	private Account sourceAccount;
	
	@ManyToOne // many transactions to one account
	@JoinColumn(name = "destination_account_id", referencedColumnName = "account_id", nullable = true)
	private Account destinationAccount;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Column(name = "timestamp", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime timestamp;

}
