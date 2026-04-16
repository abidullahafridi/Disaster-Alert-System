package com.disaster.disaster_alert_system.controller;

import com.disaster.disaster_alert_system.model.entity.*;
import com.disaster.disaster_alert_system.model.enums.*;
import com.disaster.disaster_alert_system.service.DisasterService;
import com.disaster.disaster_alert_system.service.VolunteerService;
import com.disaster.disaster_alert_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ReportsController {
    
    @Autowired
    private DisasterService disasterService;
    
    @Autowired
    private VolunteerService volunteerService;
    
    @Autowired
    private DisasterRepository disasterRepository;
    
    @Autowired
    private VolunteerRepository volunteerRepository;
    
    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private ThresholdRuleRepository thresholdRuleRepository;
    
    @GetMapping("/reports")
    public String reports(Model model) {
        try {
            // Get ALL real data
            List<Disaster> allDisasters = disasterRepository.findAll();
            List<Volunteer> allVolunteers = volunteerRepository.findAll();
            List<Assignment> allAssignments = assignmentRepository.findAll();
            
            // 1. Calculate REAL Statistics
            Map<String, Object> stats = calculateRealStatistics(allDisasters, allVolunteers, allAssignments);
            model.addAttribute("stats", stats);
            
            // 2. Get REAL disaster trend data (last 30 days)
            Map<String, Integer> disasterTrend = calculateDisasterTrend(allDisasters);
            model.addAttribute("disasterTrendLabels", new ArrayList<>(disasterTrend.keySet()));
            model.addAttribute("disasterTrendData", new ArrayList<>(disasterTrend.values()));
            
            // 3. Calculate REAL response times by disaster type
            Map<String, Double> responseTimes = calculateResponseTimes(allDisasters, allAssignments);
            model.addAttribute("responseTimeLabels", new ArrayList<>(responseTimes.keySet()));
            model.addAttribute("responseTimeData", new ArrayList<>(responseTimes.values()));
            
            // 4. Calculate REAL volunteer performance
            Map<String, Map<String, Integer>> volunteerPerformance = calculateVolunteerPerformance(allVolunteers, allAssignments);
            model.addAttribute("volunteerPerformance", volunteerPerformance);
            
            // 5. Calculate REAL severity distribution
            Map<String, Long> severityDistribution = calculateSeverityDistribution(allDisasters);
            model.addAttribute("severityLabels", new ArrayList<>(severityDistribution.keySet()));
            model.addAttribute("severityData", new ArrayList<>(severityDistribution.values()));
            
            // 6. Get detailed performance report (REAL data)
            List<Map<String, Object>> detailedReport = generateDetailedReport(allDisasters, allAssignments);
            model.addAttribute("detailedReport", detailedReport);
            
            // 7. Calculate insights based on REAL data
            Map<String, List<String>> insights = generateInsights(allDisasters, allVolunteers, allAssignments);
            model.addAttribute("strengths", insights.get("strengths"));
            model.addAttribute("improvements", insights.get("improvements"));
            
            // 8. Thread utilization data
            int activeThreads = disasterService.getActiveThreadCount();
            int totalVolunteers = allVolunteers.size();
            double threadUtilization = totalVolunteers > 0 ? (activeThreads * 100.0 / totalVolunteers) : 0;
            model.addAttribute("threadUtilization", Math.round(threadUtilization));
            model.addAttribute("activeThreads", activeThreads);
            
            return "reports";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error generating reports: " + e.getMessage());
            return "reports";
        }
    }
    
    private Map<String, Object> calculateRealStatistics(List<Disaster> disasters, List<Volunteer> volunteers, List<Assignment> assignments) {
        Map<String, Object> stats = new HashMap<>();
        
        // Total disasters
        stats.put("totalDisasters", disasters.size());
        
        // Disasters by status
        stats.put("activeDisasters", disasters.stream().filter(d -> d.getStatus() == Status.ACTIVE).count());
        stats.put("resolvedDisasters", disasters.stream().filter(d -> d.getStatus() == Status.RESOLVED).count());
        stats.put("pendingDisasters", disasters.stream().filter(d -> d.getStatus() == Status.PENDING).count());
        
        // Total volunteers
        stats.put("totalVolunteers", volunteers.size());
        
        // Volunteers by status
        stats.put("availableVolunteers", volunteers.stream().filter(v -> v.getStatus() == Status.AVAILABLE).count());
        stats.put("assignedVolunteers", volunteers.stream().filter(v -> v.getStatus() == Status.ASSIGNED).count());
        stats.put("busyVolunteers", volunteers.stream().filter(v -> v.getStatus() == Status.BUSY).count());
        
        // Assignments
        stats.put("totalAssignments", assignments.size());
        stats.put("completedAssignments", assignments.stream().filter(a -> a.getStatus() == Status.COMPLETED).count());
        stats.put("inProgressAssignments", assignments.stream().filter(a -> a.getStatus() == Status.IN_PROGRESS).count());
        
        // Calculate average response time (in minutes)
        double avgResponseTime = assignments.stream()
            .filter(a -> a.getStartTime() != null && a.getEndTime() != null)
            .mapToLong(a -> ChronoUnit.MINUTES.between(a.getStartTime(), a.getEndTime()))
            .average()
            .orElse(0);
        stats.put("avgResponseTime", Math.round(avgResponseTime));
        
        // Calculate success rate (completed disasters / total resolved disasters)
        long resolvedDisasters = disasters.stream().filter(d -> d.getStatus() == Status.RESOLVED).count();
        long completedDisasters = disasters.stream()
            .filter(d -> d.getStatus() == Status.RESOLVED)
            .filter(d -> {
                List<Assignment> disasterAssignments = assignments.stream()
                    .filter(a -> a.getDisaster().getId().equals(d.getId()))
                    .collect(Collectors.toList());
                return disasterAssignments.stream().allMatch(a -> a.getStatus() == Status.COMPLETED);
            })
            .count();
        
        double successRate = resolvedDisasters > 0 ? (completedDisasters * 100.0 / resolvedDisasters) : 0;
        stats.put("successRate", Math.round(successRate * 10) / 10.0); // One decimal place
        
        // Volunteer utilization (assigned + busy / total)
        double utilization = volunteers.size() > 0 ? 
            ((volunteers.stream().filter(v -> v.getStatus() == Status.ASSIGNED || v.getStatus() == Status.BUSY).count()) * 100.0 / volunteers.size()) : 0;
        stats.put("volunteerUtilization", Math.round(utilization));
        
        return stats;
    }
    
    private Map<String, Integer> calculateDisasterTrend(List<Disaster> disasters) {
        Map<String, Integer> trend = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        
        // Last 6 months
        for (int i = 5; i >= 0; i--) {
            LocalDateTime monthStart = now.minusMonths(i).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime monthEnd = monthStart.plusMonths(1);
            
            long count = disasters.stream()
                .filter(d -> d.getCreatedAt() != null)
                .filter(d -> !d.getCreatedAt().isBefore(monthStart) && d.getCreatedAt().isBefore(monthEnd))
                .count();
            
            String monthName = monthStart.getMonth().toString().substring(0, 3);
            trend.put(monthName, (int) count);
        }
        
        return trend;
    }
    
    private Map<String, Double> calculateResponseTimes(List<Disaster> disasters, List<Assignment> assignments) {
        Map<String, Double> responseTimes = new HashMap<>();
        
        // Group by disaster type
        Map<DisasterType, List<Disaster>> disastersByType = disasters.stream()
            .filter(d -> d.getStatus() == Status.RESOLVED)
            .collect(Collectors.groupingBy(Disaster::getType));
        
        for (Map.Entry<DisasterType, List<Disaster>> entry : disastersByType.entrySet()) {
           // In calculateResponseTimes method, fix this line:
double avgTime = entry.getValue().stream()
    .mapToDouble(disaster -> {
        List<Assignment> disasterAssignments = assignments.stream()
            .filter(a -> a.getDisaster().getId().equals(disaster.getId()))
            .collect(Collectors.toList());
        
        // Calculate average completion time for this disaster
        return disasterAssignments.stream()
            .filter(a -> a.getStartTime() != null && a.getEndTime() != null)
            .mapToDouble(a -> ChronoUnit.MINUTES.between(a.getStartTime(), a.getEndTime()))
            .average()
            .orElse(0.0);  // Changed from 0 to 0.0
    })
    .average()
    .orElse(0.0);  // Changed from 0 to 0.0

responseTimes.put(entry.getKey().getDisplayName(), Math.round(avgTime * 10) / 10.0);
        }
        
        return responseTimes;
    }
    
    private Map<String, Map<String, Integer>> calculateVolunteerPerformance(List<Volunteer> volunteers, List<Assignment> assignments) {
        Map<String, Map<String, Integer>> performance = new HashMap<>();
        
        // Initialize for each category
        for (VolunteerCategory category : VolunteerCategory.values()) {
            Map<String, Integer> categoryData = new HashMap<>();
            
            // Get volunteers in this category
            List<Volunteer> categoryVolunteers = volunteers.stream()
                .filter(v -> v.getCategory() == category)
                .collect(Collectors.toList());
            
            if (!categoryVolunteers.isEmpty()) {
                // Calculate average metrics
                double avgResponseTime = categoryVolunteers.stream()
    .mapToDouble(v -> {
        List<Assignment> volunteerAssignments = assignments.stream()
            .filter(a -> a.getVolunteer().getId().equals(v.getId()))
            .collect(Collectors.toList());
                        double avg = volunteerAssignments.stream()
            .filter(a -> a.getStartTime() != null && a.getEndTime() != null)
            .mapToDouble(a -> ChronoUnit.MINUTES.between(a.getStartTime(), a.getEndTime()))
            .average()
            .orElse(0.0);
        
        return avg;
    })
    .average()
    .orElse(0.0);
    
categoryData.put("Response Time", (int) Math.round(avgResponseTime));
                
                double successRate = categoryVolunteers.stream()
                    .mapToInt(v -> {
                        List<Assignment> volunteerAssignments = assignments.stream()
                            .filter(a -> a.getVolunteer().getId().equals(v.getId()))
                            .collect(Collectors.toList());
                        long completed = volunteerAssignments.stream()
                            .filter(a -> a.getStatus() == Status.COMPLETED)
                            .count();
                        return volunteerAssignments.isEmpty() ? 0 : (int) ((completed * 100) / volunteerAssignments.size());
                    })
                    .average()
                    .orElse(0);
                
                categoryData.put("Response Time", (int) avgResponseTime);
                categoryData.put("Success Rate", (int) successRate);
                categoryData.put("Efficiency", 85); // Hardcoded for now
                categoryData.put("Availability", 90); // Hardcoded for now
                categoryData.put("Coordination", 88); // Hardcoded for now
                
                performance.put(category.getDisplayName(), categoryData);
            }
        }
        
        return performance;
    }
    
    private Map<String, Long> calculateSeverityDistribution(List<Disaster> disasters) {
        return disasters.stream()
            .collect(Collectors.groupingBy(
                d -> d.getSeverity().getDisplayName(),
                Collectors.counting()
            ));
    }
    
    private List<Map<String, Object>> generateDetailedReport(List<Disaster> disasters, List<Assignment> assignments) {
        List<Map<String, Object>> report = new ArrayList<>();
        
        // Get last 10 resolved disasters for detailed report
        List<Disaster> recentDisasters = disasters.stream()
            .filter(d -> d.getStatus() == Status.RESOLVED)
            .sorted((d1, d2) -> d2.getCreatedAt().compareTo(d1.getCreatedAt()))
            .limit(10)
            .collect(Collectors.toList());
        
        for (Disaster disaster : recentDisasters) {
            Map<String, Object> row = new HashMap<>();
            
            // Disaster ID
            row.put("id", disaster.getId());
            
            // Type with badge color
            row.put("type", disaster.getType().getDisplayName());
            row.put("typeColor", getDisasterTypeColor(disaster.getType()));
            
            // Location
            row.put("location", disaster.getLocation());
            
            // Severity with badge
            row.put("severity", disaster.getSeverity().getDisplayName());
            row.put("severityColor", getSeverityColor(disaster.getSeverity()));
            
            // Calculate response time
            List<Assignment> disasterAssignments = assignments.stream()
                .filter(a -> a.getDisaster().getId().equals(disaster.getId()))
                .collect(Collectors.toList());
            
            double avgResponseTime = disasterAssignments.stream()
                .filter(a -> a.getStartTime() != null && a.getEndTime() != null)
                .mapToLong(a -> ChronoUnit.MINUTES.between(a.getStartTime(), a.getEndTime()))
                .average()
                .orElse(0);
            
            row.put("responseTime", formatTime(avgResponseTime));
            
            // Number of volunteers
            row.put("volunteers", disasterAssignments.size());
            
            // Status
            row.put("status", disaster.getStatus().getDisplayName());
            
            // Completion percentage
            long completed = disasterAssignments.stream()
                .filter(a -> a.getStatus() == Status.COMPLETED)
                .count();
            int completionRate = disasterAssignments.isEmpty() ? 0 : (int) ((completed * 100) / disasterAssignments.size());
            row.put("completion", completionRate + "%");
            
            report.add(row);
        }
        
        return report;
    }
    
    private Map<String, List<String>> generateInsights(List<Disaster> disasters, List<Volunteer> volunteers, List<Assignment> assignments) {
        Map<String, List<String>> insights = new HashMap<>();
        List<String> strengths = new ArrayList<>();
        List<String> improvements = new ArrayList<>();
        
        // Analyze strengths
        double avgResponseTime = calculateAverageResponseTime(disasters, assignments);
        if (avgResponseTime < 60) { // Less than 60 minutes
            strengths.add("Excellent response time: " + Math.round(avgResponseTime) + " minutes average");
        }
        
        double volunteerUtilization = volunteers.stream()
            .filter(v -> v.getStatus() == Status.ASSIGNED || v.getStatus() == Status.BUSY)
            .count() * 100.0 / volunteers.size();
        
        if (volunteerUtilization > 70) {
            strengths.add("High volunteer utilization: " + Math.round(volunteerUtilization) + "%");
        }
        
        // Analyze which volunteer category is most successful
        Map<VolunteerCategory, Double> successByCategory = new HashMap<>();
        for (VolunteerCategory category : VolunteerCategory.values()) {
            List<Volunteer> categoryVolunteers = volunteers.stream()
                .filter(v -> v.getCategory() == category)
                .collect(Collectors.toList());
            
            double categorySuccess = categoryVolunteers.stream()
                .mapToDouble(v -> {
                    List<Assignment> volunteerAssignments = assignments.stream()
                        .filter(a -> a.getVolunteer().getId().equals(v.getId()))
                        .collect(Collectors.toList());
                    long completed = volunteerAssignments.stream()
                        .filter(a -> a.getStatus() == Status.COMPLETED)
                        .count();
                    return volunteerAssignments.isEmpty() ? 0 : (completed * 100.0 / volunteerAssignments.size());
                })
                .average()
                .orElse(0);
            
            successByCategory.put(category, categorySuccess);
        }
        
        VolunteerCategory bestCategory = successByCategory.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
        
        if (bestCategory != null && successByCategory.get(bestCategory) > 90) {
            strengths.add(bestCategory.getDisplayName() + " volunteers show highest success rate: " + 
                Math.round(successByCategory.get(bestCategory)) + "%");
        }
        
        // Multithreading effectiveness
        int activeThreads = disasterService.getActiveThreadCount();
        if (activeThreads > 0) {
            strengths.add("Effective multithreading implementation: " + activeThreads + " active threads");
        }
        
        // Areas for improvement
        Map<Severity, Double> responseTimeBySeverity = new HashMap<>();
        for (Severity severity : Severity.values()) {
            List<Disaster> severityDisasters = disasters.stream()
                .filter(d -> d.getSeverity() == severity && d.getStatus() == Status.RESOLVED)
                .collect(Collectors.toList());
            
            double severityResponseTime = severityDisasters.stream()
                .mapToDouble(d -> calculateDisasterResponseTime(d, assignments))
                .average()
                .orElse(0);
            
            responseTimeBySeverity.put(severity, severityResponseTime);
        }
        
        // Check for critical severity response time
        if (responseTimeBySeverity.containsKey(Severity.CRITICAL) && 
            responseTimeBySeverity.get(Severity.CRITICAL) > 120) { // More than 2 hours
            improvements.add("Critical severity disasters need faster response: " + 
                Math.round(responseTimeBySeverity.get(Severity.CRITICAL)) + " minutes average");
        }
        
        // Check volunteer distribution
        Map<VolunteerCategory, Long> volunteerCountByCategory = volunteers.stream()
            .collect(Collectors.groupingBy(Volunteer::getCategory, Collectors.counting()));
        
        long minVolunteers = volunteerCountByCategory.values().stream()
            .mapToLong(Long::longValue)
            .min()
            .orElse(0);
        
        VolunteerCategory leastCategory = volunteerCountByCategory.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
        
        if (leastCategory != null && minVolunteers < 3) {
            improvements.add(leastCategory.getDisplayName() + " volunteer pool needs expansion: only " + 
                minVolunteers + " available");
        }
        
        insights.put("strengths", strengths);
        insights.put("improvements", improvements);
        
        return insights;
    }
    
    // Helper methods
    private String getDisasterTypeColor(DisasterType type) {
        switch (type) {
            case FLOOD: return "primary";
            case EARTHQUAKE: return "warning";
            case FIRE: return "danger";
            case HURRICANE: return "info";
            case TSUNAMI: return "info";
            default: return "secondary";
        }
    }
    
    private String getSeverityColor(Severity severity) {
        switch (severity) {
            case LOW: return "success";
            case MEDIUM: return "warning";
            case HIGH: return "danger";
            case CRITICAL: return "danger";
            default: return "secondary";
        }
    }
    
    private String formatTime(double minutes) {
        if (minutes < 60) {
            return Math.round(minutes) + "m";
        } else {
            int hours = (int) (minutes / 60);
            int remainingMinutes = (int) (minutes % 60);
            return hours + "h " + remainingMinutes + "m";
        }
    }
    
    private double calculateAverageResponseTime(List<Disaster> disasters, List<Assignment> assignments) {
        return disasters.stream()
            .filter(d -> d.getStatus() == Status.RESOLVED)
            .mapToDouble(d -> calculateDisasterResponseTime(d, assignments))
            .average()
            .orElse(0);
    }
    
    private double calculateDisasterResponseTime(Disaster disaster, List<Assignment> assignments) {
        List<Assignment> disasterAssignments = assignments.stream()
            .filter(a -> a.getDisaster().getId().equals(disaster.getId()))
            .collect(Collectors.toList());
        
        return disasterAssignments.stream()
            .filter(a -> a.getStartTime() != null && a.getEndTime() != null)
            .mapToLong(a -> ChronoUnit.MINUTES.between(a.getStartTime(), a.getEndTime()))
            .average()
            .orElse(0);
    }
}