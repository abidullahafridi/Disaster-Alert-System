package com.disaster.disaster_alert_system.model.inheritance;

import com.disaster.disaster_alert_system.model.enums.VolunteerCategory;
import com.disaster.disaster_alert_system.model.enums.Status;

public abstract class Volunteer {
    protected Long id;
    protected String name;
    protected VolunteerCategory category;
    protected Status status;
    protected Integer experienceLevel;
    
    // Constructor
    public Volunteer(String name, VolunteerCategory category, Integer experienceLevel) {
        this.name = name;
        this.category = category;
        this.experienceLevel = experienceLevel;
        this.status = Status.AVAILABLE;
    }
    
    // Abstract method - each volunteer type performs tasks differently
    public abstract String performTask();
    
    public abstract Integer getTaskDuration(); // in seconds
    
    // Common methods
    public void startTask() {
        this.status = Status.BUSY;
        System.out.println(name + " started " + category.getDisplayName() + " task");
    }
    
    public void completeTask() {
        this.status = Status.AVAILABLE;
        System.out.println(name + " completed " + category.getDisplayName() + " task");
    }
    
    // Manual Getters and Setters (since Lombok not working with abstract class)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public VolunteerCategory getCategory() { return category; }
    public void setCategory(VolunteerCategory category) { this.category = category; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public Integer getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(Integer experienceLevel) { this.experienceLevel = experienceLevel; }
}