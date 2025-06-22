package com.hexaware.careerjobportal.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "job_postings")
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "job_description", nullable = false, columnDefinition = "TEXT")
    private String jobDescription;

    @Column(name = "qualifications", nullable = false)
    private String qualifications;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "salary")
    private String salary;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false)
    private JobType employmentType;

    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private EmployerProfile employerProfile;

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    public JobPosting() {}

    // Getters and setters

    public int getId() { return id; }

    public String getJobTitle() { return title; }
    public void setJobTitle(String jobTitle) { this.title = jobTitle; }

    public String getJobDescription() { return jobDescription; }
    public void setJobDescription(String jobDescription) { this.jobDescription = jobDescription; }

    public String getQualifications() { return qualifications; }
    public void setQualifications(String qualifications) { this.qualifications = qualifications; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public JobType getEmploymentType() { return employmentType; }
    public void setEmploymentType(JobType employmentType) { this.employmentType = employmentType; }

    public LocalDate getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    public EmployerProfile getEmployerProfile() { return employerProfile; }
    public void setEmployerProfile(EmployerProfile employerProfile) { this.employerProfile = employerProfile; }

    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }
}
