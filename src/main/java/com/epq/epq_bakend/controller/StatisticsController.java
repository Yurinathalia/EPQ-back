package com.epq.epq_bakend.controller;

import org.springframework.web.bind.annotation.*;

import com.epq.epq_bakend.service.StatisticsService;

import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/overview")
    public StatisticsService.Overview getOverview() { return statisticsService.getOverview(); }

    @GetMapping("/employees-by-dependency")
    public Map<String, Object> getEmployeeCountsByDependency() {
        return statisticsService.getEmployeeCountsByDependency();
    }
}

