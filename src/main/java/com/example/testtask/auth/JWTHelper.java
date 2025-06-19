package com.example.testtask.auth;

import io.jsonwebtoken.Jwts;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.util.Date;

public class JWTHelper {
    private static final Key PRIVATE_KEY;

    static {
        try {
            PRIVATE_KEY = PEMHelper.loadPrivateKey(JWTHelper.class.getClassLoader().getResource("private_key.pem"));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static String mintAccessToken(Long userId) {
        Date expiration = Date.from(Instant.now().plusSeconds(3600_00)); // 1 hour expiration
        return Jwts.builder()
                .claim("userId", userId)
                .setExpiration(expiration)
                .signWith(PRIVATE_KEY)
                .compact();
    }

    public static Long extractUserId(JwtAuthenticationToken jwtToken) {
        return (Long) jwtToken.getTokenAttributes().get("userId");
    }
}
