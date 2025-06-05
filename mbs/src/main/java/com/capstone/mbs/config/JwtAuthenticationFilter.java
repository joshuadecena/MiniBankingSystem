package com.capstone.mbs.config;

import com.capstone.mbs.entity.User;
import com.capstone.mbs.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
    	log.debug("JwtAuthenticationFilter triggered for: {}", request.getRequestURI());
        final String authHeader = request.getHeader(AUTH_HEADER);
        final String requestURI = request.getRequestURI();

        log.debug("Incoming request to: {}", requestURI);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            log.debug("No Authorization header or invalid format: '{}'", authHeader);
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(BEARER_PREFIX.length());
        log.debug("JWT extracted: {}", jwt);

        String username = null;

        try {
            username = jwtService.extractUsername(jwt);
            log.debug("Username extracted from token: {}", username);
        } catch (Exception e) {
            log.warn("Failed to extract username from token", e);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isEmpty()) {
                log.warn("User not found in DB: {}", username);
                filterChain.doFilter(request, response);
                return;
            }

            UserDetails userDetails = optionalUser.get();

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.debug("User '{}' authenticated and context set", username);
            } else {
                log.warn("Invalid token for user: {}", username);
            }
        }

        filterChain.doFilter(request, response);
    }
}