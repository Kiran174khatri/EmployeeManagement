package com.example.employee_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee_management.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}