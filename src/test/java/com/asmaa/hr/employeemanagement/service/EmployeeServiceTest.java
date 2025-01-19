package com.asmaa.hr.employeemanagement.service;

import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.dto.EmployeeRequest;
import com.asmaa.hr.employeemanagement.dto.EmployeeResponse;
import com.asmaa.hr.employeemanagement.entity.Address;
import com.asmaa.hr.employeemanagement.entity.Department;
import com.asmaa.hr.employeemanagement.entity.Employee;
import com.asmaa.hr.employeemanagement.entity.User;
import com.asmaa.hr.employeemanagement.mapper.EmployeeMapper;
import com.asmaa.hr.employeemanagement.repository.DepartmentRepository;
import com.asmaa.hr.employeemanagement.repository.EmployeeRepository;
import com.asmaa.hr.employeemanagement.specification.EmployeeSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private DepartmentRepository departmentRepository;


    @InjectMocks
    private EmployeeService employeeService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findEmployee_ThenReturnEmployeee() {
        Department department = new Department(1, "IT", null, null);
        Address address = new Address("Street", "rabat", "5676", "rabat");
        DepartmentResponse departmentResponse = new DepartmentResponse(1, "IT", 1);
        Employee employee = new Employee(1, "asmaa", "hr",
                "developer",department ,LocalDate.now(), "employed", User.builder().build(),
                "asmaa@gmail.com", address);
        EmployeeResponse employeeResponse = new EmployeeResponse(1, "asmaa", "hr",
                "developer" ,departmentResponse , "employed", LocalDate.now(),
                "asmaa@gmail.com", "Street", "rabat", "5676", "rabat");
        when(this.employeeRepository.findAll(
                any(EmployeeSpecification.class))).
                thenReturn(List.of(employee));
        when(this.employeeMapper.toEmployeeResponse(employee)).thenReturn(employeeResponse);
        List<EmployeeResponse> employeeResponses = this.employeeService.findEmployee(1, null, null, null, null);
        assertEquals(1, employeeResponses.size());
        assertEquals(1, employeeResponses.get(0).id());
        assertEquals("asmaa", employeeResponses.get(0).firstName());
        assertEquals("hr", employeeResponses.get(0).lastName());
        assertEquals("developer", employeeResponses.get(0).jobTitle());
        assertEquals(1, employeeResponses.get(0).departmentResponse().id());
        assertEquals("IT", employeeResponses.get(0).departmentResponse().name());
        assertEquals("employed", employeeResponses.get(0).status());
        assertEquals(LocalDate.now(), employeeResponses.get(0).hireDate());
        assertEquals("asmaa@gmail.com", employeeResponses.get(0).email());
        assertEquals("Street", employeeResponses.get(0).street());
        assertEquals("rabat", employeeResponses.get(0).city());
        assertEquals("5676", employeeResponses.get(0).zipCode());
        assertEquals("rabat", employeeResponses.get(0).state());




    }

    @Test
    public void createEmployee_thenReturnIdEmployee() {
        EmployeeRequest employeeRequest = new EmployeeRequest("asmaa", "hr",
                "developer", 1, LocalDate.now(), "employed", "asmaa@gmail.com",
                "Street", "rabat", "5676", "rabat");
        Department department = new Department(1, "IT", null ,null);
        Address address = new Address("Street", "rabat", "5676", "rabat");
        Employee employee = new Employee(1, "asmaa", "hr",
                "developer" ,department ,LocalDate.now(), "employed", User.builder().id(1).build(),
                "asmaa@gmail.com", address);

        when(this.employeeRepository.save(employee)).thenReturn(employee);
        when(this.employeeMapper.toEmployee(employeeRequest)).thenReturn(employee);
        when(this.departmentRepository.existsById(1)).thenReturn(true);

        Integer id = this.employeeService.createEmployee(employeeRequest);

        assertEquals(1, id);
    }

    @Test
    public void updateEmployee_success() {
        EmployeeRequest employeeRequest = new EmployeeRequest("asmaa", "hr",
                "developer", 1, LocalDate.now(), "employed", "asmaa@gmail.com",
                "Street", "rabat", "5676", "rabat");
        Department department = new Department(2, "IT", null, null);
        Address address = new Address("Street", "rabat", "5676", "rabat");
        Employee employee = new Employee(1, "asma", "hermak",
                "software developer" ,department ,LocalDate.now(), "employed", User.builder().id(1).build(),
                "asmaa@gmail.com", address);

        when(this.employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(this.departmentRepository.existsById(1)).thenReturn(true);


        assertDoesNotThrow(() -> this.employeeService.updateEmployee(1, employeeRequest));

    }

    @Test
    public void deleteEmployee_success() {
        assertDoesNotThrow(() -> departmentRepository.deleteById(1));

    }
}
