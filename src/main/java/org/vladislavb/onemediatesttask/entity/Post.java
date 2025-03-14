package org.vladislavb.onemediatesttask.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Entity class representing a post in the system.
 * This class is mapped to the "posts" table in the database.
 *
 * <p>It contains fields for the post ID, user ID, title, and body.</p>
 *
 * @author Vladislav Baryshev
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@RequiredArgsConstructor
public class Post {

    /**
     * Unique identifier for the post.
     */
    @Id
    private Long id;

    /**
     * Identifier of the user who created the post.
     */
    private Long userId;

    /**
     * Title of the post.
     */
    private String title;


    /**
     * Content of the post stored as text.
     */
    @Column(columnDefinition = "TEXT")
    private String body;

    /**
     * Compares this post with another object for equality.
     *
     * @param o the object to compare to
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(userId, post.userId) && Objects.equals(title, post.title) && Objects.equals(body, post.body);
    }

    /**
     * Generates a hash code for this post.
     *
     * @return the hash code value for this post
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, body);
    }

    /**
     * Returns a string representation of this post.
     *
     * @return a string containing the post details
     */
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
