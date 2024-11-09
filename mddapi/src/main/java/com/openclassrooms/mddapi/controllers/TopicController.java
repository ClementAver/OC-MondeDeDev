package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.TopicResponse;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.services.TopicService;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topic")
    public  List<TopicResponse> getTopics() {
        return topicService.getTopics();
    }

    @PostMapping("/topic/{topicId}/subscribe/{userId}")
    public String subscribe(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).")  int topicId,
                                            @PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int userId) throws NotFoundException {
            return topicService.subscribe(topicId, userId);
    }

    @PostMapping("/topic/{topicId}/unsubscribe/{userId}")
    public String unsubscribe(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int topicId,
                                              @PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int userId) throws NotFoundException {
       return topicService.unsubscribe(topicId, userId);

    }
}