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
    
}