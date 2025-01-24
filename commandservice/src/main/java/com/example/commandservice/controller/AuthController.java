package com.example.commandservice.controller;

import com.example.commandservice.dto.LoginDTO;
import com.example.commandservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO request) {
        log.info("Login attempt for email: {}", request.getEmail());
        String token = authService.authenticate(request.getEmail(), request.getPassword());
        log.info("User with email: {} authenticated successfully.", request.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
