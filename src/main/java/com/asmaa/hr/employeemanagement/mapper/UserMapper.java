package com.asmaa.hr.employeemanagement.mapper;


import com.asmaa.hr.employeemanagement.dto.UserRequest;
import com.asmaa.hr.employeemanagement.dto.UserResponse;
import com.asmaa.hr.employeemanagement.entity.Employee;
import com.asmaa.hr.employeemanagement.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserMapper {

    private final EmployeeMapper employeeMapper;

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getRole(),
                employeeMapper.toEmployeeResponse(user.getEmployee()));
    }

    public User toUser(@Valid UserRequest userRequest) {
        return User.builder()
                .username(userRequest.username())
                .password(userRequest.password())
                .role(userRequest.role())
                .employee(Employee.builder().id(userRequest.employeeId()).build())
                .build();
    }
}
