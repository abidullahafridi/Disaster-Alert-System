package com.disaster.disaster_alert_system.model.enums;

public enum Status {
    // Volunteer Status
    AVAILABLE("Available"),
    ASSIGNED("Assigned"),
    BUSY("Busy"),
    RESTING("Resting"),
    
    // Disaster Status
    PENDING("Pending"),
    ACTIVE("Active"),
    RESOLVED("Resolved"),
    CANCELLED("Cancelled"),
    
    // Assignment Status
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    FAILED("Failed");
    
    private final String displayName;
    
    Status(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}