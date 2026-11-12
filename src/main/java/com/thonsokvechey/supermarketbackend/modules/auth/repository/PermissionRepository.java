package com.thonsokvechey.supermarketbackend.modules.auth.repository;

import com.thonsokvechey.supermarketbackend.modules.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    Set<Permission> findByPermissionKeyIn(Collection<String> permissionKeys);

    boolean existsByPermissionKeyIgnoreCase(String permissionKey);
}

