package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.TopicResponse;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.mappers.TopicResponseMapper;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService implements TopicInterface {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final TopicResponseMapper topicResponseMapper;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository, TopicResponseMapper topicResponseMapper) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.topicResponseMapper = topicResponseMapper;
    }

    @Override
    public List<TopicResponse> getTopics() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(topicResponseMapper).collect(Collectors.toList());
    }

    @Override
    public String subscribe(Integer topicId, Integer userId) throws NotFoundException {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException("Thème non référencé."));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        user.getSubscriptions().add(topic);
        userRepository.save(user);

        return "Abonnement souscrit avec succès.";
    }

    @Override
    public String unsubscribe(Integer topicId, Integer userId) throws NotFoundException {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException("Thème non référencé."));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        user.getSubscriptions().remove(topic);
        userRepository.save(user);

        return "L'abonnement a été résilié avec succès.";
    }
}