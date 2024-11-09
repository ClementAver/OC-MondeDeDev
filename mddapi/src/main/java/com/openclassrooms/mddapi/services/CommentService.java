package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentRequest;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements CommentInterface {

private final CommentRepository commentRepository;
private final PostRepository postRepository;
private final UserRepository userRepository;


    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;

        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Comment> getCommentByPostId(Integer id) throws NotFoundException {
        postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article non référencé."));

        return commentRepository.findByPostId(id);
    }

    @Override
    public Comment createComment(CommentRequest commentRequest) throws NotFoundException {
        User user = userRepository.findById(commentRequest.getUser())
                .orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));
        Post post = postRepository.findById(commentRequest.getPost())
                .orElseThrow(() -> new NotFoundException("Article non référencé."));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(commentRequest.getContent());

        return commentRepository.save(comment);
    }
}
