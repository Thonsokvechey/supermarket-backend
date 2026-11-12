package com.thonsokvechey.supermarketbackend.modules.auth.repository;

import com.thonsokvechey.supermarketbackend.modules.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""
            SELECT DISTINCT u FROM User u
            LEFT JOIN FETCH u.roles r
            LEFT JOIN FETCH r.permissions
            WHERE LOWER(u.username) = LOWER(:identifier)
               OR LOWER(u.email) = LOWER(:identifier)
            """)
    Optional<User> findByUsernameOrEmailWithRolesAndPermissions(@Param("identifier") String identifier);

    @Query("""
            SELECT DISTINCT u FROM User u
            LEFT JOIN FETCH u.roles r
            LEFT JOIN FETCH r.permissions
            WHERE u.id = :id
            """)
    Optional<User> findByIdWithRolesAndPermissions(@Param("id") UUID id);

    @Query("""
            SELECT DISTINCT u FROM User u
            LEFT JOIN FETCH u.roles r
            LEFT JOIN FETCH r.permissions
            ORDER BY u.createdAt DESC
            """)
    List<User> findAllWithRolesAndPermissions();

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCaseAndIdNot(String username, UUID id);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, UUID id);
}
