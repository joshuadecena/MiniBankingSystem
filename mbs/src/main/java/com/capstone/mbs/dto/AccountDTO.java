package com.capstone.mbs.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;

import java.time.LocalDate;

import java.math.BigDecimal;

public class AccountDTO {
	
	private AccountDTO() {
		// Default constructor
	}
	
	public static class AccountRequest {
		
		@NotBlank(message = "Name cannot be blank")
		@Size(max = 100, message = "Name must not exceed 100 characters")
		private String name;
		
		@NotBlank(message = "Email cannot be blank")
		@Email(message = "Email should be valid")
		@Size(max = 100, message = "Email must not exceed 100 characters")
		private String email;
		
		@Size(max = 255, message = "Address must not exceed 255 characters")
		private String address;
		
		@NotNull(message = "Date of Birth is required")
		private LocalDate dateOfBirth;
		
		@DecimalMin(value = "0.0", inclusive = true, message = "Balance must be greater than or equal to zero")
		private BigDecimal balance = BigDecimal.ZERO; // Default balance is zero

		// For possible future use: link to user by userId (if creating new account with user)
        // @NotNull(message = "User ID is required")
        // private Long userId;

        // Getters and setters
		public String getName() 
		{ return name; }
		public void setName(String name) 
		{ this.name = name; }
		
		public String getEmail() 
		{ return email; }
		public void setEmail(String email) 
		{ this.email = email; }
		
		public String getAddress() 
		{ return address; }
		public void setAddress(String address) 
		{ this.address = address; }
		
		public LocalDate getDateOfBirth() 
		{ return dateOfBirth; }
		public void setDateOfBirth(LocalDate dateOfBirth) 
		{ this.dateOfBirth = dateOfBirth; }
		
        // Uncomment for future create account requests with user details
        /*
        private UserDTO.UserCreateRequest user;

        public UserDTO.UserCreateRequest getUser() {
            return user;
        }

        public void setUser(UserDTO.UserCreateRequest user) {
            this.user = user;
        }
        */
	}
	
	 /**
     * DTO for data returned from API (responses)
     */
	
	public static class AccountResponse {
		
		private Long accountId;
		private Long userId;
		private String name;
		private String email;
		private String address;
		private LocalDate dateOfBirth;
		private BigDecimal balance;
		
		// Getters and setters
		public Long getAccountId() 
		{ return accountId; }
		public void setAccountId(Long accountId) 
		{ this.accountId = accountId; }
		
		public Long getUserId() 
		{ return userId; }
		public void setUserId(Long userId) 
		{ this.userId = userId; }
		
		public String getName() 
		{ return name; }
		public void setName(String name) 
		{ this.name = name; }
		
		public String getEmail() 
		{ return email; }
		public void setEmail(String email) 
		{ this.email = email; }
		
		public String getAddress() 
		{ return address; }
		public void setAddress(String address) 
		{ this.address = address; }
		
		public LocalDate getDateOfBirth() 
		{ return dateOfBirth; }
		public void setDateOfBirth(LocalDate dateOfBirth) 
		{ this.dateOfBirth = dateOfBirth; }
		
		public BigDecimal getBalance() 
		{ return balance; }
		public void setBalance(BigDecimal balance) 
		{ this.balance = balance; }
		
        //  For future nested user details in responses
        /*
        private UserDTO.UserResponse user;

        public UserDTO.UserResponse getUser() {
            return user;
        }

        public void setUser(UserDTO.UserResponse user) {
            this.user = user;
        }
        */
	}
	
    // You could create a UserDTO class separately with create, update, delete requests and responses
    // For example:

    /*
    public static class UserDTO {

        public static class UserCreateRequest {
            @NotBlank
            private String username;
            @NotBlank
            private String password;
            // other user fields and validation

            // getters/setters
        }

        public static class UserUpdateRequest {
            // fields to update user info
        }

        public static class UserResponse {
            private Long userId;
            private String username;
            // other user details

            // getters/setters
        }
    }
    */
	
}