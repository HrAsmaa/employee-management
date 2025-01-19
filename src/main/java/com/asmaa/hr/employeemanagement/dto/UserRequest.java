package com.asmaa.hr.employeemanagement.dto;

import com.asmaa.hr.employeemanagement.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @JsonAlias("employee_id")
        @NotNull(message = "the employee id is required")
        Integer employeeId,
        @NotNull(message = "the username is required")
        String username,
        @NotNull(message = "the password is required")
        String password,
        @NotNull(message = "the role is required")
        UserRole role
) {
}
