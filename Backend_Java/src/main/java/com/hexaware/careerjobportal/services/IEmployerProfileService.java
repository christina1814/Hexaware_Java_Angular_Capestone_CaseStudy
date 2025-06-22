package com.hexaware.careerjobportal.services;

import java.util.Optional;

import com.hexaware.careerjobportal.dto.EmployerProfileDTO;
import com.hexaware.careerjobportal.entities.EmployerProfile;

public interface IEmployerProfileService {

	EmployerProfile updateEmployerProfileByUserId(Integer userId, EmployerProfileDTO dto);

    Optional<EmployerProfile> getEmployerProfileByUserId(Integer userId);

}
