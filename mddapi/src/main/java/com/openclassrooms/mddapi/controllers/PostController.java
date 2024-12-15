package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.PostRequest;
import com.openclassrooms.mddapi.dtos.PostResponse;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Create a new post
     * @param postRequest The post to create
     * @return The created post
     * @throws NotFoundException If the post is not found
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/post")
    public PostResponse createPost(@Valid @RequestBody PostRequest postRequest) throws NotFoundException {
        return postService.createPost(postRequest);
    }

    /**
     * Get a post by its identifier
     * @param id The post identifier
     * @return The post
     * @throws NotFoundException If the post is not found
     */
    @GetMapping("/post/{id}")
    public PostResponse getPost(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id) throws NotFoundException {
            return postService.getPost(id);
    }
}
