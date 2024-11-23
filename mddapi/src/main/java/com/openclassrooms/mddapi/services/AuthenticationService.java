package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.LoginRequest;
import com.openclassrooms.mddapi.dtos.LoginResponse;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exceptions.NotFoundException;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationInterface {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager, JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) throws NotFoundException {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .or(() -> userRepository.findByName(loginRequest.getName()))
                .orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        // Handle email or name authentication.
                        loginRequest.getEmail().isEmpty() ? loginRequest.getName() :  loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(user);
        String jwtRefreshToken = jwtService.generateRefreshToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setRefreshToken(jwtRefreshToken);

        return loginResponse;
    }

    public LoginResponse refresh(String refreshToken) throws NotFoundException {
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByEmail(username)
                .or(() -> userRepository.findByName(username))
                .orElseThrow(() -> new NotFoundException("Utilisateur non référencé."));

        if (jwtService.isTokenValid(refreshToken, user)) {
            String newAccessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateToken(user);
            return new LoginResponse(newAccessToken, newRefreshToken);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}