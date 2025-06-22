package com.hexaware.careerjobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careerjobportal.dto.JobPostingDTO;
import com.hexaware.careerjobportal.entities.EmployerProfile;
import com.hexaware.careerjobportal.entities.JobPosting;
import com.hexaware.careerjobportal.entities.JobType;
import com.hexaware.careerjobportal.exception.IncompleteEmployerProfileException;
import com.hexaware.careerjobportal.exception.ResourceNotFoundException;
import com.hexaware.careerjobportal.repositories.EmployerProfileRepository;
import com.hexaware.careerjobportal.repositories.JobPostingRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobPostingServiceImpl implements IJobPostingService {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private EmployerProfileRepository employerProfileRepository;

    @Override
    public JobPosting createJobPosting(JobPostingDTO dto) {
        EmployerProfile employerProfile = employerProfileRepository.findById(dto.getEmployerId())
                .orElseThrow(() -> new RuntimeException("EmployerProfile not found with id: " + dto.getEmployerId()));

        // Validates required fields in employer profile table
        if (isIncomplete(employerProfile)) {
            throw new IncompleteEmployerProfileException("Employer profile is incomplete. Please update your profile before posting a job.");
        }

        JobPosting jobPosting = new JobPosting();
        jobPosting.setJobTitle(dto.getTitle());
        jobPosting.setJobDescription(dto.getDescription());
        jobPosting.setLocation(dto.getLocation());
        jobPosting.setSalary(dto.getSalaryRange());
        jobPosting.setQualifications
        (
           dto.getQualifications()
        );
        jobPosting.setApplicationDeadline(dto.getApplicationDeadline());
        jobPosting.setEmployerProfile(employerProfile);
        
        try 
        {
            jobPosting.setEmploymentType(JobType.valueOf(dto.getJobType().toUpperCase()));
        } 
        catch (IllegalArgumentException ex) 
        {
            throw new ResourceNotFoundException("Invalid job type: " + dto.getJobType() +
                ". Allowed values are: FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP.");
        }

        return jobPostingRepository.save(jobPosting);
    }

    private boolean isIncomplete(EmployerProfile employer) {
        return isNotProvided(employer.getCompanyName())
            || isNotProvided(employer.getIndustry())
            || isNotProvided(employer.getLocation())
            || isNotProvided(employer.getContactInfo());
    }

    private boolean isNotProvided(String value) {
        return value == null || value.equalsIgnoreCase("Not Provided");
    }
    
    @Override
    public JobPosting updateJobPosting(Integer id, JobPostingDTO dto) {
        JobPosting existing = jobPostingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Job posting not found with ID: " + id));

        // Update fields
        existing.setJobTitle(dto.getTitle());
        existing.setJobDescription(dto.getDescription());
        existing.setLocation(dto.getLocation());
        existing.setSalary(dto.getSalaryRange());
        existing.setApplicationDeadline(dto.getApplicationDeadline());
        existing.setQualifications
        (
           dto.getQualifications() != null ? dto.getQualifications() : "Not Provided"
        );
        
        try 
        {
            existing.setEmploymentType(JobType.valueOf(dto.getJobType().toUpperCase()));
        } catch (IllegalArgumentException ex) 
        {
            throw new ResourceNotFoundException("Invalid job type: " + dto.getJobType() +
                ". Allowed values are: FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP.");
        }

        return jobPostingRepository.save(existing);
    }


    @Override
    public Optional<JobPosting> getJobPostingById(Integer id) {
        return jobPostingRepository.findById(id);
    }

    @Override
    public List<JobPostingDTO> getJobPostingsByEmployerId(Integer employerId) {
        List<JobPosting> jobs = jobPostingRepository.findByEmployerProfileId(employerId);

        return jobs.stream().map(job -> {
            JobPostingDTO dto = new JobPostingDTO();
            dto.setId(job.getId()); 
            dto.setEmployerId(job.getEmployerProfile().getId());
            dto.setTitle(job.getJobTitle());
            dto.setDescription(job.getJobDescription());
            dto.setLocation(job.getLocation());
            dto.setJobType(job.getEmploymentType().name());
            dto.setSalaryRange(job.getSalary());
            dto.setApplicationDeadline(job.getApplicationDeadline());
            dto.setQualifications(job.getQualifications());
            return dto;
        }).collect(Collectors.toList());
    }


    @Override
    public List<JobPosting> searchJobPostings(String title, String location) {
        if (title != null && !title.isEmpty() && location != null && !location.isEmpty()) {
            return jobPostingRepository.findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(title, location);
        } else if (title != null && !title.isEmpty()) {
            return jobPostingRepository.findByTitleContainingIgnoreCase(title);  
        } else if (location != null && !location.isEmpty()) {
            return jobPostingRepository.findByLocationContainingIgnoreCase(location);
        } else {
            return jobPostingRepository.findAll();
        }
    }

    @Override
    public void deleteJobPosting(Integer id) {
    	JobPosting jobPosting = jobPostingRepository.findById(id)
    	        .orElseThrow(() -> new ResourceNotFoundException("No job posting found with ID: " + id));
    	    jobPostingRepository.delete(jobPosting);
    }
}
