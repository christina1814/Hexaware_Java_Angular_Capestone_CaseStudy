package com.hexaware.careerjobportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.careerjobportal.entities.JobPosting;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Integer> {

    List<JobPosting> findByEmployerProfileId(Integer employerId);

    List<JobPosting> findByTitleContainingIgnoreCase(String title);

    List<JobPosting> findByLocationContainingIgnoreCase(String location);

    List<JobPosting> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(String title, String location);
}
