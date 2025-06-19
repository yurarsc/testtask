package com.example.testtask.api;

import com.example.testtask.dao.EmailData;

public record EmailResponse(Long id, String email) {
    public EmailResponse(EmailData emailData) {
        this(emailData.getId(), emailData.getEmail());
    }
}
