package com.example.testtask.dao;

import com.example.testtask.dao.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
