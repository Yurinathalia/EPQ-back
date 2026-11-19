-- ============================================================================
-- EPQ - Esquema canonico de la base de datos `epq` (MySQL 8.0)
-- ============================================================================
--
-- ESTE ARCHIVO ES LA FUENTE DE VERDAD DEL ESQUEMA.
--
-- Generado desde la base `epq` real el 2026-11-16, despues de restaurar las
-- columnas que `spring.jpa.hibernate.ddl-auto=update` habia degradado:
--
--   * todos los ENUM de MySQL se habian convertido a varchar(255)
--   * se habian perdido NOT NULL, DEFAULT y los tamaños declarados
--     (p. ej. employee.photo_url 1000 -> 255, employee.document_number 30 -> 255)
--
-- Reglas:
--   1. Los cambios de esquema se hacen con ALTER TABLE revisados, no dejando
--      que Hibernate genere DDL. En application.properties debe seguir
--      `spring.jpa.hibernate.ddl-auto=validate`.
--   2. Si se agrega una entidad nueva, su DDL se agrega aqui en el mismo commit.
--   3. La tabla `employee_sex_backup_20261116` es un respaldo puntual de la
--      correccion de generos; no forma parte del esquema y puede eliminarse.
--
-- Uso:
--   mysql -u root -p < db/schema.sql
--
-- ============================================================================

CREATE DATABASE IF NOT EXISTS `epq`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE `epq`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- activity
-- ----------------------------------------------------------------------------
CREATE TABLE `activity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(220) NOT NULL,
  `description` text,
  `activity_type` enum('WORKSHOP','MEETING','INVITATION','CAMPAIGN','TRAINING','EVENT','OTHER') NOT NULL DEFAULT 'OTHER',
  `status` enum('DRAFT','PLANNED','IN_PROGRESS','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PLANNED',
  `start_at` datetime DEFAULT NULL,
  `end_at` datetime DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `dependency_id` bigint unsigned DEFAULT NULL,
  `responsible_employee_id` bigint unsigned DEFAULT NULL,
  `created_by_user_id` bigint unsigned DEFAULT NULL,
  `updated_by_user_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_activity_responsible` (`responsible_employee_id`),
  KEY `fk_activity_created_by` (`created_by_user_id`),
  KEY `fk_activity_updated_by` (`updated_by_user_id`),
  KEY `idx_activity_start` (`start_at`),
  KEY `idx_activity_type_status` (`activity_type`,`status`),
  KEY `idx_activity_dependency` (`dependency_id`),
  CONSTRAINT `fk_activity_created_by` FOREIGN KEY (`created_by_user_id`) REFERENCES `app_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_activity_dependency` FOREIGN KEY (`dependency_id`) REFERENCES `dependency` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_activity_responsible` FOREIGN KEY (`responsible_employee_id`) REFERENCES `employee` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_activity_updated_by` FOREIGN KEY (`updated_by_user_id`) REFERENCES `app_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `chk_activity_dates` CHECK (((`end_at` is null) or (`start_at` is null) or (`end_at` >= `start_at`)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- activity_file
-- ----------------------------------------------------------------------------
CREATE TABLE `activity_file` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `activity_id` bigint unsigned NOT NULL,
  `drive_file_id` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) NOT NULL,
  `mime_type` varchar(150) DEFAULT NULL,
  `file_category` enum('INVITATION','PHOTO','DOCUMENT','VIDEO','REPORT','OTHER') NOT NULL DEFAULT 'OTHER',
  `drive_web_view_link` varchar(1000) DEFAULT NULL,
  `drive_created_at` datetime DEFAULT NULL,
  `uploaded_by_user_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_activity_file_activity` (`activity_id`),
  KEY `fk_activity_file_user` (`uploaded_by_user_id`),
  KEY `idx_activity_file_drive_id` (`drive_file_id`),
  KEY `idx_activity_file_category` (`file_category`),
  CONSTRAINT `fk_activity_file_activity` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_activity_file_user` FOREIGN KEY (`uploaded_by_user_id`) REFERENCES `app_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- app_user
-- ----------------------------------------------------------------------------
CREATE TABLE `app_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `employee_id` bigint unsigned DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(180) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('ADMIN','BOSS','MANAGER','HR','EDITOR','EMPLOYEE','AUDITOR','VIEWER') NOT NULL DEFAULT 'EMPLOYEE',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `must_change_password` tinyint(1) NOT NULL DEFAULT '1',
  `failed_login_attempts` int unsigned NOT NULL DEFAULT '0',
  `locked_until` datetime DEFAULT NULL,
  `last_login_at` datetime DEFAULT NULL,
  `password_changed_at` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `employee_id` (`employee_id`),
  KEY `idx_app_user_role_active` (`role`,`is_active`),
  CONSTRAINT `fk_app_user_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- audit_log
-- ----------------------------------------------------------------------------
CREATE TABLE `audit_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned DEFAULT NULL,
  `action` varchar(80) NOT NULL,
  `entity_type` varchar(80) NOT NULL,
  `entity_id` varchar(100) DEFAULT NULL,
  `details` json DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_audit_entity` (`entity_type`,`entity_id`),
  KEY `idx_audit_created_at` (`created_at`),
  KEY `idx_audit_user_created` (`user_id`,`created_at`),
  CONSTRAINT `fk_audit_user` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- birthday_template
-- ----------------------------------------------------------------------------
CREATE TABLE `birthday_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(1000) DEFAULT NULL,
  `message` text NOT NULL,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- birthday_notification_log
-- ----------------------------------------------------------------------------
CREATE TABLE `birthday_notification_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `birthday_year` int NOT NULL,
  `channel` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `employee_id` bigint NOT NULL,
  `error_message` varchar(500) DEFAULT NULL,
  `message_text` text NOT NULL,
  `sent_by_user_id` bigint DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `template_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- dependency
-- ----------------------------------------------------------------------------
CREATE TABLE `dependency` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `parent_id` bigint unsigned DEFAULT NULL,
  `responsible_employee_id` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `fk_dependency_parent` (`parent_id`),
  CONSTRAINT `fk_dependency_parent` FOREIGN KEY (`parent_id`) REFERENCES `dependency` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- dependency_parent
-- ----------------------------------------------------------------------------
CREATE TABLE `dependency_parent` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- employee
-- ----------------------------------------------------------------------------
CREATE TABLE `employee` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `document_type` varchar(30) NOT NULL DEFAULT 'CC',
  `document_number` varchar(30) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `middle_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) NOT NULL,
  `second_last_name` varchar(100) DEFAULT NULL,
  `sex` varchar(40) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `phone` varchar(40) DEFAULT NULL,
  `personal_email` varchar(180) DEFAULT NULL,
  `institutional_email` varchar(180) DEFAULT NULL,
  `job_title` varchar(150) DEFAULT NULL,
  `dependency_id` bigint unsigned DEFAULT NULL,
  `photo_url` varchar(1000) DEFAULT NULL,
  `employment_status` enum('ACTIVE','INACTIVE','LEAVE','RETIRED','OTHER') NOT NULL DEFAULT 'ACTIVE',
  `hire_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `full_name` varchar(200) DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `document_number` (`document_number`),
  KEY `idx_employee_name` (`last_name`,`second_last_name`,`first_name`),
  KEY `idx_employee_dependency` (`dependency_id`),
  KEY `idx_employee_birth_date` (`birth_date`),
  KEY `idx_employee_status` (`employment_status`),
  CONSTRAINT `fk_employee_dependency` FOREIGN KEY (`dependency_id`) REFERENCES `dependency` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `chk_employee_dates` CHECK (((`end_date` is null) or (`hire_date` is null) or (`end_date` >= `hire_date`)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- employee_private_data  (datos sensibles, 1 a 1 con employee)
-- ----------------------------------------------------------------------------
CREATE TABLE `employee_private_data` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `employee_id` bigint unsigned NOT NULL,
  `contract_type` varchar(120) DEFAULT NULL,
  `salary` decimal(14,2) DEFAULT NULL,
  `bank_name` varchar(120) DEFAULT NULL,
  `bank_account_type` varchar(50) DEFAULT NULL,
  `bank_account_number` varchar(60) DEFAULT NULL,
  `other_confidential_notes` text,
  `valid_from` date DEFAULT NULL,
  `valid_to` date DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `employee_id` (`employee_id`),
  CONSTRAINT `fk_private_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `chk_private_dates` CHECK (((`valid_to` is null) or (`valid_from` is null) or (`valid_to` >= `valid_from`)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- employee_absence
-- ----------------------------------------------------------------------------
CREATE TABLE `employee_absence` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `employee_id` bigint unsigned NOT NULL,
  `absence_type` enum('ABSENCE','PERMISSION','SICK_LEAVE','VACATION','LICENSE','COMMISSION','OTHER') NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `days_count` decimal(6,2) DEFAULT NULL,
  `status` enum('REPORTED','APPROVED','REJECTED','CANCELLED') NOT NULL DEFAULT 'REPORTED',
  `reference_code` varchar(100) DEFAULT NULL,
  `general_note` varchar(500) DEFAULT NULL,
  `recorded_by_user_id` bigint unsigned DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `approval_date` datetime DEFAULT NULL,
  `support_document` varchar(500) DEFAULT NULL,
  `registered_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_absence_user` (`recorded_by_user_id`),
  KEY `idx_absence_employee_dates` (`employee_id`,`start_date`,`end_date`),
  KEY `idx_absence_status` (`status`),
  CONSTRAINT `fk_absence_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_absence_user` FOREIGN KEY (`recorded_by_user_id`) REFERENCES `app_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `chk_absence_dates` CHECK ((`end_date` >= `start_date`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- employee_dependency_history
-- ----------------------------------------------------------------------------
CREATE TABLE `employee_dependency_history` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `employee_id` bigint unsigned NOT NULL,
  `previous_dependency_id` bigint unsigned DEFAULT NULL,
  `new_dependency_id` bigint unsigned NOT NULL,
  `change_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `changed_by_user_id` bigint unsigned DEFAULT NULL,
  `observation` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_hist_employee` (`employee_id`),
  KEY `fk_hist_previous_dependency` (`previous_dependency_id`),
  KEY `fk_hist_new_dependency` (`new_dependency_id`),
  KEY `fk_hist_user` (`changed_by_user_id`),
  CONSTRAINT `fk_hist_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_hist_new_dependency` FOREIGN KEY (`new_dependency_id`) REFERENCES `dependency` (`id`),
  CONSTRAINT `fk_hist_previous_dependency` FOREIGN KEY (`previous_dependency_id`) REFERENCES `dependency` (`id`),
  CONSTRAINT `fk_hist_user` FOREIGN KEY (`changed_by_user_id`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- password_reset_token
-- ----------------------------------------------------------------------------
CREATE TABLE `password_reset_token` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `token_hash` varchar(255) NOT NULL,
  `expires_at` datetime NOT NULL,
  `used_at` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `token_hash` (`token_hash`),
  KEY `idx_reset_user_expiry` (`user_id`,`expires_at`),
  CONSTRAINT `fk_reset_user` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------------------------------------------------------
-- user_session
-- ----------------------------------------------------------------------------
CREATE TABLE `user_session` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `refresh_token_hash` varchar(255) NOT NULL,
  `expires_at` datetime NOT NULL,
  `revoked_at` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_used_at` datetime DEFAULT NULL,
  `user_agent` varchar(500) DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `refresh_token_hash` (`refresh_token_hash`),
  KEY `idx_session_user_expiry` (`user_id`,`expires_at`),
  CONSTRAINT `fk_session_user` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
