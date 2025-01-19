package com.asmaa.hr.employeemanagement.repository;

import com.asmaa.hr.employeemanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
