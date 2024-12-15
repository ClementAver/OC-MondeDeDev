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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService implements UserInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;
    private final UserResponseMapper userResponseMapper;
    private final PostResponseMapper postResponseMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PostService postService, UserResponseMapper userResponseMapper, PostResponseMapper postResponseMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.postService = postService;

        this.userResponseMapper = userResponseMapper;
        this.postResponseMapper = postResponseMapper;
    }

    /**
     * Get a user by its identifier
     * @param id The user identifier
     * @return The user
     * @throws NotFoundException If the user is not found
     */
    @Override
    public UserResponse getUser(Integer id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));
        return userResponseMapper.apply(user);
    }

    // Register
    /**
     * Create a new user
     * @param userRequest The user to create
     * @throws AlreadyExistException If the user already exists (email)
     */
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

        // return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

    /**
     * Update a user
     * @param id The user identifier
     * @param userRequest The updated user infos
     * @return The updated user
     * @throws NotFoundException If the user is not found
     */
    @Override
    public UserResponse updateUser(Integer id, UpdateUserRequest userRequest) throws NotFoundException {
        Optional<User> userInDB = userRepository.findById(id);
        if (userInDB.isPresent()) {
            User user = userInDB.get();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            if (!Objects.equals(userRequest.getPassword(), "")) user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

            userRepository.save(user);
            return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
        } else {
            throw new NotFoundException("Utilisateur non référencé.");
        }
    }

    /**
     * Get all user's subscriptions
     * @param id The user identifier
     * @return All user's subscriptions
     * @throws NotFoundException If the user is not found
     */
    @Override
    public List<Topic> getUserTopics(Integer id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));
        return user.getSubscriptions();
    }

    /**
     * Get a user feed
     * @param id The user identifier
     * @param limit The number of posts to return
     * @param offset The number of posts to skip
     * @param sort The sort order (by date - descending or ascending)
     * @return All user's subscriptions posts
     * @throws NotFoundException If the user is not found
     */
    @Override
    public Stream<PostResponse> getUserFeed(int id, int limit, int offset, boolean sort) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        List<Integer> topicIds = user.getSubscriptions().stream().map(Topic::getId).toList();

        List<Post> posts = postService.getFeed(topicIds, limit, offset, sort);

        return posts.stream().map(postResponseMapper);
    }

    /**
     * Get the user feed size
     * @param id The user identifier
     * @return The user feed size
     * @throws NotFoundException If the user is not found
     */
    @Override
    public int getUserFeedSize(int id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        List<Integer> topicIds = user.getSubscriptions().stream().map(Topic::getId).toList();

        return postService.getFeedSize(topicIds);
    }
}
