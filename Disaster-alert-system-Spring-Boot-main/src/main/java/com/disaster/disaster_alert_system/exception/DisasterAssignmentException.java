package com.disaster.disaster_alert_system.exception;

public class DisasterAssignmentException extends RuntimeException {
    
    public DisasterAssignmentException(String message) {
        super(message);
    }
    
    public DisasterAssignmentException(Long disasterId, String reason) {
        super("Failed to assign volunteers to Disaster ID: " + disasterId + ". Reason: " + reason);
    }
}