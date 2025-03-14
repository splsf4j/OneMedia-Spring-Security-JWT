package org.vladislavb.onemediatesttask.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for user credentials.
 * This class holds the user's email and password for authentication purposes.
 *
 * <p>It utilizes Lombok's {@code @Data} annotation to generate boilerplate code such as getters and setters.</p>
 *
 * @author Vladislav Baryshev
 */
@Data
public class UserCredentialsDto {

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password associated with the user account.
     */
    private String password;
}
