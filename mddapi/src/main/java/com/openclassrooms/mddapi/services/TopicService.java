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

    /**
     * Get all topics
     * @return All topics
     */
    @Override
    public List<TopicResponse> getTopics() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(topicResponseMapper).collect(Collectors.toList());
    }

    /**
     * Get a topic by its identifier
     * @param topicId The topic identifier
     * @return The topic
     * @throws NotFoundException If the topic is not found
     */
    @Override
    public TopicResponse getTopic(Integer topicId) throws NotFoundException {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("Thème non référencé."));
        return topicResponseMapper.apply(topic);
    }

    /**
     * Subscribe to a topic
     * @param topicId The topic identifier
     * @param userId The user identifier
     * @return A subscription message
     * @throws NotFoundException If the topic or the user is not found
     */
    @Override
    public String subscribe(Integer topicId, Integer userId) throws NotFoundException {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException("Thème non référencé."));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        user.getSubscriptions().add(topic);
        userRepository.save(user);

        return "Abonnement souscrit avec succès.";
    }

    /**
     * Unsubscribe from a topic
     * @param topicId The topic identifier
     * @param userId The user identifier
     * @return An unsubscription message
     * @throws NotFoundException If the topic or the user is not found
     */
    @Override
    public String unsubscribe(Integer topicId, Integer userId) throws NotFoundException {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException("Thème non référencé."));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        user.getSubscriptions().remove(topic);
        userRepository.save(user);

        return "L'abonnement a été résilié avec succès.";
    }
}