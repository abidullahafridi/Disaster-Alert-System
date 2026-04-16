package com.disaster.disaster_alert_system.repository;

import com.disaster.disaster_alert_system.model.entity.ThresholdRule;
import com.disaster.disaster_alert_system.model.enums.DisasterType;
import com.disaster.disaster_alert_system.model.enums.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;  

@Repository
public interface ThresholdRuleRepository extends JpaRepository<ThresholdRule, Long> {
    
    Optional<ThresholdRule> findByDisasterTypeAndSeverity(DisasterType disasterType, Severity severity);
    
    List<ThresholdRule> findByDisasterType(DisasterType disasterType);
}