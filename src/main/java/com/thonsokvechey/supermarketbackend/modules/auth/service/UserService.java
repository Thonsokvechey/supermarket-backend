package com.thonsokvechey.supermarketbackend.modules.auth.service;

import com.thonsokvechey.supermarketbackend.modules.auth.dto.CreateUserRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.UpdateUserRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(UUID id, UpdateUserRequest request);
}

