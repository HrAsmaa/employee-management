package com.asmaa.hr.employeemanagement.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmployeeRequest(
        @NotNull(message = "employee firstName is required")
        @JsonAlias("first_name")
        String firstName,

        @NotNull(message = "employee lastName is required")
        @JsonAlias("last_name")
        String lastName,

        @NotNull(message = "employee jobTitle is required")
        @JsonAlias("job_title")
        String jobTitle,

        @NotNull(message = "employee departmentId is required")
        @JsonAlias("department_id")
        Integer departmentId,

        @NotNull(message = "employee hireDate is required")
        @JsonAlias("hire_date")
        LocalDate hireDate,

        String status,

        @NotNull(message = "employee email is required")
        @Email(message = "employee email is not valid")
        String email,

        String street,
        String city,

        @JsonAlias("zip_code")
        String zipCode,
        String state

) {
}
