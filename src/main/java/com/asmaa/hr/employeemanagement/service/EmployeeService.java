package com.asmaa.hr.employeemanagement.service;

import com.asmaa.hr.employeemanagement.dto.EmployeeRequest;
import com.asmaa.hr.employeemanagement.dto.EmployeeResponse;
import com.asmaa.hr.employeemanagement.entity.Address;
import com.asmaa.hr.employeemanagement.entity.Department;
import com.asmaa.hr.employeemanagement.entity.Employee;
import com.asmaa.hr.employeemanagement.mapper.EmployeeMapper;
import com.asmaa.hr.employeemanagement.repository.DepartmentRepository;
import com.asmaa.hr.employeemanagement.repository.EmployeeRepository;
import com.asmaa.hr.employeemanagement.specification.EmployeeSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentRepository departmentRepository;

    public List<EmployeeResponse> findEmployee(Integer id,
                                               String firstName,
                                               String lastName,
                                               String jobTitle,
                                               Integer departmentId) {
        return this.employeeRepository.findAll(
                new EmployeeSpecification(id,firstName, lastName, jobTitle, departmentId)).stream()
                                .map(employeeMapper::toEmployeeResponse).toList();
    }

    public Integer createEmployee(EmployeeRequest employeeRequest) {
        if(!this.departmentRepository.existsById(employeeRequest.departmentId())){
            throw new EntityNotFoundException("No department found with the provided id");
        }
        return this.employeeRepository.save(this.employeeMapper.toEmployee(employeeRequest)).getId();
    }

    public void updateEmployee(Integer employeeId, EmployeeRequest employeeRequest) {
        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(
                () -> new EntityNotFoundException("No employee found with the provided id"));
        this.employeeRepository.save(this.mergeEmployee(employee, employeeRequest));

    }

    private Employee mergeEmployee(Employee employee, EmployeeRequest employeeRequest) {
        Address address = employee.getAddress();
        if(employeeRequest.email()!=null){
            employee.setEmail(employeeRequest.email());
        }
        if(employeeRequest.firstName()!=null){
            employee.setFirstName(employeeRequest.firstName());
        }
        if(employeeRequest.lastName()!=null){
            employee.setLastName(employeeRequest.lastName());
        }
        if(employeeRequest.departmentId()!=null){
            if(!departmentRepository.existsById(employeeRequest.departmentId())){
                throw new EntityNotFoundException("No department found with the provided id");
            }
            employee.setDepartment(Department.builder().id(employeeRequest.departmentId()).build());
        }
        if(employeeRequest.jobTitle()!=null){
            employee.setJobTitle(employeeRequest.jobTitle());
        }
        if(employeeRequest.hireDate()!=null){
            employee.setHireDate(employeeRequest.hireDate());
        }
        if(employeeRequest.status()!=null){
            employee.setStatus(employeeRequest.status());
        }
        if(employeeRequest.street()!=null){
            address.setStreet(employeeRequest.street());
        }
        if(employeeRequest.city()!=null){
            address.setCity(employeeRequest.city());
        }
        if(employeeRequest.zipCode()!=null){
            address.setZipCode(employeeRequest.zipCode());
        }
        if(employeeRequest.state()!=null){
            address.setState(employeeRequest.state());
        }
        employee.setAddress(address);
        return employee;
    }

    public void deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
