package com.example.testtask.api;

import com.example.testtask.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> search(@RequestParam(value = "dateOfBirth", required = false) LocalDate dateOfBirth,
                                     @RequestParam(value = "phone", required = false) String phone,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "email", required = false) String email) {
        return userService.findUser(dateOfBirth, phone, name, email)
                .stream()
                .map(UserResponse::new)
                .toList();
    }
}
