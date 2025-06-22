package com.hexaware.careerjobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careerjobportal.entities.ProficiencyLevel;
import com.hexaware.careerjobportal.entities.Skill;
import com.hexaware.careerjobportal.repositories.SkillRepository;

import java.util.List;

@Service
public class SkillServiceImpl implements ISkillService {

    @Autowired
    private SkillRepository skillRepo;

    @Override
    public List<Skill> getAllSkills() {
        return skillRepo.findAll();
    }

    @Override
    public List<Skill> getSkillsByName(String name) {
        List<Skill> skills = skillRepo.findAllByNameIgnoreCase(name);
        if (skills.isEmpty()) {
            throw new RuntimeException("No skills found with name: " + name);
        }
        return skills;
    }

    @Override
    public void updateProficiencyLevel(int skillId, ProficiencyLevel level) {
        Skill skill = skillRepo.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + skillId));
        skill.setProficiencyLevel(level);
        skillRepo.save(skill);
    }

    @Override
    public String deleteById(int id) {
        if (!skillRepo.existsById(id)) {
            throw new RuntimeException("Skill not found with ID: " + id);
        }
        skillRepo.deleteById(id);
        return "Skill deleted successfully";
    }
}
