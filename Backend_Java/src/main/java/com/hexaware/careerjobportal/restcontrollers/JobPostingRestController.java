package com.hexaware.careerjobportal.restcontrollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.careerjobportal.dto.JobPostingDTO;
import com.hexaware.careerjobportal.entities.JobPosting;
import com.hexaware.careerjobportal.services.IJobPostingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/job-postings")
public class JobPostingRestController {

    @Autowired
    private IJobPostingService jobPostingService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<?> createJobPosting(@Valid @RequestBody JobPostingDTO dto) {
        JobPosting jobPosting = jobPostingService.createJobPosting(dto);
        return ResponseEntity.ok("Job posting saved successfully with id: " + jobPosting.getId());
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<?> updateJobPosting(@PathVariable Integer id, @Valid @RequestBody JobPostingDTO dto) {
        try {
            JobPosting updated = jobPostingService.updateJobPosting(id, dto);
            return ResponseEntity.ok("Job posting updated successfully for ID: " + updated.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update job posting: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYER', 'JOB_SEEKER')")
    public ResponseEntity<?> getJobPostingById(@PathVariable Integer id) {
        return jobPostingService.getJobPostingById(id)
                .map(job -> {
                    JobPostingDTO dto = new JobPostingDTO();
                    dto.setEmployerId(job.getEmployerProfile().getId());
                    dto.setTitle(job.getJobTitle());
                    dto.setDescription(job.getJobDescription());
                    dto.setLocation(job.getLocation());
                    dto.setJobType(job.getEmploymentType().name());
                    dto.setSalaryRange(job.getSalary());
                    dto.setApplicationDeadline(job.getApplicationDeadline());
                    dto.setQualifications(job.getQualifications());
                    return ResponseEntity.ok(dto);
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employer/{employerId}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<List<JobPostingDTO>> getJobPostingsByEmployer(@PathVariable Integer employerId) {
        return ResponseEntity.ok(jobPostingService.getJobPostingsByEmployerId(employerId));
    }


    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('EMPLOYER', 'JOB_SEEKER')")
    public ResponseEntity<List<JobPostingDTO>> searchJobPostings(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location) {

        List<JobPostingDTO> dtos = jobPostingService.searchJobPostings(title, location).stream()
                .map(job -> {
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

        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<?> deleteJobPosting(@PathVariable Integer id) {
        jobPostingService.deleteJobPosting(id);
        return ResponseEntity.ok("Job posting deleted successfully.");
    }
}
