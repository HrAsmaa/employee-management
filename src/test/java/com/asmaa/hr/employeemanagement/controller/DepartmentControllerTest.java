package com.asmaa.hr.employeemanagement.controller;

import com.asmaa.hr.employeemanagement.dto.DepartmentRequest;
import com.asmaa.hr.employeemanagement.dto.DepartmentResponse;
import com.asmaa.hr.employeemanagement.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {


    @MockitoBean
    private DepartmentService departmentService;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void findAllDepartment_Success() throws Exception{

        DepartmentResponse departmentResponse = new DepartmentResponse(1, "IT",1);

        when(departmentService.findAllDepartment()).thenReturn(List.of(departmentResponse));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/departments"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(departmentResponse.id())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is(departmentResponse.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].managerId", is(departmentResponse.managerId())));
    }

    @Test
    public void createDepartment_Success() throws Exception{
        DepartmentRequest departmentRequest = new DepartmentRequest( "IT", 1);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                post("/api/v1/departments")
                .content(new ObjectMapper().writeValueAsString(departmentRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    public void deleteDepartment_Success() throws Exception{
         ResultActions response = mockMvc.perform(MockMvcRequestBuilders.
                delete("/api/v1/departments/1"));

        response.andExpect(status().isNoContent());

    }
}
