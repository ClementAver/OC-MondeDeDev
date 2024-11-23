package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @NotBlank(message = "Le nom ne doit pas être vide.")
    @Size(max = 64, message = "Le nom ne doit pas dépasser 64 caractères.")
    String name; // Needed at register

    @NotNull(message = "Le courriel est obligatoire.")
    @Email(message = "L'adresse email doit être valide.")
    String email;
}
