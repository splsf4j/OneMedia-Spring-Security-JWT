package org.vladislavb.onemediatesttask.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vladislavb.onemediatesttask.dto.JwtAuthenticationDto;
import org.vladislavb.onemediatesttask.dto.RefreshTokenDto;
import org.vladislavb.onemediatesttask.dto.UserCredentialsDto;
import org.vladislavb.onemediatesttask.dto.UserDto;
import org.vladislavb.onemediatesttask.entity.User;
import org.vladislavb.onemediatesttask.mapper.UserMapper;
import org.vladislavb.onemediatesttask.repository.UserRepository;
import org.vladislavb.onemediatesttask.security.jwt.JwtService;
import org.vladislavb.onemediatesttask.service.UserService;

import javax.naming.AuthenticationException;
import java.util.Optional;

/**
 * Implementation of the {@link UserService} interface.
 * This service provides user authentication, token management, and user retrieval operations.
 *
 *@author Vladislav Baryshev
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user based on provided credentials and generates a JWT authentication token.
     *
     * @param userCredentialsDto User credentials.
     * @return JWT authentication token.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = findByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(user.getEmail());
    }

    /**
     * Refreshes an authentication token using a valid refresh token.
     *
     * @param refreshTokenDto DTO containing the refresh token.
     * @return A new JWT authentication token.
     * @throws Exception if the refresh token is invalid or has been revoked.
     */
    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            if (jwtService.isTokenInvalidated(refreshToken)) {
                throw new AuthenticationException("Refresh token is invalidated");
            }
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new  AuthenticationException("Invalid refresh token");
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The user's ID.
     * @return User DTO.
     * @throws ChangeSetPersister.NotFoundException if the user is not found.
     */
    @Override
    @Transactional
    public UserDto getUserById(Long id) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findByUserId(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email The user's email.
     * @return User DTO.
     * @throws ChangeSetPersister.NotFoundException if the user is not found.
     */
    @Override
    @Transactional
    public UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException {
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }

    /**
     * Adds a new user to the system.
     *
     * @param userDto The user data transfer object.
     * @return A success message.
     */
    @Override
    public String addUser(UserDto userDto){
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User added";
    }

    /**
     * Finds a user by credentials and validates the password.
     *
     * @param userCredentialsDto User credentials.
     * @return The authenticated user entity.
     * @throws AuthenticationException if authentication fails.
     */
    private User findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())){
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }

    /**
     * Finds a user by their email address.
     *
     * @param email The email address.
     * @return The user entity.
     * @throws Exception if the user is not found.
     */
    private User findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(()->
                new Exception(String.format("User with email %s not found", email)));
    }

    /**
     * Logs out a user by invalidating their refresh token.
     *
     * @param refreshTokenDto DTO containing the refresh token.
     */
    @Override
    @Transactional
    public void logout(RefreshTokenDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            jwtService.invalidateToken(refreshToken); // Делаем refresh token недействительным
        }
    }
}
