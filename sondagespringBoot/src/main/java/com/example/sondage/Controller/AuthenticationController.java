package com.example.sondage.Controller;


import com.example.sondage.dto.AuthenticationRequest;
import com.example.sondage.dto.AuthenticationResponse;
import com.example.sondage.dto.RegisterRequest;
import com.example.sondage.Repository.UserRepository;
import com.example.sondage.Service.serviceUser.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @Autowired
    UserRepository userRepository;



    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request
    )throws IOException {
        System.out.println("user added ! : ");
        return ResponseEntity.ok(service.register(request));
    }




    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return ResponseEntity.ok().build();
    }


}
