package com.disaster.disaster_alert_system.model.enums;

public enum VolunteerCategory {
    RESCUE("Rescue Volunteer", "Handles search and rescue operations"),
    MEDICAL("Medical Volunteer", "Provides medical assistance"),
    SUPPLY("Supply Volunteer", "Manages and distributes supplies"),
    ENGINEER("Engineer Volunteer", "Technical support and infrastructure");
    
    private final String displayName;
    private final String description;
    
    VolunteerCategory(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
}