package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.TopicResponse;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.services.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TopicResponse>> getTopics() {
        List<TopicResponse> topics = topicService.getTopics();
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @PostMapping("/topic/{topicId}/subscribe/{userId}")
    public ResponseEntity<String> subscribe(@PathVariable Integer topicId, @PathVariable Integer userId) {
        try {
            String response = topicService.subscribe(topicId, userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/topic/{topicId}/unsubscribe/{userId}")
    public ResponseEntity<String> unsubscribe(@PathVariable Integer topicId, @PathVariable Integer userId) {
        try {
            String response = topicService.unsubscribe(topicId, userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}