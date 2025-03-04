package org.example.sstorage.entities;

import lombok.Builder;
import lombok.Data;

/**
 * Entity to save user data.
 * Used in the save method in SUserRepository.
 *
 * @author UsoltsevI
 */
@Builder
@Data
public class UserSave {
    /**
     * Username.
     */
    private String username;

    /**
     * Password hash.
     */
    private String password;

    /**
     * User role.
     */
    private SRole role;
}
