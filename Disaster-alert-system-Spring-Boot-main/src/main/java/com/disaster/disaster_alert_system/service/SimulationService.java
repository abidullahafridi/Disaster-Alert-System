package com.disaster.disaster_alert_system.service;

import com.disaster.disaster_alert_system.model.entity.Disaster;
import com.disaster.disaster_alert_system.model.entity.Volunteer;
import com.disaster.disaster_alert_system.model.entity.ThresholdRule;
import com.disaster.disaster_alert_system.model.enums.*;
import com.disaster.disaster_alert_system.repository.ThresholdRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@Transactional
public class SimulationService {
    
    @Autowired
    private DisasterService disasterService;
    
    @Autowired
    private VolunteerService volunteerService;
    
    @Autowired
    private ThresholdRuleRepository thresholdRuleRepository;
    
    // Initialize sample data
    @PostConstruct
    public void initializeSampleData() {
        initializeThresholdRules();
        initializeSampleVolunteers();
        System.out.println("✅ Sample data initialized successfully");
    }
    
    private void initializeThresholdRules() {
        // Check if rules already exist
        if (thresholdRuleRepository.count() > 0) {
            return;
        }
        
        // Flood thresholds
        createThresholdRule(DisasterType.FLOOD, Severity.LOW, 1, 0, 0, 0);
        createThresholdRule(DisasterType.FLOOD, Severity.MEDIUM, 1, 1, 1, 0);
        createThresholdRule(DisasterType.FLOOD, Severity.HIGH, 2, 1, 2, 1);
        createThresholdRule(DisasterType.FLOOD, Severity.CRITICAL, 3, 2, 2, 2);
        
        // Earthquake thresholds
        createThresholdRule(DisasterType.EARTHQUAKE, Severity.LOW, 1, 0, 0, 1);
        createThresholdRule(DisasterType.EARTHQUAKE, Severity.MEDIUM, 2, 1, 1, 1);
        createThresholdRule(DisasterType.EARTHQUAKE, Severity.HIGH, 3, 2, 1, 2);
        createThresholdRule(DisasterType.EARTHQUAKE, Severity.CRITICAL, 4, 3, 2, 3);
        
        // Fire thresholds
        createThresholdRule(DisasterType.FIRE, Severity.LOW, 1, 0, 0, 0);
        createThresholdRule(DisasterType.FIRE, Severity.MEDIUM, 2, 1, 0, 1);
        createThresholdRule(DisasterType.FIRE, Severity.HIGH, 3, 2, 1, 2);
        createThresholdRule(DisasterType.FIRE, Severity.CRITICAL, 4, 3, 2, 3);
        
        // Hurricane thresholds
        createThresholdRule(DisasterType.HURRICANE, Severity.LOW, 2, 0, 1, 0);
        createThresholdRule(DisasterType.HURRICANE, Severity.MEDIUM, 3, 1, 2, 1);
        createThresholdRule(DisasterType.HURRICANE, Severity.HIGH, 4, 2, 3, 2);
        createThresholdRule(DisasterType.HURRICANE, Severity.CRITICAL, 5, 3, 4, 3);
        
        // Tsunami thresholds
        createThresholdRule(DisasterType.TSUNAMI, Severity.LOW, 2, 1, 1, 1);
        createThresholdRule(DisasterType.TSUNAMI, Severity.MEDIUM, 3, 2, 2, 2);
        createThresholdRule(DisasterType.TSUNAMI, Severity.HIGH, 4, 3, 3, 3);
        createThresholdRule(DisasterType.TSUNAMI, Severity.CRITICAL, 5, 4, 4, 4);
    }
    
    private void createThresholdRule(DisasterType type, Severity severity, 
                                    int rescue, int medical, int supply, int engineer) {
        ThresholdRule rule = new ThresholdRule();
        rule.setDisasterType(type);
        rule.setSeverity(severity);
        rule.setRescueRequired(rescue);
        rule.setMedicalRequired(medical);
        rule.setSupplyRequired(supply);
        rule.setEngineerRequired(engineer);
        thresholdRuleRepository.save(rule);
    }
    
    private void initializeSampleVolunteers() {
        // Check if volunteers already exist
        if (volunteerService.getAllVolunteers().size() > 0) {
            return;
        }
        
        // Rescue Volunteers
        createVolunteer("John Rescue", VolunteerCategory.RESCUE, 5, "john@rescue.com", "555-0101");
        createVolunteer("Sarah Lifesaver", VolunteerCategory.RESCUE, 4, "sarah@rescue.com", "555-0102");
        createVolunteer("Mike Evacuator", VolunteerCategory.RESCUE, 3, "mike@rescue.com", "555-0103");
        createVolunteer("Emma Savior", VolunteerCategory.RESCUE, 5, "emma@rescue.com", "555-0104");
        
        // Medical Volunteers
        createVolunteer("Dr. Smith", VolunteerCategory.MEDICAL, 5, "dr.smith@medical.com", "555-0201");
        createVolunteer("Nurse Johnson", VolunteerCategory.MEDICAL, 4, "nurse.j@medical.com", "555-0202");
        createVolunteer("Dr. Wilson", VolunteerCategory.MEDICAL, 3, "dr.wilson@medical.com", "555-0203");
        createVolunteer("Paramedic Brown", VolunteerCategory.MEDICAL, 4, "paramedic@medical.com", "555-0204");
        
        // Supply Volunteers
        createVolunteer("Alex Distributor", VolunteerCategory.SUPPLY, 3, "alex@supply.com", "555-0301");
        createVolunteer("Lisa Logistics", VolunteerCategory.SUPPLY, 4, "lisa@supply.com", "555-0302");
        createVolunteer("Tom Warehouse", VolunteerCategory.SUPPLY, 2, "tom@supply.com", "555-0303");
        
        // Engineer Volunteers
        createVolunteer("Engineer Carter", VolunteerCategory.ENGINEER, 5, "carter@engineer.com", "555-0401");
        createVolunteer("Tech Davis", VolunteerCategory.ENGINEER, 3, "davis@engineer.com", "555-0402");
        createVolunteer("Builder Evans", VolunteerCategory.ENGINEER, 4, "evans@engineer.com", "555-0403");
    }
    
    private void createVolunteer(String name, VolunteerCategory category, int experience, 
                                String email, String phone) {
        Volunteer volunteer = new Volunteer();
        volunteer.setName(name);
        volunteer.setCategory(category);
        volunteer.setExperienceLevel(experience);
        volunteer.setEmail(email);
        volunteer.setPhoneNumber(phone);
        volunteer.setStatus(Status.AVAILABLE);
        volunteerService.createVolunteer(volunteer);
    }
    
    // Create sample disaster for testing
    public Disaster createSampleDisaster() {
        Disaster disaster = new Disaster();
        disaster.setType(DisasterType.FLOOD);
        disaster.setLocation("Downtown Area");
        disaster.setSeverity(Severity.HIGH);
        disaster.setDescription("Major flooding due to heavy rainfall. Multiple areas affected.");
        disaster.setEstimatedCasualties(50);
        disaster.setStatus(Status.PENDING);
        
        return disasterService.createDisaster(disaster);
    }
    
    // Get simulation status
    public Map<String, Object> getSimulationStatus() {
        Map<String, Object> status = new HashMap<>();
        
        status.put("thresholdRulesCount", thresholdRuleRepository.count());
        status.put("sampleVolunteersCount", volunteerService.getAllVolunteers().size());
        status.put("activeThreadsCount", disasterService.getDashboardStats().get("activeThreads"));
        
        return status;
    }
    
    // Get all threshold rules
    public List<ThresholdRule> getAllThresholdRules() {
        return thresholdRuleRepository.findAll();
    }
}