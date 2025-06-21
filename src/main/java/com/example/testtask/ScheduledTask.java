package com.example.testtask;

import com.example.testtask.dao.entities.Account;
import com.example.testtask.service.AccountService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Spliterator;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.StreamSupport.stream;

@Component
public class ScheduledTask {

    private AccountService accountService;
    private Map<Long, BigDecimal> accounts;

    public ScheduledTask(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostConstruct
    public void postConstruct() {
        Spliterator<Account> accountSpliterator = accountService.findAll().spliterator();
        accounts = stream(accountSpliterator, false)
                .collect(toMap(Account::getId, Account::getBallance));
    }

    @Scheduled(fixedRate = 30_000)
    public void fixedRateTask() {
        accounts.forEach((id, balance) -> {

            BigDecimal max = balance.multiply(BigDecimal.valueOf(2.07));
            accountService.updateBalanceMax(id, max);
        });
    }
}
