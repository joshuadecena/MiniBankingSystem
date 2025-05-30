package com.capstone.mbs.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")

public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", nullable = false)
	private Long accountId;
	
	@OneToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false, length = 100)
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@Column(length = 100, nullable = false, unique = true)
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email cannot be blank")
	private String email;
	
	@Column(length = 255)
	private String address;
	
	@NotNull(message = "Date of Birth is required")
	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;
	
	@Column(nullable = false)
	@DecimalMin(value = "0.0", inclusive = true, message = "Balance must be greater than or equal to zero")
	private BigDecimal balance = BigDecimal.ZERO; // Default balance is zero
	
	//Constructors
	public Account () 
	{
		// Default constructor
	}
	
	public Account(User user, String name, String email, String address, LocalDate dateOfBirth, BigDecimal balance) 
	{
		this.user = user;
		this.name = name;
		this.email = email;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
		this.balance = balance;
	}
	
}