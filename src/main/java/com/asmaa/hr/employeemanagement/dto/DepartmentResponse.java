package com.asmaa.hr.employeemanagement.dto;


import com.fasterxml.jackson.annotation.JsonAlias;

public record DepartmentResponse(
        Integer id,
        String name,

        @JsonAlias("manager_id")
        Integer managerId
) {
}
