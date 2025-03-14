package org.vladislavb.onemediatesttask.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for user details.
 * This class contains information about a user, including identification and credentials.
 *
 * <p>It utilizes Lombok's {@code @Data} annotation to generate boilerplate code such as getters and setters.</p>
 *
 * @author Vladislav Baryshev
 */
@Data
public class UserDto {

    /**
     * The unique identifier of the user.
     */
    String userId;

    /**
     * The first name of the user.
     */
    String firstName;

    /**
     * The last name of the user.
     */
    String lastName;

    /**
     * The email address of the user.
     */
    String email;

    /**
     * The password associated with the user account.
     */
    String password;
}
