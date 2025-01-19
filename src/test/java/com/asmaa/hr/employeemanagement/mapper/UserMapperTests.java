package com.asmaa.hr.employeemanagement.mapper;

import com.asmaa.hr.employeemanagement.dto.*;
import com.asmaa.hr.employeemanagement.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserMapperTests {


    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }




    @Test
    public void toUserResponse_test(){
        Employee employee = Employee.builder().id(1).build();
        User user = new User( 1, employee,"asmaa", "hr", UserRole.ADMIN);
        DepartmentResponse departmentResponse = new DepartmentResponse(1, "IT", 1);
        EmployeeResponse employeeResponse = new EmployeeResponse(1, "asmaa", "hr",
                "developer" ,departmentResponse , "employed", LocalDate.now(),
                "asmaa@gmail.com", "Street", "rabat", "5676", "rabat");
        when(this.employeeMapper.toEmployeeResponse(employee)).thenReturn(employeeResponse);

        UserResponse result = this.userMapper.toUserResponse(user);
        assertEquals(user.getId(), result.id());
        assertEquals(user.getUsername(), result.username());
        assertEquals(user.getPassword(), result.password());
        assertEquals(employeeResponse, result.employee());

    }

    @Test
    public void toUser_test(){
        UserRequest userRequest = new UserRequest(1, "asma", "hr", UserRole.ADMIN);

        User user = this.userMapper.toUser(userRequest);

        assertEquals(user.getUsername(),userRequest.username());
        assertEquals(user.getPassword(),userRequest.password());
        assertEquals(user.getRole(),userRequest.role());
        assertEquals(user.getEmployee().getId(),userRequest.employeeId());


    }
}
