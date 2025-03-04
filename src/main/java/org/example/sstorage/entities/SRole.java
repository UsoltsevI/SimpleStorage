package org.example.sstorage.entities;

import org.springframework.security.core.GrantedAuthority;

public enum SRole implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
