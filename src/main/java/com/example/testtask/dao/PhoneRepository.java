package com.example.testtask.dao;

import com.example.testtask.dao.entities.PhoneData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends CrudRepository<PhoneData, Long> {
}
