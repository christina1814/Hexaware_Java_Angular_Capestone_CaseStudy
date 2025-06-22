package com.hexaware.careerjobportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.careerjobportal.entities.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    List<Application> findByJobPostingId(Integer jobId);

    List<Application> findByJobSeekerProfileId(Integer jobSeekerProfileId);

    @Query("SELECT a FROM Application a WHERE a.jobPosting.id = :jobId AND a.jobSeekerProfile.id = :jobSeekerId")
    Optional<Application> findByJobAndSeeker(@Param("jobId") Integer jobId, @Param("jobSeekerId") Integer jobSeekerId);
}
