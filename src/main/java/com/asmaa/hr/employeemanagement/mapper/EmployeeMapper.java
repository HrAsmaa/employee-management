package com.asmaa.hr.employeemanagement.mapper;

import com.asmaa.hr.employeemanagement.dto.EmployeeRequest;
import com.asmaa.hr.employeemanagement.dto.EmployeeResponse;
import com.asmaa.hr.employeemanagement.entity.Address;
import com.asmaa.hr.employeemanagement.entity.Department;
import com.asmaa.hr.employeemanagement.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeMapper {

    private final DepartmentMapper departmentMapper;

    public Employee toEmployee(EmployeeRequest employeeRequest) {
        Address address = Address.builder()
                .street(employeeRequest.street())
                .city(employeeRequest.city())
                .zipCode(employeeRequest.zipCode())
                .state(employeeRequest.state())
                .build();
        Department department = Department.builder().id(employeeRequest.departmentId()).build();
        return Employee.builder()
                .email(employeeRequest.email())
                .firstName(employeeRequest.firstName())
                .lastName(employeeRequest.lastName())
                .hireDate(employeeRequest.hireDate())
                .status(employeeRequest.status())
                .jobTitle(employeeRequest.jobTitle())
                .address(address)
                .department(department)
                .build();
    }

    public EmployeeResponse toEmployeeResponse(Employee e) {
        return new EmployeeResponse(
                e.getId(),
                e.getFirstName(),
                e.getLastName(),
                e.getJobTitle(),
                this.departmentMapper.toDepartmentResponse(e.getDepartment()),
                e.getStatus(),
                e.getHireDate(),
                e.getEmail(),
                e.getAddress().getStreet(),
                e.getAddress().getCity(),
                e.getAddress().getZipCode(),
                e.getAddress().getState());
    }
}
