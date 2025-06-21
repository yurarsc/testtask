package com.example.testtask.service;

import com.example.testtask.cache.CacheHelper;
import com.example.testtask.cache.UserSearchParameters;
import com.example.testtask.dao.User;
import com.example.testtask.dao.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findUser(LocalDateTime dateOfBirth,
                               String phone,
                               String name,
                               String email,
                               Integer pageNumber,
                               Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.asc("id")));
        UserSearchParameters parameters = new UserSearchParameters(dateOfBirth, phone, name, email, pageable);
        Optional<Page<User>> optCached = CacheHelper.getUsers(parameters);
        if (optCached.isPresent()) {
            return optCached.get();
        }

        Page<User> users = userRepository.find(dateOfBirth, phone, name, email, pageable);
        CacheHelper.putUsersByParameters(parameters, users);
        return users;
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> optCached = CacheHelper.getUsersByEmail(email);
        if (optCached.isPresent()) {
            return optCached;
        }

        Optional<User>  optUser = userRepository.findByEmail(email);
        optUser.ifPresent(user -> CacheHelper.putUserByEmail(email, user));
        return optUser;
    }

    public Optional<User> findByPhone(String phone) {
        Optional<User> optCached = CacheHelper.getUsersByPhone(phone);
        if (optCached.isPresent()) {
            return optCached;
        }

        Optional<User>  optUser = userRepository.findByPhone(phone);
        optUser.ifPresent(user -> CacheHelper.putUserByPhone(phone, user));
        return optUser;
    }
}
