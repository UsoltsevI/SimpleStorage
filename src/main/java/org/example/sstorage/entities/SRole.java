package org.example.sstorage.entities;

import org.springframework.security.core.GrantedAuthority;

/**
 * User roles.
 *
 * @author UsoltsevI
 */
public enum SRole implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
