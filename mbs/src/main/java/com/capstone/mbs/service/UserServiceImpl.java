package com.capstone.mbs.service;

import com.capstone.mbs.entity.User;
import com.capstone.mbs.exception.DuplicateUsernameException;
import com.capstone.mbs.exception.InvalidPasswordException;
import com.capstone.mbs.exception.InvalidUsernameException;
import com.capstone.mbs.exception.UserNotFoundException;
import com.capstone.mbs.repository.UserRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private static String noUserWithId = "User not found with id: ";
	private static String nameExists = "Username already exists: ";
	
	@Override
    @Transactional
    public ResponseEntity<User> createUser(@NonNull String username, @NonNull String password, @NonNull User.Role role) {
        validateUsername(username);
        validatePassword(password);
        
        if(usernameExists(username)) {
            throw new DuplicateUsernameException(nameExists + username);
        }

        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<User> getUserById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(noUserWithId + userId));
        return ResponseEntity.ok(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<User>> getUserByUsername(String username) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(username);
        return ResponseEntity.ok(users);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<User>> getUsersByRole(User.Role role) {
        List<User> users = userRepository.findByRole(role);
        return ResponseEntity.ok(users);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUsername(long userId, String newUsername) {
        validateUsername(newUsername);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(noUserWithId + userId));
        
        if(usernameExists(newUsername)) {
            throw new DuplicateUsernameException(nameExists + newUsername);
        }

        user.setUsername(newUsername);
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
    
    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updatePassword(long userId, String newPassword) {
        validatePassword(newPassword);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(noUserWithId + userId));

        user.setPassword(passwordEncoder.encode(newPassword));
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
    
    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateRole(long userId, User.Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(noUserWithId + userId));

        user.setRole(newRole);
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteUser(long userId) {
        if(!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
    
    
    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
	
    private void validateUsername(String username) {
        if(username == null || username.isBlank()) {
            throw new InvalidUsernameException("Username cannot be blank");
        }
        if(username.length() > 36) {
            throw new InvalidUsernameException("Username length must be 36 characters or less");
        }
    }
	
    private void validatePassword(String password) {
        if(password == null || password.isBlank()) {
            throw new InvalidPasswordException("Password cannot be blank");
        }
        if(password.length() < 6) {
            throw new InvalidPasswordException("Password must be at least 6 characters");
        }
    }
}