package com.disaster.disaster_alert_system.model.inheritance;

import com.disaster.disaster_alert_system.model.enums.VolunteerCategory;
import com.disaster.disaster_alert_system.model.enums.Status;

public class MedicalVolunteer extends Volunteer {
    
    public MedicalVolunteer(String name, Integer experienceLevel) {
        super(name, VolunteerCategory.MEDICAL, experienceLevel);
    }
    
    @Override
    public String performTask() {
        return "Providing medical treatment and emergency care to victims";
    }
    
    @Override
    public Integer getTaskDuration() {
        return 40 - (experienceLevel * 6); // 34-10 seconds
    }
    
    // Medical-specific method
    public String setupFieldHospital() {
        return "Setting up temporary field hospital for mass casualties";
    }
}