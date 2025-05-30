package com.capstone.mbs.controller;

import com.capstone.mbs.entity.User;
import com.capstone.mbs.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam User.Role role) {
        return userService.createUser(username, password, role);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable User.Role role) {
        return userService.getUsersByRole(role);
    }

    @PutMapping("/{userId}/username")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUsername(
            @PathVariable long userId,
            @RequestParam String newUsername) {
        return userService.updateUsername(userId, newUsername);
    }

    @PutMapping("/{userId}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updatePassword(
            @PathVariable long userId,
            @RequestParam String newPassword) {
        return userService.updatePassword(userId, newPassword);
    }

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateRole(
            @PathVariable long userId,
            @RequestParam User.Role newRole) {
        return userService.updateRole(userId, newRole);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        return userService.deleteUser(userId);
    }
}