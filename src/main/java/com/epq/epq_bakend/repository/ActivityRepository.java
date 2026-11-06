package com.epq.epq_bakend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epq.epq_bakend.entity.Activity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByDependencyIdOrderByStartAtAsc(Long dependencyId);

    List<Activity> findByStatusOrderByStartAtAsc(String status);

    List<Activity> findByStartAtBetweenOrderByStartAtAsc(LocalDateTime from, LocalDateTime to);

    List<Activity> findByResponsibleEmployeeIdOrderByStartAtDesc(Long employeeId);
}
