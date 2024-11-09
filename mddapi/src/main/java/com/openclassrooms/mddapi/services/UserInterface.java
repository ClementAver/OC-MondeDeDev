package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostResponse;
import com.openclassrooms.mddapi.dtos.UserRequest;
import com.openclassrooms.mddapi.dtos.UserResponse;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.exceptions.AlreadyExistException;
import com.openclassrooms.mddapi.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Stream;

public interface UserInterface {

    // Register
    void createUser(UserRequest userRequest) throws AlreadyExistException;

    UserResponse getUser(Integer id) throws NotFoundException;

    UserResponse updateUser(Integer id, UserRequest userRequest) throws NotFoundException;

    List<Topic> getUserTopics(Integer id) throws NotFoundException;

    Stream<PostResponse> getUserFeed(Integer id) throws NotFoundException;
}

