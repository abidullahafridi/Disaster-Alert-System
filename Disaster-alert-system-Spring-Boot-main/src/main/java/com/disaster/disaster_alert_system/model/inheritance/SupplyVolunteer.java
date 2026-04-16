package com.disaster.disaster_alert_system.model.inheritance;

import com.disaster.disaster_alert_system.model.enums.VolunteerCategory;
import com.disaster.disaster_alert_system.model.enums.Status;

public class SupplyVolunteer extends Volunteer {
    
    public SupplyVolunteer(String name, Integer experienceLevel) {
        super(name, VolunteerCategory.SUPPLY, experienceLevel);
    }
    
    @Override
    public String performTask() {
        return "Distributing food, water, and essential supplies to affected areas";
    }
    
    @Override
    public Integer getTaskDuration() {
        return 50 - (experienceLevel * 7); // 43-15 seconds
    }
    
    // Supply-specific method
    public String manageInventory() {
        return "Managing and tracking supply inventory in disaster zone";
    }
}