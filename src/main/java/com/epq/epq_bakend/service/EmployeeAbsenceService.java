package com.epq.epq_bakend.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epq.epq_bakend.entity.EmployeeAbsence;
import com.epq.epq_bakend.repository.EmployeeAbsenceRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeAbsenceService {
    private final EmployeeAbsenceRepository absenceRepository;

    public EmployeeAbsenceService(EmployeeAbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

    public List<EmployeeAbsence> findAll() { return absenceRepository.findAll(); }

    public EmployeeAbsence findById(Long id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la ausencia con id " + id));
    }

    public List<EmployeeAbsence> findByEmployee(Long employeeId) {
        return absenceRepository.findByEmployeeIdOrderByStartDateDesc(employeeId);
    }

    public List<EmployeeAbsence> findByStatus(String status) {
        return absenceRepository.findByStatusOrderByStartDateDesc(status);
    }

    public List<EmployeeAbsence> findActiveOn(LocalDate date) {
        return absenceRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByStartDateAsc(date, date);
    }

    @Transactional
    public EmployeeAbsence save(EmployeeAbsence absence) {
        if (absence == null) throw new IllegalArgumentException("La ausencia es obligatoria.");
        if (absence.getStartDate() == null || absence.getEndDate() == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias.");
        }
        if (absence.getEndDate().isBefore(absence.getStartDate())) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la inicial.");
        }
        return absenceRepository.save(absence);
    }

    @Transactional
    public void delete(Long id) { absenceRepository.delete(findById(id)); }
}

