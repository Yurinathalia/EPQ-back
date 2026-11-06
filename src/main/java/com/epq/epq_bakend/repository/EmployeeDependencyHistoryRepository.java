package com.epq.epq_bakend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epq.epq_bakend.entity.EmployeeDependencyHistory;

import java.util.List;

@Repository
public interface EmployeeDependencyHistoryRepository extends JpaRepository<EmployeeDependencyHistory, Long> {
    List<EmployeeDependencyHistory> findByEmployeeIdOrderByChangeDateDesc(Long employeeId);

    List<EmployeeDependencyHistory> findByNewDependencyIdOrderByChangeDateDesc(Long dependencyId);
}
