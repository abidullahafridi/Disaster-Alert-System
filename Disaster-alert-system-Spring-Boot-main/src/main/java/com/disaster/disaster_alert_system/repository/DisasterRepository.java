package com.disaster.disaster_alert_system.repository;

import com.disaster.disaster_alert_system.model.entity.Disaster;
import com.disaster.disaster_alert_system.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisasterRepository extends JpaRepository<Disaster, Long> {
    
    List<Disaster> findByStatus(Status status);
    
    @Query("SELECT d FROM Disaster d WHERE d.status = 'ACTIVE' ORDER BY d.createdAt DESC")
    List<Disaster> findActiveDisasters();
    
    List<Disaster> findByTypeAndStatus(String type, Status status);
    
    long countByStatus(Status status);
}