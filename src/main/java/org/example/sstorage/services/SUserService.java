package org.example.sstorage.services;

import lombok.RequiredArgsConstructor;
import org.example.sstorage.entities.SRole;
import org.example.sstorage.entities.SUser;
import org.example.sstorage.repositories.SUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SUserService implements UserDetailsService {
    private final SUserRepository sUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<SUser> userOptional = sUserRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return userOptional.get();
    }

    public Optional<SUser> findUserById(Long id) {
        return sUserRepository.findById(id);
    }

    public boolean registerNewUser(String username, String password) {
        if (sUserRepository.findByUsername(username).isPresent()) {
            return false;
        }
        String passwordHash = passwordEncoder.encode(password);
        sUserRepository.save(SUser.builder()
                .username(username)
                .password(passwordHash)
                .role(SRole.USER)
                .build());
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (sUserRepository.findById(userId).isPresent()) {
            sUserRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<SUser> getAllUsers() {
        return sUserRepository.findAll();
    }
}
