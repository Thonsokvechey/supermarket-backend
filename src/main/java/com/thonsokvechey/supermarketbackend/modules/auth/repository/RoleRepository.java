package com.thonsokvechey.supermarketbackend.modules.auth.repository;

import com.thonsokvechey.supermarketbackend.modules.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    @Query("""
            SELECT DISTINCT r FROM Role r
            LEFT JOIN FETCH r.permissions
            ORDER BY r.name ASC
            """)
    List<Role> findAllWithPermissions();

    @Query("""
            SELECT DISTINCT r FROM Role r
            LEFT JOIN FETCH r.permissions
            WHERE r.id = :id
            """)
    Optional<Role> findByIdWithPermissions(@Param("id") UUID id);

    boolean existsByNameIgnoreCase(String name);

    Set<Role> findByNameIn(Collection<String> names);
}

