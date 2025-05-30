package com.capstone.mbs.dto;

import com.capstone.mbs.entity.User.Role;

public record UserUpdateRoleDTO(
    Role newRole
) {}