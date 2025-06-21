package com.example.testtask.service;

import com.example.testtask.cache.CacheHelper;
import com.example.testtask.dao.entities.EmailData;
import com.example.testtask.dao.EmailRepository;
import com.example.testtask.dao.entities.User;
import com.example.testtask.dao.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {

    private EmailRepository emailRepository;
    private UserRepository userRepository;

    public EmailService(EmailRepository emailRepository,
                        UserRepository userRepository) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
    }

    public EmailData create(Long userId, String email) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new RequestException("user not found");
        }
        User user = optUser.get();
        EmailData entity = new EmailData(user, email);
        EmailData saved = emailRepository.save(entity);

        CacheHelper.cleanUser(userId);

        return saved;
    }

    public EmailData update(Long userId, Long emailId, String email) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new RequestException("user not found");
        }
        User user = optUser.get();
        Optional<EmailData> optEmailData = user.getEmails().stream()
                .filter(it -> it.getId().equals(emailId))
                .findFirst();
        if (optEmailData.isEmpty()) {
            throw new RequestException("email not found");
        }
        EmailData emailData = optEmailData.get();
        emailData.setEmail(email);
        EmailData saved = emailRepository.save(emailData);

        CacheHelper.cleanUser(userId);

        return saved;
    }

    public void delete(Long userId, Long emailId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new RequestException("user not found");
        }
        emailRepository.deleteById(emailId);

        CacheHelper.cleanUser(userId);
    }
}
