package com.hexaware.careerjobportal.dto;

import jakarta.validation.constraints.NotNull;

public class ApplicationRequestDTO
{

    @NotNull(message = "Job ID is required")
    private Integer jobId;

    @NotNull(message = "Seeker ID is required")
    private Integer seekerId;

    private String resumePath;

    // Getters and Setters
    
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getSeekerId() {
        return seekerId;
    }

    public void setSeekerId(Integer seekerId) {
        this.seekerId = seekerId;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }
}
