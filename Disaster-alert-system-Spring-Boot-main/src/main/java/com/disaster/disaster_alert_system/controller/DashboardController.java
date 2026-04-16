package com.disaster.disaster_alert_system.controller;

import com.disaster.disaster_alert_system.model.entity.*;
import com.disaster.disaster_alert_system.model.enums.*;
import com.disaster.disaster_alert_system.service.DisasterService;
import com.disaster.disaster_alert_system.service.VolunteerService;
import com.disaster.disaster_alert_system.service.thread.AssignmentManagerThread;
import com.disaster.disaster_alert_system.repository.AssignmentRepository;
import com.disaster.disaster_alert_system.repository.DisasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class DashboardController {
    
    @Autowired
    private DisasterService disasterService;
    
    @Autowired
    private VolunteerService volunteerService;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private DisasterRepository disasterRepository;  // Add this
    
    @Autowired(required = false)
    private AssignmentManagerThread assignmentManager;
    
    @GetMapping("/")
    public String dashboard(Model model) {
        // Get dashboard statistics
        Map<String, Object> stats = disasterService.getDashboardStats();
        model.addAttribute("stats", stats);
        
        // Get active disasters
        List<Disaster> activeDisasters = disasterService.getActiveDisasters();
        model.addAttribute("activeDisasters", activeDisasters);
        
        // Get available volunteers
        List<Volunteer> availableVolunteers = volunteerService.getAvailableVolunteers();
        model.addAttribute("availableVolunteers", availableVolunteers);
        
        // Get pending disasters - use the service method
        List<Disaster> pendingDisasters = disasterService.getPendingDisasters();
        model.addAttribute("pendingDisasters", pendingDisasters);
        
        // Check if simulation is running
        boolean simulationRunning = assignmentManager != null && assignmentManager.isAlive();
        model.addAttribute("simulationRunning", simulationRunning);
        
        // Get active thread count from service
        int activeThreads = disasterService.getActiveThreadCount();
        model.addAttribute("activeThreads", activeThreads);
        
        // Calculate thread utilization
        long totalVolunteers = volunteerService.getAllVolunteers().size();
        double threadUtilization = totalVolunteers > 0 ? 
            (activeThreads * 100.0 / totalVolunteers) : 0;
        model.addAttribute("threadUtilization", Math.round(threadUtilization));
        
        // Get assignments for each active disaster
        Map<Long, List<Assignment>> disasterAssignments = new HashMap<>();
        Map<Long, Integer> disasterProgress = new HashMap<>();
        Map<Long, Integer> assignmentProgress = new HashMap<>();
        Map<Long, String> assignmentEndTime = new HashMap<>();
        Map<Long, String> assignmentTimeLeft = new HashMap<>();
        Map<Long, String> assignmentColor = new HashMap<>();
        Map<Long, String> assignmentThreadId = new HashMap<>();
        
        for (Disaster disaster : activeDisasters) {
            List<Assignment> assignments = assignmentRepository.findByDisaster(disaster);
            disasterAssignments.put(disaster.getId(), assignments);
            
            // Calculate disaster progress
            int totalAssignments = assignments.size();
            int completedAssignments = 0;
            
            for (Assignment assignment : assignments) {
                // Simulate progress based on time elapsed
                LocalDateTime startTime = assignment.getStartTime();
                LocalDateTime now = LocalDateTime.now();
                
                if (startTime != null) {
                    long secondsElapsed = ChronoUnit.SECONDS.between(startTime, now);
                    int duration = getTaskDuration(assignment.getVolunteer());
                    int progress = (int) Math.min(100, (secondsElapsed * 100) / duration);
                    
                    assignmentProgress.put(assignment.getId(), progress);
                    
                    // Calculate end time and time left
                    LocalDateTime endTime = startTime.plusSeconds(duration);
                    assignmentEndTime.put(assignment.getId(), endTime.toString());
                    
                    long secondsLeft = ChronoUnit.SECONDS.between(now, endTime);
                    if (secondsLeft > 0) {
                        long minutes = secondsLeft / 60;
                        long seconds = secondsLeft % 60;
                        assignmentTimeLeft.put(assignment.getId(), 
                            String.format("%dm %02ds", minutes, seconds));
                    } else {
                        assignmentTimeLeft.put(assignment.getId(), "Completed");
                    }
                    
                    // Assign color based on volunteer category
                    assignmentColor.put(assignment.getId(), 
                        getCategoryColor(assignment.getVolunteer().getCategory()));
                    
                    // Thread ID (simulated)
                    assignmentThreadId.put(assignment.getId(), 
                        "Thread-" + assignment.getVolunteer().getCategory() + "-" + assignment.getId());
                    
                    if (progress >= 100) {
                        completedAssignments++;
                    }
                }
            }
            
            // Calculate disaster progress percentage
            if (totalAssignments > 0) {
                int progress = (completedAssignments * 100) / totalAssignments;
                disasterProgress.put(disaster.getId(), progress);
            } else {
                disasterProgress.put(disaster.getId(), 0);
            }
        }
        
        // Add all maps to model
        model.addAttribute("disasterAssignments", disasterAssignments);
        model.addAttribute("disasterProgress", disasterProgress);
        model.addAttribute("assignmentProgress", assignmentProgress);
        model.addAttribute("assignmentEndTime", assignmentEndTime);
        model.addAttribute("assignmentTimeLeft", assignmentTimeLeft);
        model.addAttribute("assignmentColor", assignmentColor);
        model.addAttribute("assignmentThreadId", assignmentThreadId);
        
        // Add additional stats - FIXED: Use assignmentRepository directly
        stats.put("pendingDisasters", pendingDisasters.size());
        stats.put("availableVolunteers", availableVolunteers.size());
        
        // Get counts directly from repository
        List<Volunteer> allVolunteers = volunteerService.getAllVolunteers();
        stats.put("assignedVolunteers", allVolunteers.stream()
            .filter(v -> v.getStatus() == Status.ASSIGNED).count());
        stats.put("busyVolunteers", allVolunteers.stream()
            .filter(v -> v.getStatus() == Status.BUSY).count());
        stats.put("restingVolunteers", allVolunteers.stream()
            .filter(v -> v.getStatus() == Status.RESTING).count());
        
        // Get completed assignments count safely
        try {
            List<Assignment> completedAssignments = assignmentRepository.findByStatus(Status.COMPLETED);
            stats.put("completedAssignments", completedAssignments.size());
        } catch (Exception e) {
            stats.put("completedAssignments", 0);
        }
        
        return "dashboard";
    }
    
    private int getTaskDuration(Volunteer volunteer) {
        // Simulate task duration based on volunteer category and experience
        if (volunteer == null || volunteer.getCategory() == null) {
            return 30;
        }
        
        switch (volunteer.getCategory()) {
            case RESCUE:
                return 30 - (volunteer.getExperienceLevel() * 5); // 25-5 seconds
            case MEDICAL:
                return 40 - (volunteer.getExperienceLevel() * 6); // 34-10 seconds
            case SUPPLY:
                return 50 - (volunteer.getExperienceLevel() * 7); // 43-15 seconds
            case ENGINEER:
                return 35 - (volunteer.getExperienceLevel() * 5); // 30-10 seconds
            default:
                return 30;
        }
    }
    
    private String getCategoryColor(VolunteerCategory category) {
        if (category == null) return "#6c757d";
        
        switch (category) {
            case RESCUE: return "#dc3545";
            case MEDICAL: return "#198754";
            case SUPPLY: return "#0d6efd";
            case ENGINEER: return "#6f42c1";
            default: return "#6c757d";
        }
    }
    
    @GetMapping("/disasters")
    public String disasters(Model model) {
        List<Disaster> allDisasters = disasterService.getAllDisasters();
        model.addAttribute("disasters", allDisasters);
        return "disasters";
    }
    
    @GetMapping("/volunteers")
    public String volunteers(Model model) {
        List<Volunteer> allVolunteers = volunteerService.getAllVolunteers();
        model.addAttribute("volunteers", allVolunteers);
        return "volunteers";
    }
    
    @GetMapping("/simulation")
    public String simulation(Model model) {
        return "simulation";
    }
    
  
}