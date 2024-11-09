package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostRequest;
import com.openclassrooms.mddapi.dtos.PostResponse;
import com.openclassrooms.mddapi.exceptions.NotFoundException;

public interface PostInterface {

    PostResponse createPost(PostRequest postRequest) throws NotFoundException;

    PostResponse getPost(Integer id) throws NotFoundException;
}
