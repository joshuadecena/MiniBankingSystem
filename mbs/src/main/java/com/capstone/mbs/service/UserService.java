package com.capstone.mbs.service;

import com.capstone.mbs.dto.UserCreateDTO;
import com.capstone.mbs.dto.UserResponseDTO;
import com.capstone.mbs.dto.UserUpdatePasswordDTO;
import com.capstone.mbs.dto.UserUpdateRoleDTO;
import com.capstone.mbs.dto.UserUpdateUsernameDTO;
import com.capstone.mbs.entity.User;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface UserService {
    // Create
    ResponseEntity<UserResponseDTO> createUser(UserCreateDTO userCreateDTO);

    // Read
    ResponseEntity<List<UserResponseDTO>> getAllUsers();
    ResponseEntity<UserResponseDTO> getUserById(long userId);
    ResponseEntity<List<UserResponseDTO>> getUserByUsername(String username);
    ResponseEntity<List<UserResponseDTO>> getUsersByRole(User.Role role);

    // Update
    ResponseEntity<UserResponseDTO> updateUsername(long userId, UserUpdateUsernameDTO updateDTO);
    ResponseEntity<UserResponseDTO> updatePassword(long userId, UserUpdatePasswordDTO updateDTO);
    ResponseEntity<UserResponseDTO> updateRole(long userId, UserUpdateRoleDTO updateDTO);

    // Delete
    ResponseEntity<Void> deleteUser(long userId);
}