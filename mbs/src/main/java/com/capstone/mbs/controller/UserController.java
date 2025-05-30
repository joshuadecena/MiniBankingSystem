package com.capstone.mbs.controller;

import com.capstone.mbs.dto.UserCreateDTO;
import com.capstone.mbs.dto.UserResponseDTO;
import com.capstone.mbs.dto.UserUpdatePasswordDTO;
import com.capstone.mbs.dto.UserUpdateRoleDTO;
import com.capstone.mbs.dto.UserUpdateUsernameDTO;
import com.capstone.mbs.entity.User;
import com.capstone.mbs.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRole(@PathVariable User.Role role) {
        return userService.getUsersByRole(role);
    }

    @PutMapping("/{userId}/username")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUsername(
            @PathVariable long userId,
            @RequestBody @Valid UserUpdateUsernameDTO updateDTO) {
        return userService.updateUsername(userId, updateDTO);
    }

    @PutMapping("/{userId}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updatePassword(
            @PathVariable long userId,
            @RequestBody @Valid UserUpdatePasswordDTO updateDTO) {
        return userService.updatePassword(userId, updateDTO);
    }

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateRole(
            @PathVariable long userId,
            @RequestBody @Valid UserUpdateRoleDTO updateDTO) {
        return userService.updateRole(userId, updateDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        return userService.deleteUser(userId);
    }
}