package com.thonsokvechey.supermarketbackend.modules.auth.service.impl;

import com.thonsokvechey.supermarketbackend.modules.auth.dto.AssignPermissionsRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.CreateRoleRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.PermissionResponse;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.RoleResponse;
import com.thonsokvechey.supermarketbackend.modules.auth.entity.Permission;
import com.thonsokvechey.supermarketbackend.modules.auth.entity.Role;
import com.thonsokvechey.supermarketbackend.modules.auth.repository.PermissionRepository;
import com.thonsokvechey.supermarketbackend.modules.auth.repository.RoleRepository;
import com.thonsokvechey.supermarketbackend.modules.auth.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAllWithPermissions().stream()
                .map(this::toRoleResponse)
                .toList();
    }

    @Override
    @Transactional
    public RoleResponse createRole(CreateRoleRequest request) {
        String name = request.getName().trim();
        if (roleRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Role '" + name + "' already exists");
        }

        Set<Permission> permissions = resolvePermissions(request.getPermissionIds(), request.getPermissionKeys());

        Role role = Role.builder()
                .name(name)
                .description(request.getDescription())
                .permissions(permissions)
                .build();

        Role saved = roleRepository.save(role);
        return toRoleResponse(saved);
    }

    @Override
    @Transactional
    public RoleResponse assignPermissions(UUID roleId, AssignPermissionsRequest request) {
        Role role = roleRepository.findByIdWithPermissions(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));

        Set<Permission> permissions = resolvePermissions(request.getPermissionIds(), request.getPermissionKeys());
        role.setPermissions(permissions);

        Role saved = roleRepository.save(role);
        return toRoleResponse(saved);
    }

    private Set<Permission> resolvePermissions(Set<UUID> permissionIds, Set<String> permissionKeys) {
        Set<Permission> permissions = new HashSet<>();

        if (permissionIds != null && !permissionIds.isEmpty()) {
            permissions.addAll(permissionRepository.findAllById(permissionIds));
        }

        if (permissionKeys != null && !permissionKeys.isEmpty()) {
            Set<String> normalizedKeys = permissionKeys.stream()
                    .filter(k -> k != null && !k.isBlank())
                    .map(String::trim)
                    .collect(Collectors.toSet());

            Set<Permission> existing = permissionRepository.findByPermissionKeyIn(normalizedKeys);
            permissions.addAll(existing);

            Set<String> existingKeys = existing.stream()
                    .map(Permission::getPermissionKey)
                    .collect(Collectors.toSet());

            for (String key : normalizedKeys) {
                if (!existingKeys.contains(key)) {
                    Permission created = permissionRepository.save(
                            Permission.builder()
                                    .permissionKey(key)
                                    .description("Permission for " + key)
                                    .build()
                    );
                    permissions.add(created);
                }
            }
        }

        return permissions;
    }

    private RoleResponse toRoleResponse(Role role) {
        Set<PermissionResponse> permissionDtos = role.getPermissions().stream()
                .map(p -> PermissionResponse.builder()
                        .id(p.getId())
                        .permissionKey(p.getPermissionKey())
                        .description(p.getDescription())
                        .build())
                .collect(Collectors.toSet());

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(permissionDtos)
                .build();
    }
}

