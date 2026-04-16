package com.disaster.disaster_alert_system.model.inheritance;

import com.disaster.disaster_alert_system.model.enums.VolunteerCategory;
import com.disaster.disaster_alert_system.model.enums.Status;

public class RescueVolunteer extends Volunteer {
    
    public RescueVolunteer(String name, Integer experienceLevel) {
        super(name, VolunteerCategory.RESCUE, experienceLevel);
    }
    
    @Override
    public String performTask() {
        return "Performing search and rescue operations - evacuating people from danger zone";
    }
    
    @Override
    public Integer getTaskDuration() {
        // Higher experience = faster completion
        return 30 - (experienceLevel * 5); // 25-5 seconds
    }
    
    // Rescue-specific method
    public String performFirstAid() {
        return "Providing emergency first aid to injured persons";
    }
}