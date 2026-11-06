package com.epq.epq_bakend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.epq.epq_bakend.entity.Dependency;
import com.epq.epq_bakend.service.DependencyService;

import java.util.List;

@RestController
@RequestMapping("/api/dependencies")
public class DependencyController {
    private final DependencyService dependencyService;

    public DependencyController(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    @GetMapping
    public List<Dependency> findAll() { return dependencyService.findAll(); }

    @GetMapping("/active")
    public List<Dependency> findActive() { return dependencyService.findActive(); }

    @GetMapping("/roots")
    public List<Dependency> findRoots() { return dependencyService.findRoots(); }

    @GetMapping("/{parentId}/children")
    public List<Dependency> findChildren(@PathVariable Long parentId) {
        return dependencyService.findChildren(parentId);
    }

    @GetMapping("/{id}")
    public Dependency findById(@PathVariable Long id) { return dependencyService.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dependency create(@RequestBody Dependency dependency) {
        return dependencyService.save(dependency);
    }

    @PutMapping("/{id}")
    public Dependency update(@PathVariable Long id, @RequestBody Dependency dependency) {
        dependency.setId(id);
        return dependencyService.save(dependency);
    }

    @PatchMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) { dependencyService.deactivate(id); }
}
