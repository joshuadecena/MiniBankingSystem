package com.capstone.mbs.service;

import com.capstone.mbs.entity.User;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface UserService {
    // Create
    ResponseEntity<User> createUser(String username, String password, User.Role role);

    // Read
    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> getUserById(long userId);
    ResponseEntity<List<User>> getUserByUsername(String username);
    ResponseEntity<List<User>> getUsersByRole(User.Role role);

    // Update
    ResponseEntity<User> updateUsername(long userId, String username);
    ResponseEntity<User> updatePassword(long userId, String newPassword);
    ResponseEntity<User> updateRole(long userId, User.Role newRole);

    // Delete
    ResponseEntity<Void> deleteUser(long userId);
}