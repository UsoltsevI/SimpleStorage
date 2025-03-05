package org.example.sstorage.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
    @NonNull
    private String username;

    /**
     * Password hash.
     */
    @NonNull
    private String password;

    /**
     * User role.
     */
    @NonNull
    private SRole role;
}
