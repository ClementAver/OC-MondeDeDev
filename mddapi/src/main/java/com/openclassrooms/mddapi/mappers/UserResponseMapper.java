package com.openclassrooms.mddapi.mappers;

import com.openclassrooms.mddapi.dtos.UserResponse;
import com.openclassrooms.mddapi.entities.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserResponseMapper implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
