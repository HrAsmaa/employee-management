package com.asmaa.hr.employeemanagement.service;

import com.asmaa.hr.employeemanagement.dto.DepartmentRequest;
import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.entity.Department;
import com.asmaa.hr.employeemanagement.entity.Employee;
import com.asmaa.hr.employeemanagement.mapper.DepartmentMapper;
import com.asmaa.hr.employeemanagement.repository.DepartmentRepository;
import com.asmaa.hr.employeemanagement.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final EmployeeRepository employeeRepository;


    public Integer createDepartment(DepartmentRequest departmentRequest) {
        return this.departmentRepository.save(this.departmentMapper.toDepartment(departmentRequest)).getId();
    }

    public List<DepartmentResponse> findAllDepartment() {
        return this.departmentRepository.findAll().stream()
                .map(this.departmentMapper::toDepartmentResponse)
                .toList();
    }

    public void deleteDepartment(Integer departmentId) {
        this.departmentRepository.deleteById(departmentId);
    }

    public DepartmentResponse updateDepartment(Integer departmentId, DepartmentRequest departmentRequest) {
        Department department = this.departmentRepository.findById(departmentId).orElseThrow(
                () -> new EntityNotFoundException("No department found with the provided id"));
        return this.departmentMapper.toDepartmentResponse(
                this.departmentRepository.save(this.mergeDepartment(department, departmentRequest)));
    }

    private Department mergeDepartment(Department department, DepartmentRequest departmentRequest) {
        if(departmentRequest.name()!=null){
            department.setName(departmentRequest.name());
        }if(departmentRequest.managerId()!=null){
            if(employeeRepository.existsById(departmentRequest.managerId())) {
                department.setManager(Employee.builder().id(departmentRequest.managerId()).build());
            }else{
                throw new EntityNotFoundException("No employee found with the provided id");
            }
        }
        return department;
    }
}
