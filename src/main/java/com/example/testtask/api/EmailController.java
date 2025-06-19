package com.example.testtask.api;

import com.example.testtask.dao.EmailData;
import com.example.testtask.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EmailResponse create(@RequestBody EmailRequest request,
                                JwtAuthenticationToken jwtToken) {
        Long userId = (Long) jwtToken.getTokenAttributes().get("userId");
        EmailData emailData = emailService.create(userId, request.email());
        return new EmailResponse(emailData);
    }

    @PutMapping("/{id}")
    public EmailResponse update(@PathVariable("id") Long emailId,
                                @RequestBody EmailRequest request,
                                JwtAuthenticationToken jwtToken) {
        Long userId = (Long) jwtToken.getTokenAttributes().get("userId");
        EmailData emailData = emailService.update(userId, emailId, request.email());
        return new EmailResponse(emailData);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long emailId,
                       JwtAuthenticationToken jwtToken) {
        Long userId = (Long) jwtToken.getTokenAttributes().get("userId");
        emailService.delete(userId, emailId);
    }
}
