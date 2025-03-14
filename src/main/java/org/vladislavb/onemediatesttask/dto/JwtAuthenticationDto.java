package org.vladislavb.onemediatesttask.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for JWT authentication tokens.
 * This class holds the access token and refresh token used for authentication.
 *
 * <p>It uses Lombok's {@code @Data} annotation to generate boilerplate code such as getters, setters, and constructors.</p>
 *
 * @author Vladislav Baryshev
 */
@Data
public class JwtAuthenticationDto {

    /**
     * The JWT access token used for authentication.
     */
    private String token;

    /**
     * The refresh token used to obtain a new access token when the current one expires.
     */
    private String refreshToken;
}
