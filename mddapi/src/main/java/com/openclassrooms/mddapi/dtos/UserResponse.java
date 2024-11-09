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
public class UserResponse {
    Integer id;
    String name;
    String email;
    LocalDateTime created_at;
    LocalDateTime updated_at;
}
