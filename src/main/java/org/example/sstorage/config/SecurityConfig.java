package org.example.sstorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Class for configuring a spring Web Security.
 *
 * @author UsoltsevI
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    /**
     * Bean for configuring securityFilterChain.
     *
     * @param http http request to filter
     * @return securityFilterChain that handles the security-related aspects of http requests and responses.
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
                        .requestMatchers("/files/**", "/user/**", "/error").hasRole("ROLE_USER")
                        .requestMatchers("/register", "/css/**", "/js/**", "/").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .successForwardUrl("/")
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
                        .logoutSuccessUrl("/login?logout"));

        return http.build();
    }

    /**
     * Bean for configuring GrantedAuthority prefix.
     *
     * @return grantedAuthorityDefaults with a customized prefix
     */
    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
