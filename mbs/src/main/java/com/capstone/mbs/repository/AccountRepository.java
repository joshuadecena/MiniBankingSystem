package com.capstone.mbs.repository;

import com.capstone.mbs.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	// Find an account by its ID since it is one to one(One User = One Account)
	Optional<Account> findByUserUserId(Long userId);
	
	// Find by name(if name is not unique, return list), case-insensitive
	List<Account> findByNameIgnoreCase(String name);
	
	// Find by account ID
	Optional<Account> findByAccountId(Long accountId);
	
	// Find by email(must be unique), case-insensitive
	Optional<Account> findByEmailIgnoreCase(String email);
	
}