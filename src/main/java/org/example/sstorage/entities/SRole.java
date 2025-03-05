package org.example.sstorage.entities;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

/**
 * User roles.
 *
 * @author UsoltsevI
 */
public enum SRole implements GrantedAuthority {

    /**
     * Administrator role
     */
    ROLE_ADMIN("ADMIN"),

    /**
     * Registrated user role.
     */
    ROLE_USER("USER");

    /**
     * Role name to deal with GrantedAuthority calls.
     */
    @Getter
    private String roleName;

    /**
     * SRole private constructor to initialize roleName.
     *
     * @param roleName role name
     */
    private SRole(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Initialize SRole from the given string.
     * If the role is not recognized, a RuntimeException is thrown.
     *
     * @param roleName role as string. Example: "USER"
     * @return the appropriate role
     */
    public static SRole fromString(String roleName) {
        return switch (roleName) {
            case "ADMIN" -> ROLE_ADMIN;
            case "USER" -> ROLE_USER;
            default -> throw new RuntimeException("Unrecognized roleName: '" + roleName + "'");
        };
    }

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
