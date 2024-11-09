package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Le titre ne peut pas être vide.")
    @Size(max = 250, message = "Le titre ne doit pas dépasser 250 caractères.")
    private String title;

    @NotBlank(message = "Le contenu ne peut pas être vide.")
    @Size(max = 8000, message = "Le contenu ne doit pas dépasser 8000 caractères.")
    private String content;

    private int topic;

    private int user;
}
