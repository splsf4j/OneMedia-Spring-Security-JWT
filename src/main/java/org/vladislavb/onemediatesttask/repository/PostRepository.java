package org.vladislavb.onemediatesttask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vladislavb.onemediatesttask.entity.Post;

/**
 * PostRepository is a Spring Data JPA repository interface for performing CRUD operations
 * on the "Post" entity. This interface extends JpaRepository, which provides built-in methods
 * for handling database operations such as save, find, delete, etc.
 *
 * @author Vladislav Baryshev
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
