package com.asmaa.hr.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Department {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    Employee manager ;
}
