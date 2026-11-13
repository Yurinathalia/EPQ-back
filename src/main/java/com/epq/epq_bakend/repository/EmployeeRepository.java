package com.epq.epq_bakend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epq.epq_bakend.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByDocumentNumber(String documentNumber);

    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

    List<Employee> findByLastNameContainingIgnoreCase(String lastName);

    @Query("""
            SELECT e FROM Employee e
            WHERE LOWER(CONCAT(
                CONCAT(COALESCE(e.firstName, ''), CONCAT(' ', COALESCE(e.middleName, ''))),
                CONCAT(' ', CONCAT(COALESCE(e.lastName, ''), CONCAT(' ', COALESCE(e.secondLastName, ''))))
            )) LIKE LOWER(CONCAT(CONCAT('%', :fullName), '%'))
            """)
    List<Employee> findByFullNameContainingIgnoreCase(@Param("fullName") String fullName);
 
}
