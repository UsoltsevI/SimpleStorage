package org.example.sstorage.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The user's identity for accessing and saving data in the database.
 *
 * @author UsoltsevI
 */
@Builder
@Data
public class SUser implements UserDetails {
    /**
     * Generated by database.
     */
    @NonNull
    private Long id;

    /**
     * Unique.
     */
    @NonNull
    private String username;

    /**
     * Password hash.
     * Required but may be not loaded from the database.
     */
    @Nullable
    private String password;

    /**
     * User role.
     */
    @NonNull
    private SRole role;

    /**
     * Creation timestamp.
     * Required but may be not loaded from the database.
     */
    @Nullable
    private Instant createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SRole> authorities = new ArrayList<>();
        switch (role) {
            case SRole.ROLE_ADMIN:
                authorities.add(SRole.ROLE_ADMIN);
            case SRole.ROLE_USER:
                authorities.add(SRole.ROLE_USER);
                break;
            default:
                throw new RuntimeException("Unrecognized UserRole");
        }
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
}
