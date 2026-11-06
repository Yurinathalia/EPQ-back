package com.epq.epq_bakend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.epq.epq_bakend.entity.EmployeeAbsence;
import com.epq.epq_bakend.service.EmployeeAbsenceService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employee-absences")
public class EmployeeAbsenceController {
    private final EmployeeAbsenceService absenceService;

    public EmployeeAbsenceController(EmployeeAbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @GetMapping
    public List<EmployeeAbsence> findAll() { return absenceService.findAll(); }

    @GetMapping("/{id}")
    public EmployeeAbsence findById(@PathVariable Long id) { return absenceService.findById(id); }

    @GetMapping("/employee/{employeeId}")
    public List<EmployeeAbsence> findByEmployee(@PathVariable Long employeeId) {
        return absenceService.findByEmployee(employeeId);
    }

    @GetMapping("/status/{status}")
    public List<EmployeeAbsence> findByStatus(@PathVariable String status) {
        return absenceService.findByStatus(status);
    }

    @GetMapping("/active-on")
    public List<EmployeeAbsence> findActiveOn(@RequestParam LocalDate date) {
        return absenceService.findActiveOn(date);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeAbsence create(@RequestBody EmployeeAbsence absence) {
        return absenceService.save(absence);
    }

    @PutMapping("/{id}")
    public EmployeeAbsence update(@PathVariable Long id, @RequestBody EmployeeAbsence absence) {
        absence.setId(id);
        return absenceService.save(absence);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { absenceService.delete(id); }
}
