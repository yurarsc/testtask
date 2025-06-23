package com.example.testtask.dao;

import com.example.testtask.dao.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("""
            select distinct u
            from User u
            join u.phones p
            join u.emails e
            where (TRUE = :#{#dateOfBirth == null} or u.dateOfBirth >= :dateOfBirth) and
                (:phone is null or p.phone = :phone) and
                (:name is null or u.name ilike (:name||'%')) and
                (:email is null or e.email = :email)
            
            """)
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "user_entity_graph")
    Page<User> find(@Param("dateOfBirth") LocalDateTime dateOfBirth,
                    @Param("phone") String phone,
                    @Param("name") String name,
                    @Param("email") String email,
                    Pageable pageable);

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
