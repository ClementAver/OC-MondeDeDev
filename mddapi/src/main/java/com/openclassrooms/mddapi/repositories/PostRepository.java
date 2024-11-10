package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}

