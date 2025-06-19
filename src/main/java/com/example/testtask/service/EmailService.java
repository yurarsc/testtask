package com.example.testtask.service;

import com.example.testtask.dao.EmailData;
import com.example.testtask.dao.EmailRepository;
import com.example.testtask.dao.User;
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
        return emailRepository.save(entity);
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
        return emailRepository.save(emailData);
    }

    public void delete(Long userId, Long emailId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            throw new RequestException("user not found");
        }
        emailRepository.deleteById(emailId);
    }
}
