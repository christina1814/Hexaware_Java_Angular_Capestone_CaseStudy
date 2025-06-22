package com.hexaware.careerjobportal.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employer_profiles")
public class EmployerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "industry", nullable = false)
    private String industry;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToMany(mappedBy = "employerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobPosting> jobPostings;
    
    //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<JobPosting> getJobPostings() {
        return jobPostings;
    }

    public void setJobPostings(List<JobPosting> jobPostings) {
        this.jobPostings = jobPostings;
    }
}
