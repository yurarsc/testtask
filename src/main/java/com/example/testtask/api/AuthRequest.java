package com.example.testtask.api;

import org.springframework.util.StringUtils;

public record AuthRequest(String login, String password) {
    public AuthRequest {
        if (!StringUtils.hasText(login) || !StringUtils.hasText(password)) {
            throw new RuntimeException("phone or email and password should be present");
        }
    }
}
