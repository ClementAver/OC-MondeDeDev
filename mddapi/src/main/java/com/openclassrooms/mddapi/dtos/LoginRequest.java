package com.openclassrooms.mddapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Size(max = 64, message = "Le nom ne doit pas dépasser 64 caractères.")
    String name; // Needed at register


    @Email(message = "L'adresse email doit être valide.")
    String email;

    @NotBlank(message = "Le mot de passe ne doit pas être vide.")
    @Size(max = 256, message = "Le mot ne passe ne doit pas dépasser 256 caractères.")
    String password;
}
