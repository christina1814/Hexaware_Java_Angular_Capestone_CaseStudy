package com.hexaware.careerjobportal.dto;

public class ApplicationResponseDTO 
{

    private Integer applicationId;
    private String jobTitle;
    private String seekerEmail;
    private String status;

    public ApplicationResponseDTO() {}

    public ApplicationResponseDTO(Integer applicationId, String jobTitle, String seekerEmail, String status) 
    {
        this.applicationId = applicationId;
        this.jobTitle = jobTitle;
        this.seekerEmail = seekerEmail;
        this.status = status;
    }

    // Getters and Setters
    
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSeekerEmail() {
        return seekerEmail;
    }

    public void setSeekerEmail(String seekerEmail) {
        this.seekerEmail = seekerEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}