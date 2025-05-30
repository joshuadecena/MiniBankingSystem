package com.capstone.mbs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdatePasswordDTO(
    @NotBlank(message = "Current password cannot be blank")
    String currentPassword,
    
    @NotBlank(message = "New password cannot be blank")
    @Size(min = 6, message = "New password must be at least 6 characters")
    String newPassword,
    
    @NotBlank(message = "Password confirmation cannot be blank")
    String confirmPassword
) {
    public UserUpdatePasswordDTO {
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirmation must match");
        }
    }
}