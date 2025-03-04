package org.example.sstorage.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Data
public class SUser implements UserDetails {
    private Long id;

    private String username;

    private String password;

    private SRole role;

    private Instant createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SRole> authorities = new ArrayList<>();
        switch (role) {
            case SRole.ADMIN:
                authorities.add(SRole.ADMIN);
            case SRole.USER:
                authorities.add(SRole.USER);
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
