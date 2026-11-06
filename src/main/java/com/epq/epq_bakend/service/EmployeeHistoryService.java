package com.epq.epq_bakend.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epq.epq_bakend.entity.EmployeeDependencyHistory;
import com.epq.epq_bakend.repository.EmployeeDependencyHistoryRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeHistoryService {
    private final EmployeeDependencyHistoryRepository historyRepository;

    public EmployeeHistoryService(EmployeeDependencyHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<EmployeeDependencyHistory> findAll() {
        return historyRepository.findAll();
    }

    public List<EmployeeDependencyHistory> findByEmployee(Long employeeId) {
        return historyRepository.findByEmployeeIdOrderByChangeDateDesc(employeeId);
    }

    public List<EmployeeDependencyHistory> findByNewDependency(Long dependencyId) {
        return historyRepository.findByNewDependencyIdOrderByChangeDateDesc(dependencyId);
    }

    @Transactional
    public EmployeeDependencyHistory record(EmployeeDependencyHistory history) {
        if (history == null) throw new IllegalArgumentException("El registro histórico es obligatorio.");
        if (history.getEmployeeId() == null || history.getNewDependencyId() == null) {
            throw new IllegalArgumentException("Empleado y nueva dependencia son obligatorios.");
        }
        return historyRepository.save(history);
    }
}
