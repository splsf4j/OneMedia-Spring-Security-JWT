package org.vladislavb.onemediatesttask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vladislavb.onemediatesttask.entity.Post;
import org.vladislavb.onemediatesttask.service.PostService;

import java.util.List;

@Tag(name = "Posts", description = "Endpoints for managing posts")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Fetch Posts", description = "Fetches posts from an external source and saves them in the database.")
    @ApiResponse(responseCode = "200", description = "Posts fetched and saved successfully")
    @GetMapping("/fetch")
    public List<Post> fetchPosts() {
        return postService.fetchAndSavePosts();
    }

    @Operation(summary = "Get All Posts", description = "Retrieves all posts from the database.")
    @ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
}
