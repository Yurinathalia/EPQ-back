package com.epq.epq_bakend.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epq.epq_bakend.entity.BirthdayTemplate;
import com.epq.epq_bakend.repository.BirthdayTemplateRepository;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BirthdayService {
    private final BirthdayTemplateRepository templateRepository;
    private final JdbcTemplate jdbcTemplate;

    public BirthdayService(BirthdayTemplateRepository templateRepository, JdbcTemplate jdbcTemplate) {
        this.templateRepository = templateRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BirthdayTemplate> findActiveTemplates() {
        return templateRepository.findByIsActiveTrueOrderByNameAsc();
    }

    public BirthdayTemplate findTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la plantilla con id " + id));
    }

    @Transactional
    public BirthdayTemplate saveTemplate(BirthdayTemplate template) {
        if (template == null) throw new IllegalArgumentException("La plantilla es obligatoria.");
        if (template.getName() == null || template.getName().isBlank()
                || template.getMessage() == null || template.getMessage().isBlank()) {
            throw new IllegalArgumentException("Nombre y mensaje de la plantilla son obligatorios.");
        }
        return templateRepository.save(template);
    }

    public List<Map<String, Object>> findBirthdaysByMonth(int month) {
        if (month < 1 || month > 12) throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        return jdbcTemplate.queryForList("""
                SELECT e.id, e.document_number, e.first_name, e.last_name,
                       e.second_last_name, e.full_name, e.birth_date,
                       d.name AS dependency_name
                FROM employee e
                LEFT JOIN dependency d ON d.id = e.dependency_id
                WHERE e.birth_date IS NOT NULL
                  AND MONTH(e.birth_date) = ?
                  AND e.employment_status = 'ACTIVE'
                ORDER BY DAY(e.birth_date), e.last_name, e.first_name
                """, month);
    }
}

