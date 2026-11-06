package com.epq.epq_bakend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epq.epq_bakend.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
    
}
