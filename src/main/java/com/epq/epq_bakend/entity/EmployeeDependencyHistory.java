package com.epq.epq_bakend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_dependency_history")
public class EmployeeDependencyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "previous_dependency_id")
    private Long previousDependencyId;

    @Column(name = "new_dependency_id", nullable = false)
    private Long newDependencyId;

    @Column(name = "change_date", insertable = false, updatable = false)
    private LocalDateTime changeDate;

    @Column(name = "changed_by_user_id")
    private Long changedByUserId;

    @Column(name = "observation", length = 500)
    private String observation;

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getPreviousDependencyId() {
        return previousDependencyId;
    }

    public void setPreviousDependencyId(Long previousDependencyId) {
        this.previousDependencyId = previousDependencyId;
    }

    public Long getNewDependencyId() {
        return newDependencyId;
    }

    public void setNewDependencyId(Long newDependencyId) {
        this.newDependencyId = newDependencyId;
    }

    public LocalDateTime getChangeDate() {
        return changeDate;
    }

    public Long getChangedByUserId() {
        return changedByUserId;
    }

    public void setChangedByUserId(Long changedByUserId) {
        this.changedByUserId = changedByUserId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

}