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
public class TopicResponse {
    public Integer id;
    public String name;
    public String description;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
