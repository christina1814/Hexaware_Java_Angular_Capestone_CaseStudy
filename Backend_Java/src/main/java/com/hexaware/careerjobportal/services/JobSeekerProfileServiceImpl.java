package com.hexaware.careerjobportal.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hexaware.careerjobportal.dto.JobSeekerProfileDTO;
import com.hexaware.careerjobportal.entities.JobSeekerProfile;
import com.hexaware.careerjobportal.entities.ProficiencyLevel;
import com.hexaware.careerjobportal.entities.Skill;
import com.hexaware.careerjobportal.repositories.JobSeekerProfileRepository;
import com.hexaware.careerjobportal.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobSeekerProfileServiceImpl implements IJobSeekerProfileService {

    private final JobSeekerProfileRepository profileRepo;
    private final UserRepository userRepo;

    public JobSeekerProfileServiceImpl(JobSeekerProfileRepository profileRepo, UserRepository userRepo) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
    }

    @Override
    public JobSeekerProfile updateProfile(int id, JobSeekerProfileDTO dto) {
        JobSeekerProfile profile = profileRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with ID " + id));

        profile.setFullName(dto.getFullName());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setEducation(dto.getEducation());
        profile.setExperience(dto.getExperience());
        profile.setResume(dto.getResume());
        profile.getSkills().clear();

        List<Skill> skillEntities = dto.getSkills().stream()
            .map(name -> {
                Skill skill = new Skill();
                skill.setName(name);
                skill.setProficiencyLevel(ProficiencyLevel.NOT_PROVIDED); // default value
                skill.setJobSeekerProfile(profile);
                return skill;
            }).collect(Collectors.toList());

        profile.getSkills().addAll(skillEntities);

        return profileRepo.save(profile);
    }

    @Override
    public JobSeekerProfileDTO getProfileByUserId(int userId) {
        JobSeekerProfile profile = profileRepo.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("Profile not found for user ID " + userId));

        // Force fetch skills if lazy
        profile.getSkills().size();

        // Convert entity to DTO
        JobSeekerProfileDTO dto = new JobSeekerProfileDTO();
        dto.setId(profile.getId()); // ✅ ADD THIS LINE
        dto.setFullName(profile.getFullName());
        dto.setPhoneNumber(profile.getPhoneNumber());
        dto.setEducation(profile.getEducation());
        dto.setExperience(profile.getExperience());
        dto.setResume(profile.getResume());
        dto.setUserId(profile.getUser().getId());
        dto.setSkills(
            profile.getSkills().stream()
                   .map(Skill::getName)
                   .collect(Collectors.toList())
        );

        return dto;
    }


    @Override
    public List<JobSeekerProfile> getAllProfiles() {
        return profileRepo.findAll();
    }
  
    @Override
    @Transactional
    public String deleteProfile(int id) {
        JobSeekerProfile profile = profileRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with ID " + id));

        int userId = profile.getUser().getId();

        // Delete profile first (will also delete skills, applications due to cascading)
        profileRepo.deleteById(id);

        // Then delete the associated user
        userRepo.deleteById(userId);

        return "Profile with ID " + id + " deleted successfully.";
    }


}
