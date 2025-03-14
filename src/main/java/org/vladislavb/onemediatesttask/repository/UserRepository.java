package org.vladislavb.onemediatesttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vladislavb.onemediatesttask.entity.User;

import java.util.Optional;

/**
 * UserRepository is a Spring Data JPA repository interface for performing CRUD operations
 * on the "User" entity. It extends JpaRepository, providing built-in methods for handling
 * database operations such as save, find, delete, etc.
 *
 * This repository also includes custom query methods for finding a user by their user ID or email.
 *
 * @author Vladislav Baryshev
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their unique user ID.
     *
     * @param id the ID of the user.
     * @return an Optional containing the user if found, or an empty Optional if no user is found.
     */
    Optional<User> findByUserId(Long id);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user.
     * @return an Optional containing the user if found, or an empty Optional if no user is found.
     */
    Optional<User> findByEmail(String email);
}
