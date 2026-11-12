package com.thonsokvechey.supermarketbackend.modules.auth.controller;

import com.thonsokvechey.supermarketbackend.modules.auth.dto.LoginRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.LoginResponse;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.RefreshTokenRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.service.AuthService;
import com.thonsokvechey.supermarketbackend.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponse.UserInfo> me(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(authService.getCurrentUser(userDetails));
    }
}

