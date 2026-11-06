package com.epq.epq_bakend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.epq.epq_bakend.entity.EmployeeDependencyHistory;
import com.epq.epq_bakend.service.EmployeeHistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/employee-history")
public class EmployeeHistoryController {
    private final EmployeeHistoryService historyService;

    public EmployeeHistoryController(EmployeeHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public List<EmployeeDependencyHistory> findAll() { return historyService.findAll(); }

    @GetMapping("/employee/{employeeId}")
    public List<EmployeeDependencyHistory> findByEmployee(@PathVariable Long employeeId) {
        return historyService.findByEmployee(employeeId);
    }

    @GetMapping("/dependency/{dependencyId}")
    public List<EmployeeDependencyHistory> findByNewDependency(@PathVariable Long dependencyId) {
        return historyService.findByNewDependency(dependencyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDependencyHistory record(@RequestBody EmployeeDependencyHistory history) {
        return historyService.record(history);
    }
}
