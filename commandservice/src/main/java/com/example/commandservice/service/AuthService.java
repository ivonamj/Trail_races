package com.example.commandservice.service;

import com.example.commandservice.model.User;
import com.example.commandservice.repository.UserRepository;
import com.example.commandservice.security.JwtUtil;
import com.example.commandservice.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(String email, String password) {
        log.debug("Authenticating user with email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.warn("Authentication failed: User not found with email: {}", email);
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Authentication failed: Invalid password for email: {}", email);
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getId());
        log.info("User authenticated successfully: {}", email);
        return token;
    }
}
