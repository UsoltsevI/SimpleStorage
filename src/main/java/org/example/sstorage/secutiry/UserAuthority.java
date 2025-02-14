package org.example.sstorage.secutiry;

import org.example.sstorage.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {
    private UserRole role;

    public UserAuthority(UserRole role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }
}
