package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentRequest;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.exceptions.NotFoundException;

import java.util.List;

public interface CommentInterface {
    List<Comment> getCommentByPostId(Integer id) throws NotFoundException;

    Comment createComment(CommentRequest commentRequest) throws NotFoundException;
}
