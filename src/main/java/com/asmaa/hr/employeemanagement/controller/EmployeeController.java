package com.asmaa.hr.employeemanagement.controller;

import com.asmaa.hr.employeemanagement.dto.EmployeeRequest;
import com.asmaa.hr.employeemanagement.dto.EmployeeResponse;
import com.asmaa.hr.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> findEmployee(@RequestParam(required = false) Integer id,
                                                               @RequestParam(required = false) String firstName,
                                                               @RequestParam(required = false) String lastName,
                                                               @RequestParam(required = false) String jobTitle,
                                                               @RequestParam(required = false) Integer departmentId) {
        return ResponseEntity.ok(this.employeeService
                .findEmployee(id,firstName, lastName, jobTitle, departmentId));
    }

    @PostMapping
    public ResponseEntity<Integer> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(this.employeeService.createEmployee(employeeRequest));
    }

    @PutMapping("/{employee-id}")
    public ResponseEntity<Void> updateEmployee(@RequestBody EmployeeRequest employeeRequest,
                                               @PathVariable("employee-id") Integer employeeId) {
        this.employeeService.updateEmployee(employeeId, employeeRequest);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{employee-id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employee-id") Integer employeeId) {
        this.employeeService.deleteEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

}
