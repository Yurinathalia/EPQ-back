package com.epq.epq_bakend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epq.epq_bakend.entity.Activity;
import com.epq.epq_bakend.repository.ActivityRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> findAll() { return activityRepository.findAll(); }

    public Activity findById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la actividad con id " + id));
    }

    public List<Activity> findByDependency(Long dependencyId) {
        return activityRepository.findByDependencyIdOrderByStartAtAsc(dependencyId);
    }

    public List<Activity> findByStatus(String status) {
        return activityRepository.findByStatusOrderByStartAtAsc(status);
    }

    public List<Activity> findBetween(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null || to.isBefore(from)) {
            throw new IllegalArgumentException("El rango de fechas no es válido.");
        }
        return activityRepository.findByStartAtBetweenOrderByStartAtAsc(from, to);
    }

    public List<Activity> findByResponsibleEmployee(Long employeeId) {
        return activityRepository.findByResponsibleEmployeeIdOrderByStartAtDesc(employeeId);
    }

    @Transactional
    public Activity save(Activity activity) {
        if (activity == null) throw new IllegalArgumentException("La actividad es obligatoria.");
        if (activity.getTitle() == null || activity.getTitle().isBlank()) {
            throw new IllegalArgumentException("El título de la actividad es obligatorio.");
        }
        if (activity.getStartAt() != null && activity.getEndAt() != null
                && activity.getEndAt().isBefore(activity.getStartAt())) {
            throw new IllegalArgumentException("La fecha final no puede ser anterior a la inicial.");
        }
        return activityRepository.save(activity);
    }

    @Transactional
    public void delete(Long id) { activityRepository.delete(findById(id)); }
}
