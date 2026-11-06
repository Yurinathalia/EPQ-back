package com.epq.epq_bakend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "birthday_notification_log")
public class BirthdayNotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "message_text", nullable = false, columnDefinition = "TEXT")
    private String messageText;

    // WHATSAPP_MANUAL, WHATSAPP_AUTO
    @Column(name = "channel", nullable = false)
    private String channel = "WHATSAPP_MANUAL";

    // OPENED (se abrió WhatsApp con el mensaje), SENT, FAILED
    @Column(name = "status", nullable = false)
    private String status = "OPENED";

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @Column(name = "sent_by_user_id")
    private Long sentByUserId;

    @Column(name = "birthday_year", nullable = false)
    private Integer birthdayYear;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getSentByUserId() {
        return sentByUserId;
    }

    public void setSentByUserId(Long sentByUserId) {
        this.sentByUserId = sentByUserId;
    }

    public Integer getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(Integer birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}