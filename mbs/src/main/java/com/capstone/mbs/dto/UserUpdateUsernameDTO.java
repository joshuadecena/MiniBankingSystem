package com.capstone.mbs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateUsernameDTO(
    @NotBlank(message = "New username cannot be blank")
    @Size(min = 3, max = 36, message = "Username must be 3-36 characters")
    String newUsername
) {}