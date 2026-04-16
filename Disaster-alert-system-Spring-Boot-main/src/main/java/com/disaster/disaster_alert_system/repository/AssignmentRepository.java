package com.disaster.disaster_alert_system.repository;

import com.disaster.disaster_alert_system.model.entity.Assignment;
import com.disaster.disaster_alert_system.model.entity.Disaster;
import com.disaster.disaster_alert_system.model.entity.Volunteer;
import com.disaster.disaster_alert_system.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    List<Assignment> findByDisaster(Disaster disaster);
    
    List<Assignment> findByVolunteer(Volunteer volunteer);
    
    List<Assignment> findByStatus(Status status);
    
    @Query("SELECT a FROM Assignment a WHERE a.disaster.id = ?1 AND a.volunteer.category = ?2")
    List<Assignment> findByDisasterIdAndVolunteerCategory(Long disasterId, String category);
    
    long countByDisasterAndStatus(Disaster disaster, Status status);
}