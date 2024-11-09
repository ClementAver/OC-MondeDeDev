package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.entities.Post;
import com.openclassrooms.mddapi.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findTop10ByTopicOrderByCreatedAtDesc(Topic topic);
}
