package com.capstone.mbs.repository;

import com.capstone.mbs.entity.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	// Get all users that contain the provided userId
	List<User> findByUserId(long userId);
	
	// Get all users that contain the provided username
	List<User> findByUsernameContainingIgnoreCase(String username);
	
	// Get all users under the provided role
	List<User> findByRole(User.Role role);
}