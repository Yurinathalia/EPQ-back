package com.epq.epq_bakend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epq.epq_bakend.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByDocumentNumber(String documentNumber);

    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

    List<Employee> findByLastNameContainingIgnoreCase(String lastName);

    List<Employee> findByFullNameContainingIgnoreCase(String fullName);
 
}