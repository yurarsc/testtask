package com.example.testtask.api;

import com.example.testtask.dao.User;
import com.example.testtask.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final static String X_PAGES_TOTAL = "X-Pages-Total";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> search(@RequestParam(value = "dateOfBirth", required = false)
                                                     @DateTimeFormat(pattern = "dd.MM.yyyy") @Valid LocalDate dateOfBirth,
                                                     @RequestParam(value = "phone", required = false) String phone,
                                                     @RequestParam(value = "name", required = false) String name,
                                                     @RequestParam(value = "email", required = false) String email,
                                                     @RequestHeader("X-Page-Number") Integer pageNumber,
                                                     @RequestHeader("X-Page-Size") Integer pageSize) {
        Page<User> page = userService.findUser(dateOfBirth.atStartOfDay(), phone, name, email, pageNumber, pageSize);
        List<UserResponse> users = page.get()
                .map(UserResponse::new)
                .toList();
        return ResponseEntity.ok()
                .header(X_PAGES_TOTAL, String.valueOf(page.getTotalPages()))
                .body(users);
    }
}
