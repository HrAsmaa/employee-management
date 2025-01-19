package com.asmaa.hr.employeemanagement.controller;

import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.dto.EmployeeRequest;
import com.asmaa.hr.employeemanagement.dto.EmployeeResponse;
import com.asmaa.hr.employeemanagement.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
 public class EmployeeControllerTest {

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;
     private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void findEmployee_Success() throws Exception{

        EmployeeResponse employeeResponse = new EmployeeResponse(
                1, "asmaa", "hr", "developer",
                new DepartmentResponse(1, "IT",1), "employed", LocalDate.now(),
                "asmaa@gmail.com", "street", "Rabat", "7890", "Rabat");

        when(employeeService.findEmployee(1, null, null, null, null))
                .thenReturn(List.of(employeeResponse));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/employees?id=1"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(employeeResponse.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", is(employeeResponse.firstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", is(employeeResponse.lastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].jobTitle", is(employeeResponse.jobTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentResponse.id", is(employeeResponse.departmentResponse().id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentResponse.name", is(employeeResponse.departmentResponse().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentResponse.managerId", is(employeeResponse.departmentResponse().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", is(employeeResponse.status())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hireDate", is(employeeResponse.hireDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", is(employeeResponse.email())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].street", is(employeeResponse.street())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city", is(employeeResponse.city())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].zipCode", is(employeeResponse.zipCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].state", is(employeeResponse.state()))
                );
    }

    @Test
    public void createEmployee_Success() throws Exception{
        EmployeeRequest employeeRequest = new EmployeeRequest("asmaa",
                "hr", "developer", 1, LocalDate.now(), "employed",
                "asmaa@gmail.com", "street", "Rabat", "122", "Rabat");

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                post("/api/v1/employees")
                .content(mapper.writeValueAsString(employeeRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    public void updateEmployee_Success() throws Exception{
        EmployeeRequest employeeRequest = new EmployeeRequest("asmaa",
                "hr", "developer", 1, LocalDate.now(), "employed",
                "asmaa@gmail.com", "street", "Rabat", "122", "Rabat");

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                put("/api/v1/employees/1")
                .content(mapper.writeValueAsString(employeeRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isAccepted());
    }

    @Test
    public void deleteEmployee_Success() throws Exception{
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                delete("/api/v1/employees/1"));

        response.andExpect(status().isNoContent());

    }
}
