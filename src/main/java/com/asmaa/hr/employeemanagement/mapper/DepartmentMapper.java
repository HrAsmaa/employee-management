package com.asmaa.hr.employeemanagement.mapper;

import com.asmaa.hr.employeemanagement.dto.DepartmentRequest;
import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.entity.Department;
import com.asmaa.hr.employeemanagement.entity.Employee;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper {

    public Department toDepartment(@Valid DepartmentRequest departmentRequest) {
        return Department.builder()
                .name(departmentRequest.name())
                .manager(departmentRequest.managerId()!=null ? Employee.builder().id(departmentRequest.managerId()).build() : null)
                .build();
    }

    public DepartmentResponse toDepartmentResponse(Department dep) {
        return new DepartmentResponse(
                dep.getId(),
                dep.getName(),
                dep.getManager()!= null ? dep.getManager().getId() : null
                );
    }
}
