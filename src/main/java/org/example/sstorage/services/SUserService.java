package org.example.sstorage.services;

import lombok.RequiredArgsConstructor;
import org.example.sstorage.entities.SRole;
import org.example.sstorage.entities.SUser;
import org.example.sstorage.entities.UserSave;
import org.example.sstorage.repositories.SUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for working with user repository.
 *
 * @author UsoltsevI
 */
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

    /**
     * Check if there is a user with that ID.
     *
     * @param id ID to check
     * @return true if exists
     */
    public boolean existsById(Long id) {
        return sUserRepository.existsById(id);
    }

    /**
     * Check if there is a user with that username.
     *
     * @param username username to check
     * @return true if exists
     */
    public boolean existsByUsername(String username) {
        return sUserRepository.existsByUsername(username);
    }

    /**
     * Find user by ID.
     *
     * @param id user ID
     * @return user if found
     */
    public Optional<SUser> findUserById(Long id) {
        return sUserRepository.findById(id);
    }

    /**
     * Register new user and save it to the database.
     *
     * @param username username
     * @param password password in its purest form
     * @return true if success
     */
    public boolean registerNewUser(String username, String password) {
        if (sUserRepository.findByUsername(username).isPresent()) {
            return false;
        }
        sUserRepository.save(UserSave.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(SRole.USER)
                .build());
        return true;
    }

    /**
     * Delete user by ID.
     *
     * @param userId user ID
     * @return true if success
     */
    public boolean deleteUserById(Long userId) {
        if (sUserRepository.findById(userId).isPresent()) {
            sUserRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    /**
     * Get list of all users.
     * Do not fill in the password field.
     *
     * @return list of found users
     */
    public List<SUser> getAllUsers() {
        return sUserRepository.findAll();
    }
}
