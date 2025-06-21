package com.example.testtask.dao;

import com.example.testtask.dao.entities.EmailData;
import org.springframework.data.repository.CrudRepository;

public interface EmailRepository extends CrudRepository<EmailData, Long> {
}
