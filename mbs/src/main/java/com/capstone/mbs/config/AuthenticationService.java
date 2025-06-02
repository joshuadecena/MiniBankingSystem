package com.capstone.mbs.config;

import com.capstone.mbs.dto.AuthResponseDTO;
import com.capstone.mbs.dto.LoginRequestDTO;
import com.capstone.mbs.dto.UserDTOMapper;
import com.capstone.mbs.dto.RefreshTokenRequestDTO;
import com.capstone.mbs.exception.InvalidRefreshTokenException;
import com.capstone.mbs.repository.UserRepository;
import com.capstone.mbs.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthResponseDTO authenticate(LoginRequestDTO request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
            )
        );
        
        var user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        
        return UserDTOMapper.toAuthResponseDTO(jwtToken, refreshToken, user);
    }
    
    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {
        String refreshToken = request.refreshToken();
        String username = jwtService.extractUsername(refreshToken);
        
        if (username != null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                
                String newAccessToken = jwtService.generateToken(user);
                String newRefreshToken = jwtService.generateRefreshToken(user);
                
                return UserDTOMapper.toAuthResponseDTO(newAccessToken, newRefreshToken, user);
            }
        }
        throw new InvalidRefreshTokenException ("Invalid refresh token");
    }
}