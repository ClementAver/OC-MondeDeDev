package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.PostResponse;
import com.openclassrooms.mddapi.entities.Post;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PostResponseMapper implements Function<Post, PostResponse> {
    @Override
    public PostResponse apply(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt(), post.getTopic().getId(), post.getUser().getId());
    }
}
