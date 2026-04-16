package com.disaster.disaster_alert_system.model.entity;

import com.disaster.disaster_alert_system.model.enums.VolunteerCategory;
import com.disaster.disaster_alert_system.model.enums.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "volunteers")
public class Volunteer extends BaseEntity {
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VolunteerCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.AVAILABLE;
    
    @Column(name = "experience_level")
    private Integer experienceLevel = 1; // 1-5
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "email")
    private String email;
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public VolunteerCategory getCategory() {
        return category;
    }
    
    public void setCategory(VolunteerCategory category) {
        this.category = category;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Integer getExperienceLevel() {
        return experienceLevel;
    }
    
    public void setExperienceLevel(Integer experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}