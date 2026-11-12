package com.thonsokvechey.supermarketbackend.modules.auth.service;

import com.thonsokvechey.supermarketbackend.modules.auth.dto.AssignPermissionsRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.CreateRoleRequest;
import com.thonsokvechey.supermarketbackend.modules.auth.dto.RoleResponse;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    List<RoleResponse> getAllRoles();

    RoleResponse createRole(CreateRoleRequest request);

    RoleResponse assignPermissions(UUID roleId, AssignPermissionsRequest request);
}

