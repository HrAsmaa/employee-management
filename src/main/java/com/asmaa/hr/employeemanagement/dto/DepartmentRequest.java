package com.asmaa.hr.employeemanagement.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record DepartmentRequest(
        @NotNull(message = "department name is required")
        String name,
        @JsonAlias("manager_id")
        Integer managerId
) {
}
