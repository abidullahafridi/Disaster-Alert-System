package com.disaster.disaster_alert_system.repository;

import com.disaster.disaster_alert_system.model.entity.Volunteer;
import com.disaster.disaster_alert_system.model.enums.Status;
import com.disaster.disaster_alert_system.model.enums.VolunteerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    
    List<Volunteer> findByStatus(Status status);
    
    List<Volunteer> findByCategoryAndStatus(VolunteerCategory category, Status status);
    
    @Query("SELECT v FROM Volunteer v WHERE v.status = 'AVAILABLE' ORDER BY v.experienceLevel DESC")
    List<Volunteer> findAvailableVolunteers();
    
    long countByCategoryAndStatus(VolunteerCategory category, Status status);

    // Add to VolunteerRepository.java
@Query("SELECT v FROM Volunteer v WHERE v.status = 'RESTING'")
List<Volunteer> findRestingVolunteers();

long countByStatus(Status status);
}