package com.disaster.disaster_alert_system.model.entity;

import com.disaster.disaster_alert_system.model.enums.DisasterType;
import com.disaster.disaster_alert_system.model.enums.Severity;
import com.disaster.disaster_alert_system.model.enums.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "disasters")
public class Disaster extends BaseEntity {
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisasterType type;
    
    @Column(nullable = false)
    private String location;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;
    
    private String description;
    
    @Column(name = "estimated_casualties")
    private Integer estimatedCasualties;
    
    // Getters and Setters
    public DisasterType getType() {
        return type;
    }
    
    public void setType(DisasterType type) {
        this.type = type;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Severity getSeverity() {
        return severity;
    }
    
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getEstimatedCasualties() {
        return estimatedCasualties;
    }
    
    public void setEstimatedCasualties(Integer estimatedCasualties) {
        this.estimatedCasualties = estimatedCasualties;
    }
}