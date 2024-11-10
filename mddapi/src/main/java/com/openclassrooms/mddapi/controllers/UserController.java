package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.*;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.mappers.TopicResponseMapper;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final TopicResponseMapper topicResponseMapper;

    public UserController(UserService userService, TopicResponseMapper topicResponseMapper) {
        this.userService = userService;
        this.topicResponseMapper = topicResponseMapper;
    }

    @GetMapping("/user/{id}")
    public UserResponse getUser(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id) throws NotFoundException {
        return userService.getUser(id);
    }

    @PutMapping("/user/{id}")
    public UserResponse updateUser(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id, @Valid @RequestBody UserRequest userRequest) throws NotFoundException {
            return userService.updateUser(id, userRequest);
    }

    @GetMapping("/user/{id}/topics")
    public Stream<TopicResponse> getUserTopics(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id) throws NotFoundException {
            List<Topic> topics = userService.getUserTopics(id);

            return topics.stream().map(topicResponseMapper);
    }

    @GetMapping("/user/{id}/feed")
    public Stream<PostResponse> getUserFeed(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id,
                                            @Valid @RequestBody FeedRequest feedRequest) throws NotFoundException {
            return userService.getUserFeed(id, feedRequest.getLimit(), feedRequest.getOffset());
    }
}