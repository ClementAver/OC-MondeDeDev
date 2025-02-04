package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.PostRequest;
import com.openclassrooms.mddapi.dtos.PostResponse;
import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.mappers.PostResponseMapper;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService implements PostInterface {

    private final PostRepository postRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final PostResponseMapper postResponseMapper;
    private final EntityManager entityManager;

    @Autowired
    public PostService(PostRepository postRepository, TopicRepository topicRepository, UserRepository userRepository, PostResponseMapper postResponseMapper, EntityManager entityManager) {
        this.postRepository = postRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.postResponseMapper = postResponseMapper;
        this.entityManager = entityManager;
    }

    /**
     * Create a new post
     * @param postRequest The post to create
     * @return The created post
     * @throws NotFoundException If the post is not found
     */
    @Override
    public PostResponse createPost(PostRequest postRequest) throws NotFoundException {
        Topic topic = topicRepository.findById(postRequest.getTopic())
                .orElseThrow(() -> new NotFoundException("Thème non référencé."));
        User user = userRepository.findById(postRequest.getUser())
                .orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setTopic(topic);
        post.setUser(user);

        postRepository.save(post);
        return postResponseMapper.apply(post);
    }

    /**
     * Get a post by its identifier
     * @param id The post identifier
     * @return The post
     * @throws NotFoundException If the post is not found
     */
    @Override
    public PostResponse getPost(Integer id) throws NotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Article non référencé."));

        return postResponseMapper.apply(post);
    }

    /**
     * Get the number of posts for a list of topics
     * @param topicIds The list of topics
     * @return The number of posts
     */
    @Override
    public int getFeedSize(List<Integer> topicIds) {
        String topicIdsStr = topicIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String queryStr = String.format("""
            SELECT COUNT(*) FROM posts p
            WHERE p.topic_id IN (%s)
        """, topicIdsStr);

        Query query = entityManager.createNativeQuery(queryStr);

        return ((Number) query.getSingleResult()).intValue();
    }

    /**
     * Get a list of posts for a list of topics
     * @param topicIds The list of topics
     * @param limit The number of posts to return
     * @param offset The number of posts to skip
     * @param sort The sort order
     * @return The feed of posts
     */
    @Override
    public List<Post> getFeed(List<Integer> topicIds, int limit, int offset, boolean sort) {
        String topicIdsStr = topicIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String queryStr;

        if (sort) {
            queryStr = String.format("""
                SELECT * FROM posts p
                WHERE p.topic_id IN (%s)
                ORDER BY p.updated_at DESC
                LIMIT %d OFFSET %d
            """, topicIdsStr, limit, offset);
        } else {
            queryStr = String.format("""
                SELECT * FROM posts p
                WHERE p.topic_id IN (%s)
                ORDER BY p.updated_at ASC
                LIMIT %d OFFSET %d
            """, topicIdsStr, limit, offset);
        }

        Query query = entityManager.createNativeQuery(queryStr, Post.class);

        return query.getResultList();
    }
}
