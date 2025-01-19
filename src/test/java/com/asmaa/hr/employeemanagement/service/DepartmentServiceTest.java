package com.asmaa.hr.employeemanagement.service;

import com.asmaa.hr.employeemanagement.dto.DepartmentRequest;
import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.entity.Department;
import com.asmaa.hr.employeemanagement.entity.Employee;
import com.asmaa.hr.employeemanagement.mapper.DepartmentMapper;
import com.asmaa.hr.employeemanagement.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;



    @InjectMocks
    private DepartmentService departmentService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createDepartment__thenReturnDepartmentId(){
        DepartmentRequest departmentRequest = new DepartmentRequest("IT", 1);
        Department department = Department.builder().name("IT").build();
        when(this.departmentMapper.toDepartment(departmentRequest)).thenReturn(department);
        when(this.departmentRepository.save(department)).thenReturn(
                Department.builder().id(1).build()
        );
        Integer id = this.departmentService.createDepartment(departmentRequest);
        assertEquals(1, id);

    }

    @Test
    public void findAllDepartment_success(){
        List<Department> departments = List.of(
                Department.builder().id(1).name("IT").manager(Employee.builder().id(1).build()).build(),
                Department.builder().id(2).name("WEB").manager(Employee.builder().id(1).build()).build()
        );

        when(this.departmentRepository.findAll()).thenReturn(departments);
        when(this.departmentMapper.toDepartmentResponse(departments.get(0)))
                .thenReturn(new DepartmentResponse(1,"IT", 1));
        when(this.departmentMapper.toDepartmentResponse(departments.get(1)))
                .thenReturn(new DepartmentResponse(2,"WEB",1));

        List<DepartmentResponse> departmenetResponses = this.departmentService.findAllDepartment();

        assertEquals(2, departmenetResponses.size());
        assertEquals(1, departmenetResponses.get(0).id());
        assertEquals("IT", departmenetResponses.get(0).name());
        assertEquals(2, departmenetResponses.get(1).id());
        assertEquals("WEB", departmenetResponses.get(1).name());
    }

    @Test
    public void deleteEmployee_success(){
        assertDoesNotThrow(() -> departmentRepository.deleteById(1));

    }


}
