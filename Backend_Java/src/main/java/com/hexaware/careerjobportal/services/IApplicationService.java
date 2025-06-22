package com.hexaware.careerjobportal.services;

import java.util.List;

import com.hexaware.careerjobportal.dto.ApplicationRequestDTO;
import com.hexaware.careerjobportal.dto.ApplicationResponseDTO;

public interface IApplicationService {

    ApplicationResponseDTO applyForJob(ApplicationRequestDTO applicationRequest);

    List<ApplicationResponseDTO> getApplicationsByJobSeeker(Integer seekerId);

    List<ApplicationResponseDTO> getApplicationsByJobId(Integer jobId);

    void updateApplicationStatus(Integer applicationId, String newStatus);

    void deleteApplication(Integer applicationId);
}
