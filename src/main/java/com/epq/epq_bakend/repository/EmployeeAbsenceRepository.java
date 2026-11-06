package com.epq.epq_bakend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epq.epq_bakend.entity.EmployeeAbsence;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeAbsenceRepository extends JpaRepository<EmployeeAbsence, Long> {
    List<EmployeeAbsence> findByEmployeeIdOrderByStartDateDesc(Long employeeId);

    List<EmployeeAbsence> findByStatusOrderByStartDateDesc(String status);

    List<EmployeeAbsence> findByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateAsc(
            LocalDate date1, LocalDate date2);
}
