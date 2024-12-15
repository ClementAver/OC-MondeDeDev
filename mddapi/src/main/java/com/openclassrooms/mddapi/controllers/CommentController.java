package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentRequest;
import com.openclassrooms.mddapi.dtos.CommentResponse;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.services.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Get all comments for a given post
     * @param id The post identifier
     * @return All comments for this post
     * @throws NotFoundException If the post is not found
     */
    @GetMapping("/comment/post/{id}")
    public Stream<CommentResponse> getCommentsByPostId(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id) throws NotFoundException {
       return commentService.getCommentByPostId(id);
    }

    /**
     * Create a new comment
     * @param commentRequest The comment to create
     * @return The created comment
     * @throws NotFoundException If the post is not found
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/comment")
    public CommentResponse createComment(@Valid @RequestBody CommentRequest commentRequest) throws NotFoundException {
        return commentService.createComment(commentRequest);
    }
}
