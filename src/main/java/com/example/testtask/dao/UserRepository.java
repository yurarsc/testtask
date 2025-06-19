package com.example.testtask.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("""
            select u
            from User u
            join u.phones p
            join u.emails e
            where  (:dateOfBirth is null or u.dateOfBirth >= :dateOfBirth) and
                (:phone is null or p.phone = :phone) and
                (:name is null or u.name ilike (:name||'%')) and
                (:email is null or e.email = :email)
            """)
    List<User> find(@Param("dateOfBirth") LocalDate dateOfBirth,
                    @Param("phone") String phone,
                    @Param("name") String name,
                    @Param("email") String email);

    @Query("""
            select u
            from User u
            join u.emails e
            where e.email = :email
            """)
    Optional<User> findByEmail(@Param("email") String email);

    @Query("""
            select u
            from User u
            join u.phones p
            where p.phone = :phone
            """)
    Optional<User> findByPhone(@Param("phone") String phone);

}
