package com.epq.epq_bakend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epq.epq_bakend.entity.BirthdayTemplate;

import java.util.List;

@Repository
public interface BirthdayTemplateRepository extends JpaRepository<BirthdayTemplate, Long> {
    List<BirthdayTemplate> findByIsActiveTrueOrderByNameAsc();
}
