package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.PostRequest;
import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/post")
    public Post createPost(@RequestBody PostRequest postRequest) throws NotFoundException {
        return postService.createPost(postRequest);

    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Integer id) {
        try {
            Post post = postService.getPost(id);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
