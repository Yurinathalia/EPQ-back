package com.epq.epq_bakend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.epq.epq_bakend.entity.BirthdayTemplate;
import com.epq.epq_bakend.service.BirthdayService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/birthdays")
public class BirthdayController {
    private final BirthdayService birthdayService;

    public BirthdayController(BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    @GetMapping("/month/{month}")
    public List<Map<String, Object>> findBirthdaysByMonth(@PathVariable int month) {
        return birthdayService.findBirthdaysByMonth(month);
    }

    @GetMapping("/templates")
    public List<BirthdayTemplate> findActiveTemplates() {
        return birthdayService.findActiveTemplates();
    }

    @GetMapping("/templates/{id}")
    public BirthdayTemplate findTemplateById(@PathVariable Long id) {
        return birthdayService.findTemplateById(id);
    }

    @PostMapping("/templates")
    @ResponseStatus(HttpStatus.CREATED)
    public BirthdayTemplate createTemplate(@RequestBody BirthdayTemplate template) {
        return birthdayService.saveTemplate(template);
    }

    @PutMapping("/templates/{id}")
    public BirthdayTemplate updateTemplate(@PathVariable Long id, @RequestBody BirthdayTemplate template) {
        template.setId(id);
        return birthdayService.saveTemplate(template);
    }
}

