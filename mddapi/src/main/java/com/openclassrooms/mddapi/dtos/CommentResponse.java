package com.openclassrooms.mddapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    int id;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    int user;
    int post;
}
