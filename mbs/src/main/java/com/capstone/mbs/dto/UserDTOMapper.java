package com.capstone.mbs.dto;

import com.capstone.mbs.entity.User;
import com.capstone.mbs.entity.User.Role;

import java.util.List;

public final class UserDTOMapper {
    private UserDTOMapper() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(
            user.getUserId(),
            user.getUsername(),
            user.getRole()
        );
    }

    public static User toUserEntity(UserCreateDTO userCreateDTO) {
        return User.builder()
            .username(userCreateDTO.username())
            .password(userCreateDTO.password()) // Note: Password should be encoded in service layer
            .role(userCreateDTO.role())
            .build();
    }

    public static UserUpdateUsernameDTO toUserUpdateUsernameDTO(String newUsername) {
        return new UserUpdateUsernameDTO(newUsername);
    }

    public static UserUpdatePasswordDTO toUserUpdatePasswordDTO(
            String currentPassword, 
            String newPassword, 
            String confirmPassword) {
        return new UserUpdatePasswordDTO(currentPassword, newPassword, confirmPassword);
    }

    public static UserUpdateRoleDTO toUserUpdateRoleDTO(Role newRole) {
        return new UserUpdateRoleDTO(newRole);
    }

    public static List<UserResponseDTO> toUserResponseDTOList(List<User> users) {
        return users.stream()
            .map(UserDTOMapper::toUserResponseDTO)
            .toList();
    }

    public static AuthResponseDTO toAuthResponseDTO(String accessToken, String refreshToken, User user) {
        return new AuthResponseDTO(
            accessToken,
            refreshToken,
            user.getUserId(),
            user.getUsername(),
            user.getRole()
        );
    }
}