package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostRequest;
import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements PostInterface {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Post createPost(PostRequest postRequest) throws NotFoundException {
        Topic topic = topicRepository.findById(postRequest.getTopic())
                .orElseThrow(() -> new NotFoundException("Thème non référencé."));
        User user = userRepository.findById(postRequest.getUser())
                .orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setTopic(topic);
        post.setUser(user);

        return postRepository.save(post);
    }


    @Override
    public Post getPost(Integer id) throws NotFoundException {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article non référencé."));
    }
}
