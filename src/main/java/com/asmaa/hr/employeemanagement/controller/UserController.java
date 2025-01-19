package com.asmaa.hr.employeemanagement.controller;

import com.asmaa.hr.employeemanagement.dto.DepartmentRequest;
import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.dto.UserRequest;
import com.asmaa.hr.employeemanagement.dto.UserResponse;
import com.asmaa.hr.employeemanagement.service.DepartmentService;
import com.asmaa.hr.employeemanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> findUserByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(this.userService.findUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(this.userService.createUser(userRequest));
    }

    @PostMapping("/{user-id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("user-id") Integer userId,
                                                   @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(this.userService.updateUser(userId, userRequest));
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user-id") Integer userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


}
