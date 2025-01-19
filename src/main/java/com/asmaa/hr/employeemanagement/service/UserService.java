package com.asmaa.hr.employeemanagement.service;

import com.asmaa.hr.employeemanagement.dto.UserRequest;
import com.asmaa.hr.employeemanagement.dto.UserResponse;
import com.asmaa.hr.employeemanagement.entity.Department;
import com.asmaa.hr.employeemanagement.entity.Employee;
import com.asmaa.hr.employeemanagement.entity.User;
import com.asmaa.hr.employeemanagement.mapper.UserMapper;
import com.asmaa.hr.employeemanagement.repository.EmployeeRepository;
import com.asmaa.hr.employeemanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    public UserResponse findUserByUsername(String username) {
        return this.userMapper.toUserResponse(this.userRepository.findByUsername(username).orElseThrow(
                () ->  new EntityNotFoundException("No user found with the provided username")
        ));
    }

    public Integer createUser(UserRequest userRequest) {
        if(this.employeeRepository.existsById(userRequest.employeeId())) {
            User user = this.userMapper.toUser(userRequest);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return this.userRepository.save(user).getId();
        } else {
            throw new EntityNotFoundException("No employee found with the provided id");
        }
    }

    public UserResponse updateUser(Integer userId, UserRequest userRequest) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("No user found with the provided id"));
        return this.userMapper.toUserResponse(
                this.userRepository.save(this.mergeUser(user, userRequest)));
    }

    private User mergeUser(User user, UserRequest userRequest) {
        if(userRequest.username()!=null){
            user.setUsername(userRequest.username());
        }if(userRequest.password()!=null){
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.password()));
        }if(userRequest.employeeId()!=null){
            if(this.employeeRepository.existsById(userRequest.employeeId())) {
                user.setEmployee(Employee.builder().id(userRequest.employeeId()).build());
            } else {
                throw new EntityNotFoundException("No employee found with the provided id");
            }
        }if(userRequest.role()!=null){
            user.setRole(userRequest.role());
        }
        return user;
    }

    public void deleteUser(Integer userId) {
        this.userRepository.deleteById(userId);
    }

}
