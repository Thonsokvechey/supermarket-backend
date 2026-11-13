package com.thonsokvechey.supermarketbackend.modules.auth.service.impl;

import com.thonsokvechey.supermarketbackend.modules.auth.dto.CreateUserRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.UpdateUserRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.UserResponse;
import com.thonsokvechey.supermarketbackend.modules.auth.entity.Permission;
import com.thonsokvechey.supermarketbackend.modules.auth.entity.Role;
import com.thonsokvechey.supermarketbackend.modules.auth.entity.User;
import com.thonsokvechey.supermarketbackend.modules.auth.repository.RoleRepository;
import com.thonsokvechey.supermarketbackend.modules.auth.repository.UserRepository;
import com.thonsokvechey.supermarketbackend.modules.auth.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAllWithRolesAndPermissions().stream()
                .map(this::toUserResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        String username = request.getUsername().trim();
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new IllegalArgumentException("Username '" + username + "' is already taken");
        }

        String email = (request.getEmail() != null && !request.getEmail().isBlank())
                ? request.getEmail().trim()
                : null;
        if (email != null && userRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Email '" + email + "' is already in use");
        }

        Set<Role> assignedRoles = resolveRoles(request.getRoleIds(), request.getRoleNames());

        User user = User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .isActive(request.getIsActive() == null || request.getIsActive())
                .roles(assignedRoles)
                .build();

        User saved = userRepository.save(user);
        return toUserResponse(saved);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        User user = userRepository.findByIdWithRolesAndPermissions(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            String username = request.getUsername().trim();
            if (userRepository.existsByUsernameIgnoreCaseAndIdNot(username, id)) {
                throw new IllegalArgumentException("Username '" + username + "' is already taken");
            }
            user.setUsername(username);
        }

        if (request.getEmail() != null) {
            String email = request.getEmail().isBlank() ? null : request.getEmail().trim();
            if (email != null && userRepository.existsByEmailIgnoreCaseAndIdNot(email, id)) {
                throw new IllegalArgumentException("Email '" + email + "' is already in use");
            }
            user.setEmail(email);
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getIsActive() != null) {
            user.setActive(request.getIsActive());
        }

        if (request.getRoleIds() != null || request.getRoleNames() != null) {
            user.setRoles(resolveRoles(request.getRoleIds(), request.getRoleNames()));
        }

        User saved = userRepository.save(user);
        return toUserResponse(saved);
    }

    private Set<Role> resolveRoles(Set<UUID> roleIds, Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        if (roleIds != null && !roleIds.isEmpty()) {
            roles.addAll(roleRepository.findAllById(roleIds));
        }
        if (roleNames != null && !roleNames.isEmpty()) {
            roles.addAll(roleRepository.findByNameIn(roleNames));
        }
        return roles;
    }

    private UserResponse toUserResponse(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        Set<String> permissionKeys = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(Permission::getPermissionKey)
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isActive(user.isActive())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .roles(roleNames)
                .permissions(permissionKeys)
                .build();
    }
}

