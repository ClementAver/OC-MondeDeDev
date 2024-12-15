package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.LoginRequest;
import com.openclassrooms.mddapi.dtos.LoginResponse;
import com.openclassrooms.mddapi.exceptions.NotFoundException;

public interface AuthenticationInterface {
    LoginResponse authenticate(LoginRequest loginRequest) throws NotFoundException;

    LoginResponse refresh(String refreshToken) throws NotFoundException;
}

