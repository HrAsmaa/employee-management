package com.asmaa.hr.employeemanagement.repository;

import com.asmaa.hr.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> , JpaSpecificationExecutor<Employee> {
}
