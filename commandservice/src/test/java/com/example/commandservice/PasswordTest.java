package com.example.commandservice;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "password";
        String hashedPassword = "$2a$10$R40/wjvfvNB0wY2hMTnli.hTHzoBqq.lbLZBQf5bJ3OBYI/NIvBAC";
        System.out.println(encoder.matches(rawPassword, hashedPassword)); // Should print true
    }
}

