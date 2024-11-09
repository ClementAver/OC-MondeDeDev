package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.CommentResponse;
import com.openclassrooms.mddapi.entities.Comment;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CommentResponseMapper implements Function<Comment, CommentResponse> {
    @Override
    public CommentResponse apply(Comment comment) {
        return new CommentResponse(comment.getId(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getUser().getId(), comment.getPost().getId());
    }
}
