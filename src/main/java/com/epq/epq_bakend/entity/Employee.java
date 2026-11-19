package com.epq.epq_bakend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "second_last_name")
    private String secondLastName;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "phone")
    private String phone;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "institutional_email")
    private String institutionalEmail;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "employment_status")
    private String employmentStatus;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * Datos sensibles (contrato, salario, cuenta bancaria). Viven en
     * employee_private_data y se manejan a traves de los accesos de abajo,
     * para que el JSON del API siga siendo plano.
     */
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private EmployeePrivateData privateData;

    // GETTERS Y SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getInstitutionalEmail() {
        return institutionalEmail;
    }

    public void setInstitutionalEmail(String institutionalEmail) {
        this.institutionalEmail = institutionalEmail;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getAccountNumber() {
        return privateData == null ? null : privateData.getBankAccountNumber();
    }

    public void setAccountNumber(String accountNumber) {
        if (privateData == null && accountNumber == null) {
            return;
        }
        datosPrivados().setBankAccountNumber(accountNumber);
    }

    public String getContractType() {
        return privateData == null ? null : privateData.getContractType();
    }

    public void setContractType(String contractType) {
        if (privateData == null && contractType == null) {
            return;
        }
        datosPrivados().setContractType(contractType);
    }

    public BigDecimal getSalary() {
        return privateData == null ? null : privateData.getSalary();
    }

    public void setSalary(BigDecimal salary) {
        if (privateData == null && salary == null) {
            return;
        }
        datosPrivados().setSalary(salary);
    }

    /**
     * Devuelve la fila de datos privados creandola solo cuando hay algo que
     * guardar. Nunca se llama desde un getter, para no insertar filas vacias
     * al serializar.
     */
    private EmployeePrivateData datosPrivados() {
        if (privateData == null) {
            privateData = new EmployeePrivateData();
            privateData.setEmployee(this);
        }
        return privateData;
    }

}