package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostRequest;
import com.openclassrooms.mddapi.dtos.PostResponse;
import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.exceptions.NotFoundException;

import java.util.List;

public interface PostInterface {
    PostResponse createPost(PostRequest postRequest) throws NotFoundException;

    PostResponse getPost(Integer id) throws NotFoundException;

    int getFeedSize(List<Integer> topicIds);

    List<Post> getFeed(List<Integer> topicIds, int limit, int offset, boolean sort);
}
