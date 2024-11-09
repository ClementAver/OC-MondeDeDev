package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    @Size(max = 250, message = "Le titre ne doit pas dépasser 250 caractères.")
    private String title;
    @Size(max = 8000, message = "Le contenu ne doit pas dépasser 8000 caractères.")
    private String content;
    private Integer topic;
    private Integer user;
}
