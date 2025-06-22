package com.hexaware.careerjobportal.services;

import java.util.List;

import com.hexaware.careerjobportal.dto.JobSeekerProfileDTO;
import com.hexaware.careerjobportal.entities.JobSeekerProfile;

public interface IJobSeekerProfileService {
    JobSeekerProfile updateProfile(int id, JobSeekerProfileDTO dto);
    JobSeekerProfileDTO getProfileByUserId(int userId);
    List<JobSeekerProfile> getAllProfiles();
    String deleteProfile(int id);
}
