package com.example.testtask.api.data;

import com.example.testtask.dao.entities.EmailData;
import com.example.testtask.dao.entities.PhoneData;
import com.example.testtask.dao.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(Long id,
                           String name,
                           LocalDateTime dateOfBirth,
                           List<EmailResponse> emails,
                           List<PhoneResponse> phones,
                           BigDecimal balance) {
    public UserResponse(User user) {
        this(user.getId(),
                user.getName(),
                user.getDateOfBirth(),
                emailResponses(user.getEmails()),
                phoneResponses(user.getPhones()),
                user.getAccount().getBallance()
        );
    }

    private static List<EmailResponse> emailResponses(List<EmailData> emailData) {
        return emailData.stream()
                .map(it -> new EmailResponse(it.getId(), it.getEmail()))
                .toList();
    }

    private static List<PhoneResponse> phoneResponses(List<PhoneData> phoneData) {
        return phoneData.stream()
                .map(it -> new PhoneResponse(it.getId(), it.getPhone()))
                .toList();
    }

}
