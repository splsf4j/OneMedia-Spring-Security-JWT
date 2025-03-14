package org.vladislavb.onemediatesttask.service;


import org.springframework.data.crossstore.ChangeSetPersister;
import org.vladislavb.onemediatesttask.dto.JwtAuthenticationDto;
import org.vladislavb.onemediatesttask.dto.RefreshTokenDto;
import org.vladislavb.onemediatesttask.dto.UserCredentialsDto;
import org.vladislavb.onemediatesttask.dto.UserDto;

import javax.naming.AuthenticationException;

/**
 * UserService is an interface that defines the contract for user-related operations,
 * including signing in, refreshing tokens, fetching user details, adding a new user, and logging out.
 *
 * @author  Vladislav Baryshev
 */
public interface UserService {

    /**
     * Signs in a user with the provided credentials and generates JWT authentication tokens.
     *
     * @param userCredentialsDto the user credentials used for authentication.
     * @return a {@link JwtAuthenticationDto} containing the authentication token and refresh token.
     * @throws AuthenticationException if authentication fails.
     */
    JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;

    /**
     * Refreshes the authentication token using a valid refresh token.
     *
     * @param refreshTokenDto the refresh token used to generate a new authentication token.
     * @return a {@link JwtAuthenticationDto} containing the new authentication token and the same refresh token.
     * @throws Exception if an error occurs during the refresh process.
     */
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

    /**
     * Retrieves user details by the user's ID.
     *
     * @param id the ID of the user.
     * @return a {@link UserDto} containing the user's information.
     * @throws ChangeSetPersister.NotFoundException if the user with the given ID is not found.
     */
    UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException;

    /**
     * Retrieves user details by the user's email.
     *
     * @param email the email of the user.
     * @return a {@link UserDto} containing the user's information.
     * @throws ChangeSetPersister.NotFoundException if the user with the given email is not found.
     */
    UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException;

    /**
     * Adds a new user to the system.
     *
     * @param user the user data to be added.
     * @return a confirmation message or some identifier for the newly added user.
     */
    String addUser(UserDto user);

    /**
     * Logs out a user by invalidating their refresh token.
     *
     * @param refreshTokenDto the refresh token to be invalidated.
     */
    void logout(RefreshTokenDto refreshTokenDto);
}
