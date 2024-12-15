package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.TopicResponse;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.services.TopicService;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TopicController {

    private final TopicService topicService;


    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Get all topics
     * @return All topics
     */
    @GetMapping("/topic")
    public  List<TopicResponse> getTopics() {
        return topicService.getTopics();
    }

    /**
     * Get a topic by its identifier
     * @param topicId The topic identifier
     * @return The topic
     * @throws NotFoundException If the topic is not found
     */
    @GetMapping("/topic/{topicId}")
    public TopicResponse getTopic(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int topicId) throws NotFoundException {
        return topicService.getTopic(topicId);
    }

    /**
     * Subscribe to a topic
     * @param topicId The topic identifier
     * @param userId The user identifier
     * @return A subscription message
     * @throws NotFoundException If the topic or the user is not found
     */
    @PostMapping("/topic/{topicId}/subscribe/{userId}")
    public String subscribe(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).")  int topicId,
                                            @PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int userId) throws NotFoundException {
            return topicService.subscribe(topicId, userId);
    }

    /**
     * Unsubscribe from a topic
     * @param topicId The topic identifier
     * @param userId The user identifier
     * @return An unsubscription message
     * @throws NotFoundException If the topic or the user is not found
     */
    @PostMapping("/topic/{topicId}/unsubscribe/{userId}")
    public String unsubscribe(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int topicId,
                                              @PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int userId) throws NotFoundException {
       return topicService.unsubscribe(topicId, userId);

    }
}