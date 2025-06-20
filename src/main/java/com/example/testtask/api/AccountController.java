package com.example.testtask.api;

import com.example.testtask.auth.JWTHelper;
import com.example.testtask.service.AccountService;
import com.example.testtask.service.RequestException;
import jakarta.validation.Valid;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/transfer")
    public void transfer(@Valid @RequestBody TransferRequest request,
                         JwtAuthenticationToken jwtToken) {
        Long userId = JWTHelper.extractUserId(jwtToken);
        if (request.userIdTo().equals(userId)) {
            throw new RequestException("transfer to yourself");
        }
        if (request.amount().equals(BigDecimal.ZERO)) {
            throw new RequestException("nothing to transfer");
        }
        accountService.transfer(userId, request.userIdTo(), request.amount());
    }
}
