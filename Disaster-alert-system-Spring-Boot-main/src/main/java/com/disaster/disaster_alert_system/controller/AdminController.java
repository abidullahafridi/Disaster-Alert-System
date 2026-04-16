package com.disaster.disaster_alert_system.controller;

import com.disaster.disaster_alert_system.model.entity.Disaster;
import com.disaster.disaster_alert_system.model.entity.Volunteer;
import com.disaster.disaster_alert_system.model.entity.ThresholdRule;
import com.disaster.disaster_alert_system.service.DisasterService;
import com.disaster.disaster_alert_system.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private DisasterService disasterService;
    
    @Autowired
    private VolunteerService volunteerService;
    
    // Disaster Management
    @GetMapping("/disaster/create")
    public String showCreateDisasterForm(Model model) {
        model.addAttribute("disaster", new Disaster());
        return "admin/create-disaster";
    }
    
    @PostMapping("/disaster/create")
    public String createDisaster(@ModelAttribute Disaster disaster, RedirectAttributes redirectAttributes) {
        try {
            disasterService.createDisaster(disaster);
            redirectAttributes.addFlashAttribute("success", "Disaster created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating disaster: " + e.getMessage());
        }
        return "redirect:/admin/disaster/create";
    }
    
    @PostMapping("/disaster/{id}/complete")
    public String completeDisaster(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            disasterService.completeDisaster(id);
            redirectAttributes.addFlashAttribute("success", "Disaster marked as completed!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error completing disaster: " + e.getMessage());
        }
        return "redirect:/disasters";
    }
    
    // Volunteer Management
    @GetMapping("/volunteer/create")
    public String showCreateVolunteerForm(Model model) {
        model.addAttribute("volunteer", new Volunteer());
        return "admin/create-volunteer";
    }
    
    @PostMapping("/volunteer/create")
    public String createVolunteer(@ModelAttribute Volunteer volunteer, RedirectAttributes redirectAttributes) {
        try {
            volunteerService.createVolunteer(volunteer);
            redirectAttributes.addFlashAttribute("success", "Volunteer created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating volunteer: " + e.getMessage());
        }
        return "redirect:/admin/volunteer/create";
    }
    
    @PostMapping("/volunteer/{id}/delete")
    public String deleteVolunteer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            volunteerService.deleteVolunteer(id);
            redirectAttributes.addFlashAttribute("success", "Volunteer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting volunteer: " + e.getMessage());
        }
        return "redirect:/volunteers";
    }
    
    // Simulation Control
    @PostMapping("/simulation/start")
    public String startSimulation(RedirectAttributes redirectAttributes) {
        try {
            // Start processing pending disasters
            disasterService.processPendingDisasters();
            redirectAttributes.addFlashAttribute("success", "Simulation started! Processing pending disasters...");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error starting simulation: " + e.getMessage());
        }
        return "redirect:/";
    }
    
    @PostMapping("/simulation/stop")
    public String stopSimulation(RedirectAttributes redirectAttributes) {
        try {
            // Implementation to stop simulation
            redirectAttributes.addFlashAttribute("success", "Simulation stopped!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error stopping simulation: " + e.getMessage());
        }
        return "redirect:/";
    }
}