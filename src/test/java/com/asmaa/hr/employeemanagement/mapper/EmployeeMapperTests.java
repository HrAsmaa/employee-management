package com.asmaa.hr.employeemanagement.mapper;

import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.dto.EmployeeRequest;
import com.asmaa.hr.employeemanagement.dto.EmployeeResponse;
import com.asmaa.hr.employeemanagement.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeMapperTests {

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private EmployeeMapper employeeMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void toEmployee_test(){
        EmployeeRequest employeeRequest = new EmployeeRequest("asmaa", "hr",
                "developer", 1, LocalDate.now(), "employed", "asmaa@gmail.com",
                "Street", "rabat", "5676", "rabat");
        Address address = new Address("Street", "rabat", "5676", "rabat");
        Department department = Department.builder().id(1).name("IT").build();
        Employee employeeResult= this.employeeMapper.toEmployee(employeeRequest);

        assertEquals(0, employeeResult.getId());
        assertEquals("asmaa", employeeResult.getFirstName());
        assertEquals("hr", employeeResult.getLastName());
        assertEquals("developer", employeeResult.getJobTitle());
        assertEquals(1, employeeResult.getDepartment().getId());
        assertEquals("employed", employeeResult.getStatus());
        assertEquals(LocalDate.now(), employeeResult.getHireDate());
        assertEquals("asmaa@gmail.com", employeeResult.getEmail());
        assertEquals("Street", employeeResult.getAddress().getStreet());
        assertEquals("rabat", employeeResult.getAddress().getCity());
        assertEquals("5676", employeeResult.getAddress().getZipCode());
        assertEquals("rabat", employeeResult.getAddress().getState());

    }

    @Test
    public void toEmployeeResponse_test(){
        Department department = Department.builder().id(1).name("IT").build();
        User user = User.builder().username("asmaa").password("asmaa").role(UserRole.ADMIN).id(1).build();
        Address address = new Address("Street", "rabat", "5676", "rabat");
        Employee employee = new Employee(1, "asmaa", "hermak",
                "software developer" , department, LocalDate.now(), "employed", user,
                "asmaa@gmail.com", address);
        DepartmentResponse departmentResponse = new DepartmentResponse(1, "IT", employee.getId());
        when(departmentMapper.toDepartmentResponse(department)).thenReturn(departmentResponse);
        EmployeeResponse employeeResponse = this.employeeMapper.toEmployeeResponse(employee);

        assertEquals(employee.getId(),employeeResponse.id());
        assertEquals(employee.getFirstName(),employeeResponse.firstName());
        assertEquals(employee.getLastName(),employeeResponse.lastName());
        assertEquals(employee.getJobTitle(),employeeResponse.jobTitle());
        assertEquals(employee.getDepartment().getId(),employeeResponse.departmentResponse().id());
        assertEquals(employee.getDepartment().getName(),employeeResponse.departmentResponse().name());
        assertEquals(employee.getHireDate(),employeeResponse.hireDate());
        assertEquals(employee.getStatus(),employeeResponse.status());
        assertEquals(employee.getEmail(),employeeResponse.email());
        assertEquals(employee.getAddress().getCity(),employeeResponse.city());
        assertEquals(employee.getAddress().getZipCode(),employeeResponse.zipCode());
        assertEquals(employee.getAddress().getStreet(),employeeResponse.street());
        assertEquals(employee.getAddress().getState(),employeeResponse.state());





    }
}
