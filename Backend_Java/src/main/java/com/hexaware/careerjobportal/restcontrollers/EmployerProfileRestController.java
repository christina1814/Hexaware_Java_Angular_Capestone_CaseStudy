package com.hexaware.careerjobportal.restcontrollers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.careerjobportal.dto.EmployerProfileDTO;
import com.hexaware.careerjobportal.entities.EmployerProfile;
import com.hexaware.careerjobportal.exception.ResourceNotFoundException;
import com.hexaware.careerjobportal.services.IEmployerProfileService;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api/employer-profile")
public class EmployerProfileRestController {

    @Autowired
    private IEmployerProfileService employerProfileService;

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateEmployerProfileByUserId(@PathVariable Integer userId, @Valid @RequestBody EmployerProfileDTO dto) {
        EmployerProfile updatedProfile = employerProfileService.updateEmployerProfileByUserId(userId, dto);
        return ResponseEntity.ok("Employer profile updated successfully for user id: " + userId);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/view/{userId}") //done
    public ResponseEntity<?> getEmployerProfileByUserId(@PathVariable Integer userId) {
        EmployerProfile profile = employerProfileService.getEmployerProfileByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employer profile not found with id: " + userId));

        EmployerProfileDTO dto = new EmployerProfileDTO();
        dto.setId(profile.getId());
        dto.setUserId(userId);
        dto.setCompanyName(profile.getCompanyName());
        dto.setIndustry(profile.getIndustry());
        dto.setLocation(profile.getLocation());
        dto.setContactInfo(profile.getContactInfo());
        dto.setDescription(profile.getDescription());
        

        return ResponseEntity.ok(dto);
    }

}
