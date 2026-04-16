package com.disaster.disaster_alert_system.exception;

public class NoVolunteerAvailableException extends RuntimeException {
    
    public NoVolunteerAvailableException(String message) {
        super(message);
    }
    
    public NoVolunteerAvailableException(String category, Integer required) {
        super("Not enough " + category + " volunteers available. Required: " + required);
    }
}