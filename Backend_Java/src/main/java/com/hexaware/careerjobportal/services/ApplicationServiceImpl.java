package com.hexaware.careerjobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.careerjobportal.dto.ApplicationRequestDTO;
import com.hexaware.careerjobportal.dto.ApplicationResponseDTO;
import com.hexaware.careerjobportal.entities.Application;
import com.hexaware.careerjobportal.entities.JobPosting;
import com.hexaware.careerjobportal.entities.JobSeekerProfile;
import com.hexaware.careerjobportal.repositories.ApplicationRepository;
import com.hexaware.careerjobportal.repositories.JobPostingRepository;
import com.hexaware.careerjobportal.repositories.JobSeekerProfileRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Override
    public ApplicationResponseDTO applyForJob(ApplicationRequestDTO request) {
        if (request.getResumePath() == null || request.getResumePath().trim().isEmpty()) {
            throw new IllegalArgumentException("Resume file path must be provided to apply for a job");
        }

        JobSeekerProfile seeker = jobSeekerProfileRepository.findById(request.getSeekerId())
                .orElseThrow(() -> new RuntimeException("Job seeker not found"));
        
        if (isIncomplete(seeker)) {
            throw new RuntimeException("Please complete your profile (education, experience, phoneNumber) before applying.");
        }

        JobPosting jobPosting = jobPostingRepository.findById(request.getJobId())
                .orElseThrow(() -> new RuntimeException("Job posting not found"));

        if (applicationRepository.findByJobAndSeeker(jobPosting.getId(), seeker.getId()).isPresent()) {
            throw new RuntimeException("Already applied for this job");
        }

        Application application = new Application();
        application.setJobSeekerProfile(seeker);
        application.setJobPosting(jobPosting);
        application.setApplicationDate(LocalDate.now());
        application.setStatus("APPLIED");
        application.setResumeFileName(request.getResumePath());

        applicationRepository.save(application);

        return new ApplicationResponseDTO(
                application.getId(),
                jobPosting.getJobTitle(),
                seeker.getUser().getEmail(),
                application.getStatus()
        );
    }
    
    private boolean isIncomplete(JobSeekerProfile seeker) {
        return isEmpty(seeker.getEducation()) ||
               isEmpty(seeker.getExperience()) ||
               isEmpty(seeker.getPhoneNumber());
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().equals("") || value.trim().equalsIgnoreCase("not provided");
    }


    @Override
    public List<ApplicationResponseDTO> getApplicationsByJobSeeker(Integer seekerId) {
        // Check if JobSeeker exists
        boolean seekerExists = jobSeekerProfileRepository.existsById(seekerId);
        if (!seekerExists) {
            throw new RuntimeException("Seeker ID does not exist");
        }

        List<Application> applications = applicationRepository.findByJobSeekerProfileId(seekerId);

        if (applications.isEmpty()) {
            throw new RuntimeException("This job seeker has not applied to any jobs");
        }

        return applications.stream()
                .map(app -> new ApplicationResponseDTO(
                        app.getId(),
                        app.getJobPosting().getJobTitle(),
                        app.getJobSeekerProfile().getUser().getEmail(),
                        app.getStatus()))
                .collect(Collectors.toList());
    }


    @Override
    public List<ApplicationResponseDTO> getApplicationsByJobId(Integer jobId) {
        // Check if the job exists
        boolean jobExists = jobPostingRepository.existsById(jobId);
        if (!jobExists) {
            throw new RuntimeException("Job with ID " + jobId + " does not exist");
        }

        // Fetch applications
        List<Application> applications = applicationRepository.findByJobPostingId(jobId);

        // Handle empty case
        if (applications.isEmpty()) {
            throw new RuntimeException("No job seekers have applied for this job");
        }

        return applications.stream()
                .map(app -> new ApplicationResponseDTO(
                        app.getId(),
                        app.getJobPosting().getJobTitle(),
                        app.getJobSeekerProfile().getUser().getEmail(),
                        app.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateApplicationStatus(Integer applicationId, String newStatus) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(newStatus.toUpperCase());
        applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(Integer applicationId) {
        if (!applicationRepository.existsById(applicationId)) {
            throw new RuntimeException("Application not found");
        }
        applicationRepository.deleteById(applicationId);
    }
}