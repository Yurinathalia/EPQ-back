package com.epq.epq_bakend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epq.epq_bakend.entity.Dependency;

import java.util.List;

@Repository
public interface DependencyRepository extends JpaRepository<Dependency, Long> {
    List<Dependency> findByIsActiveTrueOrderByNameAsc();

    List<Dependency> findByParentIdIsNullOrderByNameAsc();

    List<Dependency> findByParentIdOrderByNameAsc(Long parentId);
}
