package com.capstone.mbs.dto;

import com.capstone.mbs.entity.User.Role;

public record UserResponseDTO(
    Long userId,
    String username,
    Role role
) {
    // Compact constructor for additional validation if needed
    public UserResponseDTO {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than or equal to 1");
        }
    }
}