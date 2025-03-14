package org.vladislavb.onemediatesttask.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vladislavb.onemediatesttask.entity.Post;
import org.vladislavb.onemediatesttask.repository.PostRepository;

import java.util.Arrays;
import java.util.List;

/**
 * PostService is a service class that handles operations related to posts.
 * It fetches posts from an external API and saves them to the database,
 * as well as retrieves all posts from the database.
 *
 * @author  Vladislav Baryshev
 */
@Service
public class PostService {
    private final PostRepository postRepository;
    private final RestTemplate restTemplate;

    /**
     * Constructs a PostService with the given PostRepository and RestTemplate.
     *
     * @param postRepository the repository used to interact with the post database.
     * @param restTemplate   the RestTemplate used for making HTTP requests.
     */
    public PostService(PostRepository postRepository, RestTemplate restTemplate) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches posts from an external API and saves them to the database.
     * The posts are fetched from the URL "https://jsonplaceholder.typicode.com/posts".
     *
     * @return a list of saved {@link Post} objects after they are fetched and saved to the database.
     */
    public List<Post> fetchAndSavePosts() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        Post[] posts = restTemplate.getForObject(url, Post[].class);
        return postRepository.saveAll(Arrays.asList(posts));
    }

    /**
     * Retrieves all posts from the database.
     *
     * @return a list of all {@link Post} objects stored in the database.
     */
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
