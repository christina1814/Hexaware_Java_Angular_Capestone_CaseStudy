package com.hexaware.careerjobportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.careerjobportal.entities.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

}
