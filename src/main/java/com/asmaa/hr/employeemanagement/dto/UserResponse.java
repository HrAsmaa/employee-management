package com.asmaa.hr.employeemanagement.dto;

import com.asmaa.hr.employeemanagement.entity.UserRole;


public record UserResponse (
    int id,
    String username,
    String password,
    UserRole role,
    EmployeeResponse employee) {

}
