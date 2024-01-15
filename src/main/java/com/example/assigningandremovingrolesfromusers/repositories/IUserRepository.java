package com.example.assigningandremovingrolesfromusers.repositories;

import com.example.assigningandremovingrolesfromusers.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    boolean existUserEmail(String email);
    void deleteByUserEmail(String email);
    Optional<Object> findByUserEmail(String email);
}
