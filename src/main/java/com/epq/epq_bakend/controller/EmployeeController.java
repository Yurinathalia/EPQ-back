package com.epq.epq_bakend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epq.epq_bakend.entity.Employee;
import com.epq.epq_bakend.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> listar() {
        return service.listar();
    }

    @PostMapping
    public Employee guardar(@RequestBody Employee employee) {
        return service.guardar(employee);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/documento/{documento}")
    public Employee buscarPorDocumento(
            @PathVariable String documento) {

        return service.buscarPorDocumento(documento);
    }

    @GetMapping("/nombre/{nombre}")
    public List<Employee> buscarPorNombre(
            @PathVariable String nombre) {

        return service.buscarPorNombre(nombre);
    }

    @GetMapping("/apellido/{apellido}")
    public List<Employee> buscarPorApellido(
            @PathVariable String apellido) {

        return service.buscarPorApellido(apellido);
    }

    @GetMapping("/nombre-completo/{nombre}")
    public List<Employee> buscarPorNombreCompleto(
            @PathVariable String nombre) {

        return service.buscarPorNombreCompleto(nombre);
    }
}