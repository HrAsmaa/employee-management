package com.asmaa.hr.employeemanagement.mapper;

import com.asmaa.hr.employeemanagement.dto.DepartmentRequest;
import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.entity.Department;
import com.asmaa.hr.employeemanagement.entity.Employee;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DepartmentMapperTests {

    @Autowired
    private DepartmentMapper departmentMapper;



    @Test
    public void toDepartment_test(){
        DepartmentRequest departmentRequest = new DepartmentRequest( "IT", 1);
        Employee employee = Employee.builder().id(1).build();
        Department department = new Department(null, "IT", null, employee);
        Department departmentResult = this.departmentMapper.toDepartment(departmentRequest);

        assertEquals(null, departmentResult.getId());
        assertEquals(departmentRequest.name(), departmentResult.getName());
        assertEquals(departmentRequest.managerId(), departmentResult.getManager().getId());
        assertEquals(null, departmentResult.getEmployees());

    }

    @Test
    public void toDepartmentResponse_test(){
        Employee employee = Employee.builder().id(1).build();
        Department department =  new Department(null, "IT", null, employee);
        DepartmentResponse departmentResponse = this.departmentMapper.toDepartmentResponse(department);

        assertEquals(department.getId(), departmentResponse.id());
        assertEquals(department.getName(),departmentResponse.name());
        assertEquals(department.getManager().getId(),departmentResponse.managerId());

    }
}
