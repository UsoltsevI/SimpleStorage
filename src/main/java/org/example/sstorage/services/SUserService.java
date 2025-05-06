package org.example.sstorage.services;

import lombok.RequiredArgsConstructor;
import org.example.sstorage.entities.SRole;
import org.example.sstorage.entities.SUser;
import org.example.sstorage.entities.UserSave;
import org.example.sstorage.repositories.SUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for working with sUser repository.
 *
 * @author UsoltsevI
 */
@RequiredArgsConstructor
@Service
public class SUserService implements UserDetailsService {

    /**
     * SUserRepository to manage user data stored in the database.
     */
    private final SUserRepository sUserRepository;

    /**
     * SFileService to manage files.
     * It is only used to delete files when a user is deleted.
     */
    private final SFileService sFileService;

    /**
     * A password encoder for storing a hash in a database.
     */
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<SUser> userOptional = sUserRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        SUser sUser = userOptional.get();
        return User.withUsername(sUser.getUsername())
                .password(sUser.getPassword())
                .roles(sUser.getRole().getRoleName())
                .build();
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
     * @return true if success, false  if the username has already been used
     */
    public boolean registerNewUser(String username, String password) {
        if (sUserRepository.existsByUsername(username)) {
            return false;
        }
        sUserRepository.save(UserSave.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(SRole.ROLE_USER)
                .build());
        return true;
    }

    /**
     * Register admin user.
     *
     * @param username admin username
     * @param password admin password
     * @return true if successful
     */
    public boolean registerAdmin(String username, String password) {
        if (sUserRepository.existsByUsername(username)) {
            return false;
        }
        sUserRepository.save(UserSave.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(SRole.ROLE_ADMIN)
                .build());
        return true;
    }

    /**
     * Delete user by ID.
     *
     * @param userId user ID
     * @return true if success, false if there is not such user
     */
    public boolean deleteUserById(Long userId) {
        if (sUserRepository.existsById(userId)) {
            sFileService.deleteAllByUserId(userId);
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
    public Page<SUser> getAllUsers(int pageNumber, int pageSize) {
        return sUserRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
