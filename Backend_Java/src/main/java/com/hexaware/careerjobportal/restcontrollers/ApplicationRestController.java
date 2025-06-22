package com.hexaware.careerjobportal.restcontrollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.careerjobportal.dto.ApplicationRequestDTO;
import com.hexaware.careerjobportal.dto.ApplicationResponseDTO;
import com.hexaware.careerjobportal.services.IApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationRestController {

    @Autowired
    private IApplicationService applicationService;

    // Only job seekers should apply for jobs
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PostMapping("/apply")
    public ResponseEntity<ApplicationResponseDTO> applyForJob(
            @Valid @RequestBody ApplicationRequestDTO requestDTO) {
        ApplicationResponseDTO response = applicationService.applyForJob(requestDTO);
        return ResponseEntity.ok(response);
    }

    // Job seekers can view their own applications; employers can view applications for jobs they posted or all applications
    @PreAuthorize("hasRole('JOB_SEEKER') or hasRole('EMPLOYER')")
    @GetMapping("/seeker/{seekerId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsBySeeker(@PathVariable Integer seekerId) {
        List<ApplicationResponseDTO> responseList = applicationService.getApplicationsByJobSeeker(seekerId);
        return ResponseEntity.ok(responseList);
    }


    // Employers can view applications for a job posting
    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByJobId(@PathVariable Integer jobId) {
        List<ApplicationResponseDTO> responseList = applicationService.getApplicationsByJobId(jobId);
        return ResponseEntity.ok(responseList);
    }

    // Employers can update application status
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Integer applicationId,
                                               @RequestParam String status) {
        applicationService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok("Application status updated to " + status.toUpperCase());
    }

    // Job seekers can withdraw their own application
    @PreAuthorize("hasRole('JOB_SEEKER')")
    @DeleteMapping("withDraw/{applicationId}")
    public ResponseEntity<String> deleteApplication(@PathVariable Integer applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.ok("Application deleted successfully");
    }
}

