package com.hexaware.careerjobportal.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careerjobportal.entities.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> 
{
	List<Skill> findAllByNameIgnoreCase(String name);
}