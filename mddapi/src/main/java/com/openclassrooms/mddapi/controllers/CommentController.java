package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentRequest;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comment/post/{id}")
    public  List<Comment> getCommentsByPostId(@PathVariable Integer id) throws NotFoundException {
       return commentService.getCommentByPostId(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/comment")
    public Comment createComment(@RequestBody CommentRequest commentRequest) throws NotFoundException {
        return commentService.createComment(commentRequest);
    }
}
