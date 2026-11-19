package com.epq.epq_bakend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Datos sensibles del empleado: contrato, salario y cuenta bancaria.
 *
 * Viven en su propia tabla (employee_private_data) para que no se expongan
 * en las consultas normales de employee. La relación es 1 a 1 con Employee.
 */
@Entity
@Table(name = "employee_private_data")
public class EmployeePrivateData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, unique = true)
    private Employee employee;

    @Column(name = "contract_type", length = 120)
    private String contractType;

    @Column(name = "salary", precision = 14, scale = 2)
    private BigDecimal salary;

    @Column(name = "bank_name", length = 120)
    private String bankName;

    @Column(name = "bank_account_type", length = 50)
    private String bankAccountType;

    // El codigo lo llama accountNumber
    @Column(name = "bank_account_number", length = 60)
    private String bankAccountNumber;

    @Column(name = "other_confidential_notes", columnDefinition = "TEXT")
    private String otherConfidentialNotes;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getOtherConfidentialNotes() {
        return otherConfidentialNotes;
    }

    public void setOtherConfidentialNotes(String otherConfidentialNotes) {
        this.otherConfidentialNotes = otherConfidentialNotes;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}
