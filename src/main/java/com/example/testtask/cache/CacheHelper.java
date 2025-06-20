package com.example.testtask.cache;

import com.example.testtask.dao.User;
import org.springframework.data.domain.Page;

import java.util.*;

public class CacheHelper {

    private static final Map<UserSearchParameters, Page<User>> usersCacheByParameters = new TreeMap<>();
    private static final Map<String, User> usersCacheByEmail = new TreeMap<>();
    private static final Map<String, User> usersCacheByPhone = new TreeMap<>();

    public static Optional<Page<User>> getUsers(UserSearchParameters userRequest) {
        return Optional.ofNullable(usersCacheByParameters.get(userRequest));
    }

    public static void putUsersByParameters(UserSearchParameters userRequest,
                                            Page<User> users) {
        usersCacheByParameters.put(userRequest, users);
    }

    synchronized public static void cleanUser(Long userId) {
        List<UserSearchParameters> userParamsToDelete = new ArrayList<>();
        usersCacheByParameters.forEach((userParams, users) -> {
            boolean containsUserId = users.stream()
                    .anyMatch(it -> it.getId().equals(userId));
            if (containsUserId) {
                userParamsToDelete.add(userParams);
            }
        });
        userParamsToDelete.forEach(usersCacheByParameters::remove);

        List<String> emailsToDelete = new ArrayList<>();
        usersCacheByEmail.forEach((email, user) -> {
            if (user.getId().equals(userId)) {
                emailsToDelete.add(email);

            }
        });
        emailsToDelete.forEach(usersCacheByEmail::remove);

        List<String> numbersToDelete = new ArrayList<>();
        usersCacheByPhone.forEach((phone, user) -> {
            if (user.getId().equals(userId)) {
                numbersToDelete.add(phone);

            }
        });
        numbersToDelete.forEach(usersCacheByPhone::remove);
    }

    synchronized public static void putUserByEmail(String email, User user) {
        usersCacheByEmail.put(email, user);
    }

    synchronized public static void putUserByPhone(String phone, User user) {
        usersCacheByEmail.put(phone, user);
    }

    synchronized public static Optional<User> getUsersByEmail(String email) {
        User user = usersCacheByEmail.get(email);
        return Optional.ofNullable(user);
    }

    synchronized public static Optional<User> getUsersByPhone(String phone) {
        User user = usersCacheByPhone.get(phone);
        return Optional.ofNullable(user);
    }
}
