package com.capstone.mbs.dto;

import com.capstone.mbs.entity.User.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 36, message = "Username must be 3-36 characters")
    String username,
    
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password,
    
    @NotBlank(message = "Password confirmation cannot be blank")
    String confirmPassword,
    
    Role role
) {
    public UserCreateDTO {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Password and confirmation must match");
        }
    }
}