package com.hexaware.careerjobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careerjobportal.dto.EmployerProfileDTO;
import com.hexaware.careerjobportal.entities.EmployerProfile;
import com.hexaware.careerjobportal.repositories.EmployerProfileRepository;
import java.util.Optional;

@Service
public class EmployerProfileServiceImpl implements IEmployerProfileService {

    @Autowired
    private EmployerProfileRepository employerProfileRepository;

    @Override
    public EmployerProfile updateEmployerProfileByUserId(Integer userId, EmployerProfileDTO dto) {
        EmployerProfile profile = employerProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Employer profile not found for user id: " + userId));

        profile.setCompanyName(dto.getCompanyName());
        profile.setIndustry(dto.getIndustry());
        profile.setLocation(dto.getLocation());
        profile.setContactInfo(dto.getContactInfo());
        profile.setDescription(dto.getDescription());

        return employerProfileRepository.save(profile);
    }




    @Override
    public Optional<EmployerProfile> getEmployerProfileByUserId(Integer userId) {
        return employerProfileRepository.findByUserId(userId);
    }

}


