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
public class PostResponse {
    int id;
    String title;
    String content;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    int topic;
    int user;

}
