package org.example.sstorage.entities;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

/**
 * User roles.
 *
 * @author UsoltsevI
 */
public enum SRole implements GrantedAuthority {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    @Getter
    private String roleName;

    private SRole(String roleName) {
        this.roleName = roleName;
    }

    public static SRole fromString(String role) {
        return switch (role) {
            case "ADMIN" -> ROLE_ADMIN;
            case "USER" -> ROLE_USER;
            default -> throw new RuntimeException("Unrecognized role: '" + role + "'");
        };
    }

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
