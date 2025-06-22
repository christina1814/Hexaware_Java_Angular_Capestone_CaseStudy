package com.hexaware.careerjobportal.dto;

import com.hexaware.careerjobportal.entities.ProficiencyLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SkillDTO 
{

    @NotBlank(message = "Skill name is required")
    private String name;

    private ProficiencyLevel proficiencyLevel;

    @NotNull(message = "Job Seeker ID is required")
    private Integer jobSeekerId;

    // Getters and Setters
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public Integer getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Integer jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }
}

