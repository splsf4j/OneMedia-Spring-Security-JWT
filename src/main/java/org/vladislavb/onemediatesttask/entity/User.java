package org.vladislavb.onemediatesttask.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents a user entity in the system.
 * This class is mapped to the "users" table in the database and contains
 * information about a user such as their ID, first name, last name, email, and password.
 * It uses JPA annotations to define the persistence behavior and Lombok annotations for getter, setter,
 * and constructor generation.
 *
 * @author Vladislav Baryshev
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    /**
     * The unique identifier of the user. This field is mapped to the "user_id" column in the database.
     * It is generated automatically by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * The first name of the user. This field is mapped to the "first_name" column in the database.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * The last name of the user. This field is mapped to the "last_name" column in the database.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * The email address of the user. This field is mapped to the "email" column in the database.
     */
    @Column(name = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * The password of the user. This field is mapped to the "password" column in the database.
     */
    @Column(name = "password", nullable = false)
//    @NotBlank(message = "Password is required")
//    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
//    @Pattern(
//            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
//            message = "Password must contain at least one uppercase letter, one lowercase letter, " +
//                    "one digit, and one special character"
//    )
    private String password;

    /**
     * Compares this user object with another object for equality.
     * Two user objects are considered equal if they have the same user ID and email.
     *
     * @param o the object to compare this user to.
     * @return true if the two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(email, user.email);
    }

    /**
     * Returns a hash code value for the user object.
     * The hash code is generated based on the user ID and email.
     *
     * @return the hash code value for this user object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, email);
    }

    /**
     * Returns a string representation of the user object.
     * The string includes the user ID, first name, last name, email, and password.
     *
     * @return a string representation of the user object.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
