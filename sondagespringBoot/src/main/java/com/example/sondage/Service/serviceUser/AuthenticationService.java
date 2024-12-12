package com.example.sondage.Service.serviceUser;


import com.example.sondage.config.JwtService;
import com.example.sondage.dto.AuthenticationRequest;
import com.example.sondage.dto.AuthenticationResponse;
import com.example.sondage.dto.RegisterRequest;
import com.example.sondage.entity.User;
import com.example.sondage.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    private UserService userService;
    OTPInterface otpInterface;
    public static final String uploadDirectory = "C:/xampp/htdocs/images/";

    public AuthenticationResponse register(RegisterRequest request) throws IOException {
        if (userRepository.existsByEmail(request.getEmail())) {
            System.out.println("Email already exists ");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (request.getUsername().equals(request.getPassword())) {
            throw new IllegalArgumentException("Le mot de passe doit être différent du nom d'utilisateur");
        }
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleName(request.getRoleName())
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        System.out.println("welcome : " + user.getUsername());

        // Ensure idUser is being set correctly
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .idUser(user.getIdUser()) // This should not be null if saved correctly
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .idUser(user.getIdUser())
                .roleName(user.getRoleName()) // Include role name
                .build();
    }
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var authResponse = AuthenticationResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .idUser(user.getIdUser())
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }



}
