package com.asmaa.hr.employeemanagement.service;

import com.asmaa.hr.employeemanagement.dto.*;
import com.asmaa.hr.employeemanagement.entity.Employee;
import com.asmaa.hr.employeemanagement.entity.User;
import com.asmaa.hr.employeemanagement.entity.UserRole;
import com.asmaa.hr.employeemanagement.mapper.UserMapper;
import com.asmaa.hr.employeemanagement.repository.EmployeeRepository;
import com.asmaa.hr.employeemanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private UserService userService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findUserByUsername_WhenUserExist_thenReturnUser(){
        Employee employee = Employee.builder().id(1).build();
        User user = new User( 1, employee,"asmaa", "hr", UserRole.ADMIN);

        DepartmentResponse departmentResponse = new DepartmentResponse(1, "IT", 1);
        EmployeeResponse employeeResponse = new EmployeeResponse(1, "asmaa", "hr",
                "developer" ,departmentResponse , "employed", LocalDate.now(),
                "asmaa@gmail.com", "Street", "rabat", "5676", "rabat");

        UserResponse userResponse = new UserResponse(1, "asmaa", "hr", UserRole.ADMIN, employeeResponse);

        when(this.userRepository.findByUsername("asmaa")).thenReturn(Optional.of(user));
        when(this.userMapper.toUserResponse(user)).thenReturn(userResponse);

        UserResponse result = this.userService.findUserByUsername("asmaa");

        assertEquals(userResponse.id(), result.id());
        assertEquals(userResponse.username(), result.username());
        assertEquals(userResponse.password(), result.password());
        assertEquals(userResponse.role(), result.role());
        assertEquals(userResponse.employee(), result.employee());

    }

    @Test
    public void createUser_thenReturnUserId(){
        Employee employee = Employee.builder().id(1).build();
        UserRequest userRequest = new UserRequest(1, "asma", "hr", UserRole.ADMIN);
        User user = new User( 1, employee,"asmaa", "hr", UserRole.ADMIN);


        when(this.employeeRepository.existsById(userRequest.employeeId())).thenReturn(true);
        when(this.userMapper.toUser(userRequest)).thenReturn(user);
        when(this.userRepository.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());;

        Integer result = this.userService.createUser(userRequest);

        assertEquals(user.getId(), result);

    }

    @Test
    public void updateUser_thenReturnUser(){

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        UserRequest userRequest = new UserRequest(1, "asm", "dev", UserRole.HR);
        User user = new User( 1, Employee.builder().id(2).build(), "asmaa", "hr", UserRole.ADMIN);
        User userUpdated = new User( 1, Employee.builder().id(1).build(),"asm", any(String.class), UserRole.HR);
        EmployeeResponse employeeResponse = new EmployeeResponse(1, "asmaa", "hr",
                "developer" ,null , "employed", LocalDate.now(),
                "asmaa@gmail.com", "Street", "rabat", "5676", "rabat");
        UserResponse userResponse = new UserResponse(1, "asm", "dev", UserRole.HR, employeeResponse);


        when(this.userRepository.findById(1)).thenReturn(Optional.of(user));
        when(this.userRepository.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());;
        when(this.userMapper.toUserResponse(argThat(u ->
                userUpdated.getRole().equals(u.getRole()) && userUpdated.getUsername().equals(u.getUsername())
                && userUpdated.getId()==u.getId() && userUpdated.getEmployee().equals(u.getEmployee())))).thenReturn(userResponse);
        when(this.employeeRepository.existsById(userRequest.employeeId())).thenReturn(true);


        UserResponse result = this.userService.updateUser(1, userRequest);

        assertEquals(user.getId(), result.id());
        assertEquals(userRequest.username(), result.username());
        assertEquals(userRequest.password(), result.password());
        assertEquals(employeeResponse, result.employee());


    }

    @Test
    public void deleteUser_Success(){
        assertDoesNotThrow(() -> userRepository.deleteById(1));

    }
}
