package com.epq.epq_bakend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_absence")
public class EmployeeAbsence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    // ABSENCE, PERMISSION, SICK_LEAVE, VACATION, LICENSE, COMMISSION, OTHER
    @Column(name = "absence_type", nullable = false)
    private String absenceType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "days_count", precision = 6, scale = 2)
    private BigDecimal daysCount;

    // REPORTED, APPROVED, REJECTED, CANCELLED
    @Column(name = "status", nullable = false)
    private String status = "REPORTED";

    @Column(name = "general_note", length = 500)
    private String generalNote;

    @Column(name = "recorded_by_user_id")
    private Long recordedByUserId;

    @Column(name = "registered_at", insertable = false, updatable = false)
    private LocalDateTime registeredAt;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @Column(name = "support_document", length = 500)
    private String supportDocument;

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(String absenceType) {
        this.absenceType = absenceType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(BigDecimal daysCount) {
        this.daysCount = daysCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGeneralNote() {
        return generalNote;
    }

    public void setGeneralNote(String generalNote) {
        this.generalNote = generalNote;
    }

    public Long getRecordedByUserId() {
        return recordedByUserId;
    }

    public void setRecordedByUserId(Long recordedByUserId) {
        this.recordedByUserId = recordedByUserId;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getSupportDocument() {
        return supportDocument;
    }

    public void setSupportDocument(String supportDocument) {
        this.supportDocument = supportDocument;
    }

    public void setId(Long id2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }

}