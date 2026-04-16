package com.disaster.disaster_alert_system.model.enums;

public enum DisasterType {
    FLOOD("Flood"),
    EARTHQUAKE("Earthquake"),
    FIRE("Fire"),
    HURRICANE("Hurricane"),
    TSUNAMI("Tsunami");
    
    private final String displayName;
    
    DisasterType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}