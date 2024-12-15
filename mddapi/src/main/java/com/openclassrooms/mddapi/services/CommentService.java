package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentRequest;
import com.openclassrooms.mddapi.dtos.CommentResponse;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.mappers.CommentResponseMapper;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CommentService implements CommentInterface {

private final CommentRepository commentRepository;
private final PostRepository postRepository;
private final UserRepository userRepository;
private final CommentResponseMapper commentResponseMapper;


    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, com.openclassrooms.mddapi.mappers.CommentResponseMapper commentResponseMapper) {
        this.commentRepository = commentRepository;

        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentResponseMapper = commentResponseMapper;
    }

    /**
     * Get all comments for a given post
     * @param id The post identifier
     * @return All comments for this post
     * @throws NotFoundException If the post is not found
     */
    @Override
    public Stream<CommentResponse> getCommentByPostId(Integer id) throws NotFoundException {
        postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article non référencé."));

        return commentRepository.findByPostId(id).stream().map(commentResponseMapper);
    }

    /**
     * Create a new comment
     * @param commentRequest The comment to create
     * @return The created comment
     * @throws NotFoundException If the post is not found
     */
    @Override
    public CommentResponse createComment(CommentRequest commentRequest) throws NotFoundException {
        User user = userRepository.findById(commentRequest.getUser())
                .orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));
        Post post = postRepository.findById(commentRequest.getPost())
                .orElseThrow(() -> new NotFoundException("Article non référencé."));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(commentRequest.getContent());

        commentRepository.save(comment);

        return commentResponseMapper.apply(comment);
    }
}
