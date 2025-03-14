package org.vladislavb.onemediatesttask.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.vladislavb.onemediatesttask.security.jwt.JwtFilter;

/**
 * SecurityConfig is a configuration class for Spring Security.
 * It configures the security settings for the application, including HTTP security, password encoding,
 * and JWT authentication filters.
 *
 * This configuration disables HTTP basic authentication, CSRF protection, and sets the session management policy
 * to stateless, meaning the application does not store session information.
 * It also adds a JWT filter before the {@link UsernamePasswordAuthenticationFilter} to validate JWT tokens for requests.
 *
 * @author Vladislav Baryshev
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    /**
     * Configures the HTTP security for the application.
     *
     * This method disables HTTP basic authentication, CSRF protection, and sets up URL-based access rules.
     * It allows unauthenticated access to the user registration and authentication endpoints and requires authentication
     * for all other endpoints.
     *
     * The session creation policy is set to stateless to ensure that no session is created or used.
     * The method also adds the {@link JwtFilter} before the {@link UsernamePasswordAuthenticationFilter} to validate
     * JWT tokens in incoming requests.
     *
     * @param http the {@link HttpSecurity} object used to configure the security settings.
     * @return a configured {@link SecurityFilterChain} object.
     * @throws Exception if an error occurs while configuring HTTP security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers
                                (
                                        "/user/registration",
                                        "/auth/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()

                        .requestMatchers("/**").authenticated())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Provides a {@link PasswordEncoder} bean that uses BCrypt hashing algorithm for password encoding.
     *
     * @return a {@link BCryptPasswordEncoder} with strength 4.
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }
}
