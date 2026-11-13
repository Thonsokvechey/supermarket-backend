package com.thonsokvechey.supermarketbackend.modules.auth.service.impl;

import com.thonsokvechey.supermarketbackend.modules.auth.dto.LoginRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.LoginResponse;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.LogoutRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.RefreshTokenRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.entity.User;
import com.thonsokvechey.supermarketbackend.modules.auth.repository.UserRepository;
import com.thonsokvechey.supermarketbackend.modules.auth.service.AuthService;
import com.thonsokvechey.supermarketbackend.security.CustomUserDetails;
import com.thonsokvechey.supermarketbackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail().trim(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userRepository.findByIdWithRolesAndPermissions(userDetails.getId())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        if (!user.isActive()) {
            throw new DisabledException("User account is inactive");
        }

        OffsetDateTime now = OffsetDateTime.now();
        user.setLastLoginAt(now);
        userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return buildLoginResponse(userDetails, now, accessToken, refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        String token = request.getRefreshToken().trim();
        if (!jwtService.isTokenValid(token, "REFRESH")) {
            throw new BadCredentialsException("Invalid or expired refresh token");
        }

        UUID userId = jwtService.extractUserId(token);
        User user = userRepository.findByIdWithRolesAndPermissions(userId)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        if (!user.isActive()) {
            throw new DisabledException("User account is inactive");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        return buildLoginResponse(userDetails, user.getLastLoginAt(), newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(String authorizationHeader, LogoutRequest request) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7).trim();
            if (!accessToken.isEmpty()) {
                jwtService.blacklistToken(accessToken);
            }
        }

        if (request != null && request.getRefreshToken() != null && !request.getRefreshToken().isBlank()) {
            jwtService.blacklistToken(request.getRefreshToken().trim());
        }

        SecurityContextHolder.clearContext();
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse.UserInfo getCurrentUser(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new BadCredentialsException("Authentication required");
        }
        User user = userRepository.findByIdWithRolesAndPermissions(userDetails.getId())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        return LoginResponse.UserInfo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.isActive())
                .lastLoginAt(user.getLastLoginAt())
                .roles(userDetails.getRoles())
                .permissions(userDetails.getPermissions())
                .build();
    }

    private LoginResponse buildLoginResponse(
            CustomUserDetails userDetails,
            OffsetDateTime lastLoginAt,
            String accessToken,
            String refreshToken
    ) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresInMs(jwtService.getAccessTokenExpirationMs())
                .user(LoginResponse.UserInfo.builder()
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .isActive(userDetails.isActive())
                        .lastLoginAt(lastLoginAt)
                        .roles(userDetails.getRoles())
                        .permissions(userDetails.getPermissions())
                        .build())
                .build();
    }
}
