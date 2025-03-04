package org.example.sstorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class for configuring the password encoder.
 *
 * @author UsoltsevI
 */
@Configuration
public class PasswordConfig {

    /**
     * The bean for getting the password encoder.
     * It is used to hash the password before writing to the database.
     *
     * @return password encoder
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
