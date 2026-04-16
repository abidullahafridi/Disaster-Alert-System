package com.disaster.disaster_alert_system.service;

import com.disaster.disaster_alert_system.model.entity.*;
import com.disaster.disaster_alert_system.model.enums.*;
import com.disaster.disaster_alert_system.exception.*;
import com.disaster.disaster_alert_system.repository.*;
import com.disaster.disaster_alert_system.service.thread.VolunteerThread;
import com.disaster.disaster_alert_system.service.thread.AssignmentManagerThread;
import com.disaster.disaster_alert_system.model.inheritance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Transactional
public class DisasterService {
    
    @Autowired
    private DisasterRepository disasterRepository;
    
    @Autowired
    private VolunteerRepository volunteerRepository;
    
    @Autowired
    private ThresholdRuleRepository thresholdRuleRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    // Use @Lazy to break circular dependency
    @Autowired
  
    private AssignmentManagerThread assignmentManager;
    
    // Thread-safe collections (SCD Concept: Generics & Synchronization)
    private final Map<Long, VolunteerThread> activeVolunteerThreads = new ConcurrentHashMap<>();
    private final ReentrantLock assignmentLock = new ReentrantLock();
    
    // Create Disaster
    public Disaster createDisaster(Disaster disaster) {
        if (disaster.getStatus() == null) {
            disaster.setStatus(Status.PENDING);
        }
        return disasterRepository.save(disaster);
    }
    
    // Process Pending Disasters
    public void processPendingDisasters() {
        List<Disaster> pendingDisasters = disasterRepository.findByStatus(Status.PENDING);
        
        for (Disaster disaster : pendingDisasters) {
            try {
                assignVolunteersToDisaster(disaster);
                disaster.setStatus(Status.ACTIVE);
                disasterRepository.save(disaster);
                System.out.println("Disaster " + disaster.getId() + " activated and volunteers assigned");
            } catch (Exception e) {
                System.err.println("Failed to process disaster " + disaster.getId() + ": " + e.getMessage());
            }
        }
    }
    
    // Assign Volunteers based on Threshold Rules (SCD Concept: Complex Logic)
    public void assignVolunteersToDisaster(Disaster disaster) {
        assignmentLock.lock();
        try {
            // Get threshold rule for this disaster type and severity
            ThresholdRule rule = thresholdRuleRepository
                .findByDisasterTypeAndSeverity(disaster.getType(), disaster.getSeverity())
                .orElseThrow(() -> new InvalidThresholdException(
                    disaster.getType().name(), 
                    disaster.getSeverity().name()
                ));
            
            // Assign volunteers by category
            assignVolunteersByCategory(disaster, VolunteerCategory.RESCUE, rule.getRescueRequired());
            assignVolunteersByCategory(disaster, VolunteerCategory.MEDICAL, rule.getMedicalRequired());
            assignVolunteersByCategory(disaster, VolunteerCategory.SUPPLY, rule.getSupplyRequired());
            assignVolunteersByCategory(disaster, VolunteerCategory.ENGINEER, rule.getEngineerRequired());
            
        } finally {
            assignmentLock.unlock();
        }
    }
    
    private void assignVolunteersByCategory(Disaster disaster, VolunteerCategory category, Integer required) {
        if (required <= 0) return;
        
        // Get available volunteers for this category
        List<com.disaster.disaster_alert_system.model.entity.Volunteer> availableVolunteers = volunteerRepository
            .findByCategoryAndStatus(category, Status.AVAILABLE);
        
        if (availableVolunteers.size() < required) {
            throw new NoVolunteerAvailableException(category.getDisplayName(), required);
        }
        
        // Sort by experience (highest first)
        availableVolunteers.sort((v1, v2) -> v2.getExperienceLevel().compareTo(v1.getExperienceLevel()));
        
        // Assign required number of volunteers
        for (int i = 0; i < required; i++) {
            com.disaster.disaster_alert_system.model.entity.Volunteer volunteer = availableVolunteers.get(i);
            createAssignment(disaster, volunteer);
            
            // Create and start volunteer thread
            startVolunteerThread(volunteer);
        }
    }
    
    private void createAssignment(Disaster disaster, com.disaster.disaster_alert_system.model.entity.Volunteer volunteer) {
        Assignment assignment = new Assignment();
        assignment.setDisaster(disaster);
        assignment.setVolunteer(volunteer);
        assignment.setStartTime(java.time.LocalDateTime.now());
        assignment.setStatus(Status.IN_PROGRESS);
        
        assignmentRepository.save(assignment);
        
        // Update volunteer status
        volunteer.setStatus(Status.ASSIGNED);
        volunteerRepository.save(volunteer);
    }
    
    private void startVolunteerThread(com.disaster.disaster_alert_system.model.entity.Volunteer entityVolunteer) {
        // Convert entity to inheritance model
        com.disaster.disaster_alert_system.model.inheritance.Volunteer volunteer = 
            createInheritanceVolunteer(entityVolunteer);
        
        VolunteerThread thread = new VolunteerThread(volunteer);
        activeVolunteerThreads.put(entityVolunteer.getId(), thread);
        thread.start();
        
        // Assign task after a short delay
        assignmentManager.submitTask(() -> {
            try {
                Thread.sleep(1000);
                thread.assignTask();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    private com.disaster.disaster_alert_system.model.inheritance.Volunteer 
        createInheritanceVolunteer(com.disaster.disaster_alert_system.model.entity.Volunteer entity) {
        
        switch (entity.getCategory()) {
            case RESCUE:
                return new RescueVolunteer(entity.getName(), entity.getExperienceLevel());
            case MEDICAL:
                return new MedicalVolunteer(entity.getName(), entity.getExperienceLevel());
            case SUPPLY:
                return new SupplyVolunteer(entity.getName(), entity.getExperienceLevel());
            case ENGINEER:
                return new RescueVolunteer(entity.getName(), entity.getExperienceLevel()); // Using Rescue as base
            default:
                return new RescueVolunteer(entity.getName(), entity.getExperienceLevel());
        }
    }
    
    // Complete Disaster
    public void completeDisaster(Long disasterId) {
        assignmentLock.lock();
        try {
            Disaster disaster = disasterRepository.findById(disasterId)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));
            
            disaster.setStatus(Status.RESOLVED);
            disasterRepository.save(disaster);
            
            // Stop all volunteer threads for this disaster
            stopVolunteerThreadsForDisaster(disasterId);
            
        } finally {
            assignmentLock.unlock();
        }
    }
    
    private void stopVolunteerThreadsForDisaster(Long disasterId) {
        Disaster disaster = disasterRepository.findById(disasterId).orElse(null);
        if (disaster == null) return;
        
        List<Assignment> assignments = assignmentRepository.findByDisaster(disaster);
        
        for (Assignment assignment : assignments) {
            VolunteerThread thread = activeVolunteerThreads.get(assignment.getVolunteer().getId());
            if (thread != null) {
                thread.stopThread();
                activeVolunteerThreads.remove(assignment.getVolunteer().getId());
            }
            
            // Update assignment status
            assignment.setStatus(Status.COMPLETED);
            assignment.setEndTime(java.time.LocalDateTime.now());
            assignmentRepository.save(assignment);
            
            // Update volunteer status
            com.disaster.disaster_alert_system.model.entity.Volunteer volunteer = assignment.getVolunteer();
            volunteer.setStatus(Status.AVAILABLE);
            volunteerRepository.save(volunteer);
        }
    }
    
    // Get Statistics
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalDisasters", disasterRepository.count());
        stats.put("activeDisasters", disasterRepository.countByStatus(Status.ACTIVE));
        stats.put("pendingDisasters", disasterRepository.countByStatus(Status.PENDING));
        
        stats.put("totalVolunteers", volunteerRepository.count());
        stats.put("availableVolunteers", volunteerRepository.findByStatus(Status.AVAILABLE).size());
        stats.put("assignedVolunteers", volunteerRepository.findByStatus(Status.ASSIGNED).size());
        
        stats.put("activeThreads", activeVolunteerThreads.size());
        
        return stats;
    }
    
    // Get all disasters
    public List<Disaster> getAllDisasters() {
        return disasterRepository.findAll();
    }
    
    // Get active disasters
    public List<Disaster> getActiveDisasters() {
        return disasterRepository.findActiveDisasters();
    }


    // Add to DisasterService.java
public List<Disaster> getPendingDisasters() {
    return disasterRepository.findByStatus(Status.PENDING);
}

public int getActiveThreadCount() {
    return activeVolunteerThreads.size();
}


}