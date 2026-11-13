package com.epq.epq_bakend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.epq.epq_bakend.entity.Employee;
import com.epq.epq_bakend.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> listar() {
        return repository.findAll();
    }

    public Employee guardar(Employee employee) {
        return repository.save(employee);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Employee buscarPorDocumento(String documento) {

        return repository.findByDocumentNumber(documento)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    public List<Employee> buscarPorNombre(String nombre) {
        return repository.findByFirstNameContainingIgnoreCase(nombre);
    }

    public List<Employee> buscarPorApellido(String apellido) {
        return repository.findByLastNameContainingIgnoreCase(apellido);
    }

    public List<Employee> buscarPorNombreCompleto(String nombre) {
        return repository.findByFullNameContainingIgnoreCase(nombre);
    }

    public Employee actualizar(
            Long id,
            Employee employeeActualizado) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Empleado no encontrado"));

        employee.setDocumentType(
                employeeActualizado.getDocumentType());

        employee.setDocumentNumber(
                employeeActualizado.getDocumentNumber());

        employee.setFirstName(
                employeeActualizado.getFirstName());

        employee.setMiddleName(
                employeeActualizado.getMiddleName());

        employee.setLastName(
                employeeActualizado.getLastName());

        employee.setSecondLastName(
                employeeActualizado.getSecondLastName());

        employee.setSex(
                employeeActualizado.getSex());

        employee.setBirthDate(
                employeeActualizado.getBirthDate());

        employee.setAddress(
                employeeActualizado.getAddress());

        employee.setCity(
                employeeActualizado.getCity());

        employee.setPhone(
                employeeActualizado.getPhone());

        employee.setPersonalEmail(
                employeeActualizado.getPersonalEmail());

        employee.setInstitutionalEmail(
                employeeActualizado.getInstitutionalEmail());

        employee.setJobTitle(
                employeeActualizado.getJobTitle());

        employee.setPhotoUrl(
                employeeActualizado.getPhotoUrl());

        employee.setEmploymentStatus(
                employeeActualizado.getEmploymentStatus());

        employee.setHireDate(
                employeeActualizado.getHireDate());

        employee.setEndDate(
                employeeActualizado.getEndDate());

        employee.setContractType(
                employeeActualizado.getContractType());

        employee.setSalary(
                employeeActualizado.getSalary());

        employee.setAccountNumber(
                employeeActualizado.getAccountNumber());

        return repository.save(employee);

    }

}