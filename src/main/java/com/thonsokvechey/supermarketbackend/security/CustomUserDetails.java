package com.thonsokvechey.supermarketbackend.security;

import com.thonsokvechey.supermarketbackend.modules.auth.entity.Permission;
import com.thonsokvechey.supermarketbackend.modules.auth.entity.Role;
import com.thonsokvechey.supermarketbackend.modules.auth.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UUID id;
    private final String username;
    private final String email;
    private final String password;
    private final boolean active;
    private final Set<String> roles;
    private final Set<String> permissions;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPasswordHash();
        this.active = user.isActive();

        this.roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        this.permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getPermissionKey)
                .collect(Collectors.toSet());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String role : this.roles) {
            String roleAuthority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
            grantedAuthorities.add(new SimpleGrantedAuthority(roleAuthority));
        }
        for (String perm : this.permissions) {
            grantedAuthorities.add(new SimpleGrantedAuthority(perm));
        }
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}

