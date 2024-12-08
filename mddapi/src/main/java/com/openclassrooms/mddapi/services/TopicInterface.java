package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.TopicResponse;
import com.openclassrooms.mddapi.exceptions.NotFoundException;

import java.util.List;

public interface TopicInterface {
    List<TopicResponse> getTopics();

    TopicResponse getTopic(Integer topicId) throws NotFoundException;

    String subscribe(Integer topicId, Integer userId) throws NotFoundException;

    String unsubscribe(Integer topicId, Integer userId) throws NotFoundException;
}
