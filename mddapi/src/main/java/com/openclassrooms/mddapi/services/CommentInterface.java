package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentRequest;
import com.openclassrooms.mddapi.dtos.CommentResponse;
import com.openclassrooms.mddapi.exceptions.NotFoundException;

import java.util.stream.Stream;

public interface CommentInterface {
    Stream<CommentResponse> getCommentByPostId(Integer id) throws NotFoundException;

    CommentResponse createComment(CommentRequest commentRequest) throws NotFoundException;
}
