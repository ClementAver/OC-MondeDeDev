package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostResponse;
import com.openclassrooms.mddapi.dtos.UpdateUserRequest;
import com.openclassrooms.mddapi.dtos.UserRequest;
import com.openclassrooms.mddapi.dtos.UserResponse;
import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exceptions.AlreadyExistException;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.mappers.PostResponseMapper;
import com.openclassrooms.mddapi.mappers.UserResponseMapper;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService implements UserInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;
    private final UserResponseMapper userResponseMapper;
    private final PostResponseMapper postResponseMapper;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PostService postService, UserResponseMapper userResponseMapper, PostResponseMapper postResponseMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.postService = postService;

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
    public UserResponse updateUser(Integer id, UpdateUserRequest userRequest) throws NotFoundException {
        Optional<User> userInDB = userRepository.findById(id);
        if (userInDB.isPresent()) {
            User user = userInDB.get();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());

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
    public Stream<PostResponse> getUserFeed(int id, int limit, int offset) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        List<Integer> topicIds = user.getSubscriptions().stream().map(Topic::getId).toList();

        logger.info("User feed retrieved for user with id: {}, topicIds : {}", id, topicIds);

        List<Post> posts = postService.getFeed(topicIds, limit, offset);

        logger.info("User feed retrieved for user with id: {}, posts : {}", id, posts);

        return posts.stream().map(postResponseMapper);
    }
}
