package com.hexaware.careerjobportal.dto;

import com.hexaware.careerjobportal.entities.ProficiencyLevel;

import jakarta.validation.constraints.NotNull;

public class UpdateProficiencyDTO
{

    @NotNull(message = "Proficiency level is required")
    private ProficiencyLevel proficiencyLevel;
    
    //Getters and Setters

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
}
