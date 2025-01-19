package com.asmaa.hr.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private String jobTitle;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    private LocalDate hireDate;
    private String status;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @Column(unique = true, nullable = false)
    private String email;

    @Embedded
    private Address address;
}
