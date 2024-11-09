package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostRequest;
import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.exceptions.NotFoundException;

public interface PostInterface {

    Post createPost(PostRequest postRequest) throws NotFoundException;

    Post getPost(Integer id) throws NotFoundException;
}
