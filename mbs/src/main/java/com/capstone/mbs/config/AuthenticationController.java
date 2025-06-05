package com.capstone.mbs.config;

import com.capstone.mbs.dto.AuthResponseDTO;
import com.capstone.mbs.dto.LoginRequestDTO;
import com.capstone.mbs.dto.RefreshTokenRequestDTO;
import com.capstone.mbs.exception.InvalidRefreshTokenException;
import com.capstone.mbs.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;
    private static final String REFRESH = "refreshToken";
    private static final String REFRESH_API = "/api/auth/refresh-token";

    @GetMapping("/token-state")
    public ResponseEntity<Map<String, Object>> checkTokenState(
            @RequestHeader("Authorization") String authHeader) {
        
        Map<String, Object> response = new HashMap<>();
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.put("valid", false);
            return ResponseEntity.ok(response);
        }
        
        String token = authHeader.substring(7);
        try {
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = userService.loadUserByUsername(username);
            
            boolean isValid = jwtService.isTokenValid(token, userDetails);
            boolean isAboutToExpire = jwtService.isTokenAboutToExpire(token, 300000); // 5 minutes
            
            response.put("valid", isValid);
            response.put("aboutToExpire", isAboutToExpire);
            response.put("expiresAt", jwtService.extractExpiration(token).getTime());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("valid", false);
            return ResponseEntity.ok(response);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO request,
            HttpServletResponse response) {
        
        AuthResponseDTO authResponse = authenticationService.authenticate(request);
        
        // Set refresh token in HttpOnly cookie
        Cookie refreshTokenCookie = new Cookie(REFRESH, authResponse.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // Enable in production with HTTPS
        refreshTokenCookie.setPath(REFRESH_API);
        refreshTokenCookie.setMaxAge((int) (authenticationService.getRefreshExpiration() / 1000));
        response.addCookie(refreshTokenCookie);
        
        // Return access token in response body
        return ResponseEntity.ok()
                .body(authResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshToken(
            @CookieValue(name = REFRESH, required = false) String refreshToken,
            HttpServletResponse response) {
        
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new InvalidRefreshTokenException("Refresh token is missing");
        }
        
        AuthResponseDTO authResponse = authenticationService.refreshToken(
                new RefreshTokenRequestDTO(refreshToken));
        
        // Update refresh token cookie
        Cookie refreshTokenCookie = new Cookie(REFRESH, authResponse.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath(REFRESH_API);
        refreshTokenCookie.setMaxAge((int) (authenticationService.getRefreshExpiration() / 1000));
        response.addCookie(refreshTokenCookie);
        
        return ResponseEntity.ok()
                .body(AuthResponseDTO.builder()
                        .accessToken(authResponse.accessToken())
                        .role(authResponse.role())
                        .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Clear refresh token cookie
        Cookie refreshTokenCookie = new Cookie(REFRESH, null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath(REFRESH_API);
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
        
        return ResponseEntity.noContent().build();
    }
}