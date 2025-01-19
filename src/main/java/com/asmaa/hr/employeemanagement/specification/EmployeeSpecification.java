package com.asmaa.hr.employeemanagement.specification;

import com.asmaa.hr.employeemanagement.entity.Department;
import com.asmaa.hr.employeemanagement.entity.Employee;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class EmployeeSpecification implements Specification<Employee> {

    private Integer id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private Integer departmentId;
    private final List<Predicate> predicates = new ArrayList<>();

    public EmployeeSpecification(Integer id,
                                 String firstName,
                                 String lastName,
                                 String jobTitle,
                                 Integer departmentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.departmentId = departmentId;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if(id!=null){
            predicates.add(criteriaBuilder.equal(
                    root.get("id"),id
            ));
        }
        if(firstName!=null){
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("firstName")),
                    "%" + firstName.toLowerCase() + "%"

            ));
        }
        if(lastName!=null){
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("lastName")),
                    "%" + lastName.toLowerCase() + "%"

            ));
        }
        if(jobTitle!=null){
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("jobTitle")),
                    "%" + jobTitle.toLowerCase() + "%"

            ));
        }
        if(departmentId!=null){
            Join<Employee, Department> employeeDepartment = root.join("department");
            predicates.add(criteriaBuilder.equal(
                    employeeDepartment.get("id"),departmentId
            ));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
