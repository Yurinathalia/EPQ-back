package com.epq.epq_bakend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_file")
public class ActivityFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    @Column(name = "drive_file_id", length = 255)
    private String driveFileId;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "mime_type", length = 150)
    private String mimeType;

    // INVITATION, PHOTO, DOCUMENT, VIDEO, REPORT, OTHER
    @Column(name = "file_category", nullable = false)
    private String fileCategory = "OTHER";

    @Column(name = "drive_web_view_link", length = 1000)
    private String driveWebViewLink;

    @Column(name = "drive_created_at")
    private LocalDateTime driveCreatedAt;

    @Column(name = "uploaded_by_user_id")
    private Long uploadedByUserId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getDriveFileId() {
        return driveFileId;
    }

    public void setDriveFileId(String driveFileId) {
        this.driveFileId = driveFileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }

    public String getDriveWebViewLink() {
        return driveWebViewLink;
    }

    public void setDriveWebViewLink(String driveWebViewLink) {
        this.driveWebViewLink = driveWebViewLink;
    }

    public LocalDateTime getDriveCreatedAt() {
        return driveCreatedAt;
    }

    public void setDriveCreatedAt(LocalDateTime driveCreatedAt) {
        this.driveCreatedAt = driveCreatedAt;
    }

    public Long getUploadedByUserId() {
        return uploadedByUserId;
    }

    public void setUploadedByUserId(Long uploadedByUserId) {
        this.uploadedByUserId = uploadedByUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}