package com.example.testtask.api.data;

import com.example.testtask.dao.entities.EmailData;

public record EmailResponse(Long id, String email) {
    public EmailResponse(EmailData emailData) {
        this(emailData.getId(), emailData.getEmail());
    }
}
