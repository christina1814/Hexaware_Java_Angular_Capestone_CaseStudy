package com.hexaware.careerjobportal.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careerjobportal.entities.EmployerProfile;

@Repository
public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, Integer> {
	Optional<EmployerProfile> findByUserId(Integer userId);

}
