package com.example.testtask.api;

import com.example.testtask.auth.JWTHelper;
import com.example.testtask.dao.User;
import com.example.testtask.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {

        Optional<User> user = userService.findByEmail(request.login());
        if (user.isEmpty()) {
            user = userService.findByPhone(request.login());
        }

        if (user.isPresent()
                && request.password().equals(user.get().getPassword())) {
            String token = JWTHelper.mintAccessToken(user.get().getId());
            AuthResponse response = new AuthResponse(token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(400).build();
    }
}
