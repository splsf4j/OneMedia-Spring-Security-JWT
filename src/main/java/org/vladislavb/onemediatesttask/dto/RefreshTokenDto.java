package org.vladislavb.onemediatesttask.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for handling refresh tokens.
 * This class contains the refresh token used to obtain a new access token.
 *
 * <p>It utilizes Lombok's {@code @Data} annotation to generate boilerplate code such as getters and setters.</p>
 *
 * @author Vladislav Baryshev
 */
@Data
public class RefreshTokenDto {

    /**
     * The refresh token used to obtain a new access token.
     */
    private String refreshToken;
}
