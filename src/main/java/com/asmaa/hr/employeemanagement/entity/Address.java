package com.asmaa.hr.employeemanagement.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class Address {

    private String street;
    private String city;
    private String zipCode;
    private String state;
}
