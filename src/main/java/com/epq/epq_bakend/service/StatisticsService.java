package com.epq.epq_bakend.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epq.epq_bakend.repository.ActivityRepository;
import com.epq.epq_bakend.repository.DependencyRepository;
import com.epq.epq_bakend.repository.EmployeeAbsenceRepository;

import java.util.Map;

@Service
@Transactional(readOnly = true)
public class StatisticsService {
    private final JdbcTemplate jdbcTemplate;
    private final DependencyRepository dependencyRepository;
    private final EmployeeAbsenceRepository absenceRepository;
    private final ActivityRepository activityRepository;

    public StatisticsService(JdbcTemplate jdbcTemplate,
                             DependencyRepository dependencyRepository,
                             EmployeeAbsenceRepository absenceRepository,
                             ActivityRepository activityRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.dependencyRepository = dependencyRepository;
        this.absenceRepository = absenceRepository;
        this.activityRepository = activityRepository;
    }

    public Overview getOverview() {
        Long employees = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM employee", Long.class);
        Long activeEmployees = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM employee WHERE employment_status = 'ACTIVE'", Long.class);
        return new Overview(
                employees == null ? 0 : employees,
                activeEmployees == null ? 0 : activeEmployees,
                dependencyRepository.count(),
                absenceRepository.count(),
                activityRepository.count());
    }

    public Map<String, Object> getEmployeeCountsByDependency() {
        return jdbcTemplate.query("""
                SELECT d.id AS dependency_id, d.name AS dependency_name,
                       COUNT(e.id) AS employee_count
                FROM dependency d
                LEFT JOIN employee e ON e.dependency_id = d.id
                GROUP BY d.id, d.name
                ORDER BY d.name
                """, rs -> {
            java.util.LinkedHashMap<String, Object> result = new java.util.LinkedHashMap<>();
            while (rs.next()) result.put(rs.getString("dependency_name"), rs.getLong("employee_count"));
            return result;
        });
    }

    public record Overview(long employees, long activeEmployees, long dependencies,
                           long absences, long activities) { }
}

