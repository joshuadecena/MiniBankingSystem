package com.capstone.mbs.dto;

import com.capstone.mbs.entity.User.Role;

import lombok.Builder;

@Builder
public record AuthResponseDTO(
    String accessToken,
    String refreshToken,
    Long userId,
    String username,
    Role role
) {}