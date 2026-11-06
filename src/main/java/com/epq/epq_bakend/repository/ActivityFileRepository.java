package com.epq.epq_bakend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epq.epq_bakend.entity.ActivityFile;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityFileRepository extends JpaRepository<ActivityFile, Long> {
    List<ActivityFile> findByActivityIdOrderByCreatedAtDesc(Long activityId);

    List<ActivityFile> findByFileCategoryOrderByCreatedAtDesc(String fileCategory);

    Optional<ActivityFile> findByDriveFileId(String driveFileId);
}
