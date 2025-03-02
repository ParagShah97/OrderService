package com.parag.OrderService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Configuration class for Spring Security.
 * This class defines security settings, including authentication and authorization.
 */
@Configuration // Marks this class as a configuration class for Spring
@EnableWebSecurity // Enables web security features in Spring Security
//@EnableMethodSecurity // Replaces the deprecated @EnableGlobalMethodSecurity, enables method-level security annotations like @PreAuthorize, @PostAuthorize
public class WebSecurityConfig {

    /**
     * Configures security settings using a SecurityFilterChain.
     *
     * @param http HttpSecurity object to define security rules
     * @return Configured SecurityFilterChain
     * @throws Exception if any security configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configures authorization rules for incoming HTTP requests
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest() // Applies to any request
                                .authenticated()) // Requires authentication for all requests
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));   // Configures OAuth2 Resource Server to use JWT-based authentication

        // Builds and returns the security filter chain
        return http.build();
    }
}
