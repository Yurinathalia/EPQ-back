package com.epq.epq_bakend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.epq.epq_bakend.entity.Activity;
import com.epq.epq_bakend.service.ActivityService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<Activity> findAll() { return activityService.findAll(); }

    @GetMapping("/{id}")
    public Activity findById(@PathVariable Long id) { return activityService.findById(id); }

    @GetMapping("/dependency/{dependencyId}")
    public List<Activity> findByDependency(@PathVariable Long dependencyId) {
        return activityService.findByDependency(dependencyId);
    }

    @GetMapping("/status/{status}")
    public List<Activity> findByStatus(@PathVariable String status) {
        return activityService.findByStatus(status);
    }

    @GetMapping("/date-range")
    public List<Activity> findBetween(@RequestParam LocalDateTime from,
                                     @RequestParam LocalDateTime to) {
        return activityService.findBetween(from, to);
    }

    @GetMapping("/responsible/{employeeId}")
    public List<Activity> findByResponsibleEmployee(@PathVariable Long employeeId) {
        return activityService.findByResponsibleEmployee(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activity create(@RequestBody Activity activity) { return activityService.save(activity); }

    @PutMapping("/{id}")
    public Activity update(@PathVariable Long id, @RequestBody Activity activity) {
        activity.setId(id);
        return activityService.save(activity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { activityService.delete(id); }
}
