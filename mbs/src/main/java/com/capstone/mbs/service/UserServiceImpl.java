package com.capstone.mbs.service;

import com.capstone.mbs.dto.UserCreateDTO;
import com.capstone.mbs.dto.UserDTOMapper;
import com.capstone.mbs.dto.UserResponseDTO;
import com.capstone.mbs.dto.UserUpdatePasswordDTO;
import com.capstone.mbs.dto.UserUpdateRoleDTO;
import com.capstone.mbs.dto.UserUpdateUsernameDTO;
import com.capstone.mbs.entity.User;
import com.capstone.mbs.exception.DuplicateUsernameException;
import com.capstone.mbs.exception.InvalidPasswordException;
import com.capstone.mbs.exception.InvalidUsernameException;
import com.capstone.mbs.exception.UserNotFoundException;
import com.capstone.mbs.repository.UserRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String NO_USER_WITH_ID = "User not found with id: ";
    private static final String NAME_EXISTS = "Username already exists: ";
    
    @Override
    @Transactional
    public ResponseEntity<UserResponseDTO> createUser(@Valid UserCreateDTO userCreateDTO) {
        validateUsername(userCreateDTO.username());
        validatePassword(userCreateDTO.password());
        
        if(usernameExists(userCreateDTO.username())) {
            throw new DuplicateUsernameException(NAME_EXISTS + userCreateDTO.username());
        }

        var user = new User();
        user.setUsername(userCreateDTO.username());
        user.setPassword(passwordEncoder.encode(userCreateDTO.password()));
        user.setRole(userCreateDTO.role());

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
               .body(UserDTOMapper.toUserResponseDTO(savedUser));
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(
            users.stream()
                .map(UserDTOMapper::toUserResponseDTO)
                .toList()
        );
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UserResponseDTO> getUserById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NO_USER_WITH_ID + userId));
        return ResponseEntity.ok(UserDTOMapper.toUserResponseDTO(user));
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Optional<UserResponseDTO>> getUserByUsername(String username) {
    	Optional<User> user = userRepository.findByUsername(username);
        return ResponseEntity.ok(
            user.map(UserDTOMapper::toUserResponseDTO)
        );
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserResponseDTO>> getUsersByRole(User.Role role) {
        List<User> users = userRepository.findByRole(role);
        return ResponseEntity.ok(
            users.stream()
                .map(UserDTOMapper::toUserResponseDTO)
                .toList()
        );
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUsername(long userId, UserUpdateUsernameDTO updateDTO) {
        validateUsername(updateDTO.newUsername());
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NO_USER_WITH_ID + userId));
        
        if(usernameExists(updateDTO.newUsername())) {
            throw new DuplicateUsernameException(NAME_EXISTS + updateDTO.newUsername());
        }

        user.setUsername(updateDTO.newUsername());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(UserDTOMapper.toUserResponseDTO(updatedUser));
    }
    
    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updatePassword(long userId, UserUpdatePasswordDTO updateDTO) {
        validatePassword(updateDTO.newPassword());
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NO_USER_WITH_ID + userId));

        user.setPassword(passwordEncoder.encode(updateDTO.newPassword()));
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(UserDTOMapper.toUserResponseDTO(updatedUser));
    }
    
    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateRole(long userId, UserUpdateRoleDTO updateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NO_USER_WITH_ID + userId));

        user.setRole(updateDTO.newRole());
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(UserDTOMapper.toUserResponseDTO(updatedUser));
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
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}