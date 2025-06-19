package com.example.testtask.service;

import com.example.testtask.dao.User;
import com.example.testtask.dao.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUser(LocalDate dateOfBirth,
                               String phone,
                               String name,
                               String email) {
        return userRepository.find(dateOfBirth, phone, name, email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
