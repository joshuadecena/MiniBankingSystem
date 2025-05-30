package com.capstone.mbs.dto;

import com.capstone.mbs.entity.User.Role;

public record AuthResponseDTO(
    String token,
    Long userId,
    String username,
    Role role
) {}