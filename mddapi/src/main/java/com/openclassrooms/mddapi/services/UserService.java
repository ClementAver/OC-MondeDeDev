package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostResponse;
import com.openclassrooms.mddapi.dtos.UserRequest;
import com.openclassrooms.mddapi.dtos.UserResponse;
import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exceptions.AlreadyExistException;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.mappers.PostResponseMapper;
import com.openclassrooms.mddapi.mappers.UserResponseMapper;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService implements UserInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final UserResponseMapper userResponseMapper;
    private final PostResponseMapper postResponseMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PostRepository postRepository, UserResponseMapper userResponseMapper, PostResponseMapper postResponseMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;
        this.userResponseMapper = userResponseMapper;
        this.postResponseMapper = postResponseMapper;
    }

    @Override
    public UserResponse getUser(Integer id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));
        return userResponseMapper.apply(user);
    }

    // Register
    @Override
    public void createUser(UserRequest userRequest) throws AlreadyExistException {
        Optional<User> userInDB = userRepository.findByEmail(userRequest.getEmail());
        if (userInDB.isPresent()) {
            throw new AlreadyExistException("Cet email a déjà été renseigné.");
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(user);

        new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

    @Override
    public UserResponse updateUser(Integer id, UserRequest userRequest) throws NotFoundException {
        Optional<User> userInDB = userRepository.findById(id);
        if (userInDB.isPresent()) {
            User user = userInDB.get();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }

            userRepository.save(user);
            return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
        } else {
            throw new NotFoundException("Utilisateur non référencé.");
        }
    }

    @Override
    public List<Topic> getUserTopics(Integer id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));
        return user.getSubscriptions();
    }

    @Override
    public Stream<PostResponse> getUserFeed(Integer id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        List<Topic> topics = user.getSubscriptions();
        List<Post> feed = new ArrayList<>();

        for (Topic topic : topics) {
            List<Post> posts = postRepository.findTop10ByTopicOrderByCreatedAtDesc(topic);
            feed.addAll(posts);
        }

        feed.sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));

        return feed.stream().map(postResponseMapper).limit(25);

    }
}
