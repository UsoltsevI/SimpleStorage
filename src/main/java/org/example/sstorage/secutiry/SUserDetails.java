package org.example.sstorage.secutiry;

import org.example.sstorage.entities.SUser;
import org.example.sstorage.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SUserDetails implements UserDetails {
    private SUser sUser;

    public SUserDetails(SUser sUser) {
        this.sUser = sUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserAuthority> authorities = new ArrayList<>();
        switch (sUser.getRole()) {
            case UserRole.ADMIN:
                authorities.add(new UserAuthority(UserRole.ADMIN));
            case UserRole.USER:
                authorities.add(new UserAuthority(UserRole.USER));
            case UserRole.GUEST:
                authorities.add(new UserAuthority(UserRole.GUEST));
                break;
            default:
                throw new RuntimeException("Unrecognized UserRole");
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return sUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sUser.getUsername();
    }
}
