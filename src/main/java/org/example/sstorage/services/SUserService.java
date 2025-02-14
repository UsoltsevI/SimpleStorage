package org.example.sstorage.services;

import lombok.RequiredArgsConstructor;
import org.example.sstorage.entities.SUser;
import org.example.sstorage.repositories.SUserRepository;
import org.example.sstorage.secutiry.SUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SUserService implements UserDetailsService {
    private final SUserRepository SUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<SUser> userOptional = SUserRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new SUserDetails(userOptional.get());
    }
}
