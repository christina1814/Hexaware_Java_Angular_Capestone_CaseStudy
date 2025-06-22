package com.hexaware.careerjobportal.services;

import java.util.List;

import com.hexaware.careerjobportal.dto.SkillDTO;
import com.hexaware.careerjobportal.entities.ProficiencyLevel;
import com.hexaware.careerjobportal.entities.Skill;

public interface ISkillService {
    List<Skill> getAllSkills();
    List<Skill> getSkillsByName(String name);
    String deleteById(int id);
    void updateProficiencyLevel(int skillId, ProficiencyLevel level);

}
