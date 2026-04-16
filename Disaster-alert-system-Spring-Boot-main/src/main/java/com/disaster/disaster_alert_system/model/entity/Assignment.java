package com.disaster.disaster_alert_system.model.entity;

import com.disaster.disaster_alert_system.model.enums.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "assignments")
public class Assignment extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disaster_id", nullable = false)
    private Disaster disaster;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.IN_PROGRESS;
    
    @Column(name = "start_time")
    private java.time.LocalDateTime startTime;
    
    @Column(name = "end_time")
    private java.time.LocalDateTime endTime;
    
    @Column(name = "completion_notes")
    private String completionNotes;
    
    // Getters and Setters
    public Disaster getDisaster() {
        return disaster;
    }
    
    public void setDisaster(Disaster disaster) {
        this.disaster = disaster;
    }
    
    public Volunteer getVolunteer() {
        return volunteer;
    }
    
    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public java.time.LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(java.time.LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public java.time.LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(java.time.LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public String getCompletionNotes() {
        return completionNotes;
    }
    
    public void setCompletionNotes(String completionNotes) {
        this.completionNotes = completionNotes;
    }
}