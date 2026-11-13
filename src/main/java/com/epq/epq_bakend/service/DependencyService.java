package com.epq.epq_bakend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epq.epq_bakend.entity.Dependency;
import com.epq.epq_bakend.repository.DependencyRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DependencyService {
    private final DependencyRepository dependencyRepository;

    public DependencyService(DependencyRepository dependencyRepository) {
        this.dependencyRepository = dependencyRepository;
    }

    public List<Dependency> findAll() {
        return dependencyRepository.findAll();
    }

    public List<Dependency> findActive() {
        return dependencyRepository.findByActiveTrueOrderByNameAsc();
    }

    public List<Dependency> findRoots() {
        return dependencyRepository.findByParentIdIsNullOrderByNameAsc();
    }

    public List<Dependency> findChildren(Long parentId) {
        if (parentId == null) throw new IllegalArgumentException("El id del padre es obligatorio.");
        return dependencyRepository.findByParentIdOrderByNameAsc(parentId);
    }

    public Dependency findById(Long id) {
        return dependencyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la dependencia con id " + id));
    }

    @Transactional
    public Dependency save(Dependency dependency) {
        if (dependency == null) throw new IllegalArgumentException("La dependencia es obligatoria.");
        if (dependency.getName() == null || dependency.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre de la dependencia es obligatorio.");
        }
        return dependencyRepository.save(dependency);
    }

    @Transactional
    public void deactivate(Long id) {
        Dependency dependency = findById(id);
        dependency.setActive(false);
        dependencyRepository.save(dependency);
    }
}

