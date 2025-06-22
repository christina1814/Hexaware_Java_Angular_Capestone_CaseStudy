package com.hexaware.careerjobportal.restcontrollers;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.careerjobportal.dto.JobSeekerProfileDTO;
import com.hexaware.careerjobportal.entities.JobSeekerProfile;
import com.hexaware.careerjobportal.services.IJobSeekerProfileService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobseeker-profile")
public class JobSeekerProfileRestController {

    private final IJobSeekerProfileService profileService;

    public JobSeekerProfileRestController(IJobSeekerProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/view/{userId}")
    public ResponseEntity<JobSeekerProfileDTO> getProfileByUserId(@PathVariable int userId) {
        JobSeekerProfileDTO profileDTO = profileService.getProfileByUserId(userId);

        if (profileDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }

        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/showAll")
    public ResponseEntity<List<JobSeekerProfile>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @PutMapping("update/{id}") // done by jobseeker id
    public ResponseEntity<String> updateProfile(@PathVariable int id, @Valid @RequestBody JobSeekerProfileDTO dto) {
        profileService.updateProfile(id, dto);
        return ResponseEntity.ok("Profile updated successfully.");
    }

    @DeleteMapping("deactivate/{id}")
    public ResponseEntity<String> deleteProfile(@PathVariable int id) {
        String message = profileService.deleteProfile(id);
        return ResponseEntity.ok(message);
    }


}
