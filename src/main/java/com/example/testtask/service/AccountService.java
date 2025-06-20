package com.example.testtask.service;

import com.example.testtask.dao.Account;
import com.example.testtask.dao.AccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final EntityManager entityManager;

    public AccountService(AccountRepository accountRepository, EntityManager entityManager) {
        this.accountRepository = accountRepository;
        this.entityManager = entityManager;
    }

    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional
    public void updateBalanceMax(Long id, double max) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("update_balance_max");

        query.registerStoredProcedureParameter("account_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("max_value", Double.class, ParameterMode.IN);

        query.setParameter("account_id", id);
        query.setParameter("max_value", max);

        query.execute();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transfer(Long userIdFrom, Long userIdTo, BigDecimal amount) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("account_transfer");

        query.registerStoredProcedureParameter("user_id_from", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("user_id_to", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("amount", Double.class, ParameterMode.IN);

        query.setParameter("user_id_from", userIdFrom);
        query.setParameter("user_id_to", userIdTo);
        query.setParameter("amount", amount);

        query.execute();
    }
}
