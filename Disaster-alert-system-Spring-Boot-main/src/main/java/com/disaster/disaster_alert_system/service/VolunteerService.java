package com.disaster.disaster_alert_system.service;

import com.disaster.disaster_alert_system.model.entity.Volunteer;
import com.disaster.disaster_alert_system.model.enums.Status;
import com.disaster.disaster_alert_system.model.enums.VolunteerCategory;
import com.disaster.disaster_alert_system.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class VolunteerService {
    
    @Autowired
    private VolunteerRepository volunteerRepository;
    
    public Volunteer createVolunteer(Volunteer volunteer) {
        if (volunteer.getStatus() == null) {
            volunteer.setStatus(Status.AVAILABLE);
        }
        if (volunteer.getExperienceLevel() == null) {
            volunteer.setExperienceLevel(1);
        }
        return volunteerRepository.save(volunteer);
    }
    
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }
    
    public List<Volunteer> getAvailableVolunteers() {
        return volunteerRepository.findAvailableVolunteers();
    }
    
    public List<Volunteer> getVolunteersByCategory(VolunteerCategory category) {
        return volunteerRepository.findByCategoryAndStatus(category, Status.AVAILABLE);
    }
    
    public Volunteer getVolunteerById(Long id) {
        return volunteerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Volunteer not found with id: " + id));
    }
    
    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }
    
    public long countByCategory(VolunteerCategory category) {
        return volunteerRepository.countByCategoryAndStatus(category, Status.AVAILABLE);
    }
}