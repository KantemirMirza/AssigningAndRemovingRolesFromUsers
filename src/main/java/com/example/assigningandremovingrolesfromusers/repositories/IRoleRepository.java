package com.example.assigningandremovingrolesfromusers.repositories;

import com.example.assigningandremovingrolesfromusers.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String roleName);

}
