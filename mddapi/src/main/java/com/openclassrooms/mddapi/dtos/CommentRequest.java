package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @Size(max = 256, message = "Le contenu ne doit pas dépasser 250 caractères.")
    private String content;

    private int post;

    private int user;
}
