package com.asmaa.hr.employeemanagement.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;

public record EmployeeResponse (
    int id,

    @JsonAlias("first_name")
    String firstName,

    @JsonAlias("last_name")
    String lastName,

    @JsonAlias("job_title")
    String jobTitle,

    @JsonAlias("department")
    DepartmentResponse departmentResponse,

    String status,

    @JsonAlias("hire_date")
    LocalDate hireDate,

    String email,
    String street,
    String city,

    @JsonAlias("zip_code")
    String zipCode,
    String state) {
}
