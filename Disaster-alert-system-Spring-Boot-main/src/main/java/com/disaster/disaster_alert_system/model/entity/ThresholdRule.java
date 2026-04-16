package com.disaster.disaster_alert_system.model.entity;

import com.disaster.disaster_alert_system.model.enums.DisasterType;
import com.disaster.disaster_alert_system.model.enums.Severity;
import jakarta.persistence.*;

@Entity
@Table(name = "threshold_rules")
public class ThresholdRule extends BaseEntity {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "disaster_type", nullable = false)
    private DisasterType disasterType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;
    
    @Column(name = "rescue_required", nullable = false)
    private Integer rescueRequired = 0;
    
    @Column(name = "medical_required", nullable = false)
    private Integer medicalRequired = 0;
    
    @Column(name = "supply_required", nullable = false)
    private Integer supplyRequired = 0;
    
    @Column(name = "engineer_required", nullable = false)
    private Integer engineerRequired = 0;
    
    // Getters and Setters
    public DisasterType getDisasterType() {
        return disasterType;
    }
    
    public void setDisasterType(DisasterType disasterType) {
        this.disasterType = disasterType;
    }
    
    public Severity getSeverity() {
        return severity;
    }
    
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
    
    public Integer getRescueRequired() {
        return rescueRequired;
    }
    
    public void setRescueRequired(Integer rescueRequired) {
        this.rescueRequired = rescueRequired;
    }
    
    public Integer getMedicalRequired() {
        return medicalRequired;
    }
    
    public void setMedicalRequired(Integer medicalRequired) {
        this.medicalRequired = medicalRequired;
    }
    
    public Integer getSupplyRequired() {
        return supplyRequired;
    }
    
    public void setSupplyRequired(Integer supplyRequired) {
        this.supplyRequired = supplyRequired;
    }
    
    public Integer getEngineerRequired() {
        return engineerRequired;
    }
    
    public void setEngineerRequired(Integer engineerRequired) {
        this.engineerRequired = engineerRequired;
    }
}