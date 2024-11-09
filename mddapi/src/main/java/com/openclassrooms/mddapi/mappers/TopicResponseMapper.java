package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.TopicResponse;
import com.openclassrooms.mddapi.entities.Topic;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TopicResponseMapper implements Function<Topic, TopicResponse> {
    @Override
    public TopicResponse apply(Topic topic) {
        return new TopicResponse(topic.getId(), topic.getTitle(), topic.getDescription(), topic.getCreatedAt(), topic.getUpdatedAt());
    }
}
