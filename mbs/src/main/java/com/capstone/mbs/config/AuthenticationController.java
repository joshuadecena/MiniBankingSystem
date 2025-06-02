package com.capstone.mbs.config;

import com.capstone.mbs.dto.AuthResponseDTO;
import com.capstone.mbs.dto.LoginRequestDTO;
import com.capstone.mbs.dto.RefreshTokenRequestDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}