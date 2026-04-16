package com.disaster.disaster_alert_system.exception;

public class InvalidThresholdException extends RuntimeException {
    
    public InvalidThresholdException(String message) {
        super(message);
    }
    
    public InvalidThresholdException(String disasterType, String severity) {
        super("No threshold rule found for Disaster Type: " + disasterType + " and Severity: " + severity);
    }
}