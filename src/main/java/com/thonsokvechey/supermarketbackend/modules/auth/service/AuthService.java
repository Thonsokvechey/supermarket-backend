package com.thonsokvechey.supermarketbackend.modules.auth.service;

import com.thonsokvechey.supermarketbackend.modules.auth.dto.LoginRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.LoginResponse;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.RefreshTokenRequest;
import com.thonsokvechey.supermarketbackend.security.CustomUserDetails;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    LoginResponse.UserInfo getCurrentUser(CustomUserDetails userDetails);
}
