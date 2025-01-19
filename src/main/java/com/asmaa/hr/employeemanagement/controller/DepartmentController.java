package com.asmaa.hr.employeemanagement.controller;

import com.asmaa.hr.employeemanagement.dto.DepartmentRequest;
import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> findAllDepartment() {
        return ResponseEntity.ok(this.departmentService.findAllDepartment());
    }

    @PostMapping
    public ResponseEntity<Integer> createDepartment(@RequestBody @Valid DepartmentRequest departmentRequest) {
        return ResponseEntity.ok(this.departmentService.createDepartment(departmentRequest));
    }

    @PostMapping("/{department-id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable("department-id") Integer departmentId,
                                                 @RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity.ok(this.departmentService.updateDepartment(departmentId, departmentRequest));
    }

    @DeleteMapping("/{department-id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("department-id") Integer departmentId) {
        this.departmentService.deleteDepartment(departmentId);
        return ResponseEntity.noContent().build();
    }



}
