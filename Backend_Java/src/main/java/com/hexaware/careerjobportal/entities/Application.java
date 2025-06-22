package com.hexaware.careerjobportal.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_seeker_id")
    private JobSeekerProfile jobSeekerProfile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_posting_id")
    private JobPosting jobPosting;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "resume_file_name")
    private String resumeFileName;

    public Application() {}

    public Application(int id, JobSeekerProfile jobSeekerProfile, JobPosting jobPosting,
                       LocalDate applicationDate, String status, String resumeFileName) {
        this.id = id;
        this.jobSeekerProfile = jobSeekerProfile;
        this.jobPosting = jobPosting;
        this.applicationDate = applicationDate;
        this.status = status;
        this.resumeFileName = resumeFileName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JobSeekerProfile getJobSeekerProfile() {
        return jobSeekerProfile;
    }

    public void setJobSeekerProfile(JobSeekerProfile jobSeekerProfile) {
        this.jobSeekerProfile = jobSeekerProfile;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResumeFileName() {
        return resumeFileName;
    }

    public void setResumeFileName(String resumeFileName) {
        this.resumeFileName = resumeFileName;
    }
}
