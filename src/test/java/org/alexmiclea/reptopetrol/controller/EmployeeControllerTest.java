package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.EmployeeRetrievalDto;
import org.alexmiclea.reptopetrol.model.EmployeeRole;
import org.alexmiclea.reptopetrol.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class EmployeeControllerTest {

    private static final String API_STRING = "/api/employees/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void getEmployeeById() throws Exception {
        UUID employeeId = UUID.randomUUID();

        EmployeeRetrievalDto mockEmployeeRetrieval = EmployeeRetrievalDto.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .identificationNumber("ID12345")
                .wage(3000)
                .role(EmployeeRole.CASHIER)
                .dateOfHire(Instant.parse("2022-06-01T00:00:00Z"))
                .build();

        Mockito.when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(mockEmployeeRetrieval));

        mockMvc.perform(get(API_STRING + employeeId).param("uuid", employeeId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(employeeId.toString()))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.identificationNumber").value("ID12345"))
                .andExpect(jsonPath("$.wage").value(3000))
                .andExpect(jsonPath("$.role").value("CASHIER"));
    }

    @Test
    void getAllEmployees() throws Exception {
        UUID employeeId = UUID.randomUUID();

        EmployeeRetrievalDto mockEmployeeRetrieval = EmployeeRetrievalDto.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .identificationNumber("ID12345")
                .wage(3000)
                .role(EmployeeRole.CASHIER)
                .dateOfHire(Instant.parse("2022-06-01T00:00:00Z"))
                .build();

        Mockito.when(employeeService.getAll()).thenReturn(List.of(mockEmployeeRetrieval));

        mockMvc.perform(get(API_STRING + "all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(employeeId.toString()))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    void getNonExistantEmployee() throws Exception {
        UUID nonExistantEmployeeId = UUID.randomUUID();

        mockMvc.perform(get(API_STRING + nonExistantEmployeeId).param("uuid", nonExistantEmployeeId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addEmployee() throws Exception {
        EmployeeCreationDto mockEmployeeCreation = EmployeeCreationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .identificationNumber("ID12345")
                .wage(3000)
                .role(EmployeeRole.CASHIER)
                .dateOfHire(Instant.parse("2022-06-01T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "add")
                        .content(objectMapper.writeValueAsString(mockEmployeeCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void bulkAddEmployees() throws Exception {
        EmployeeCreationDto mockEmployeeCreation = EmployeeCreationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .identificationNumber("ID12345")
                .wage(3000)
                .role(EmployeeRole.CASHIER)
                .dateOfHire(Instant.parse("2022-06-01T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockEmployeeCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();

        EmployeeCreationDto mockEmployeeCreation = EmployeeCreationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .identificationNumber("ID12345")
                .wage(3000)
                .role(EmployeeRole.CASHIER)
                .dateOfHire(Instant.parse("2022-06-01T00:00:00Z"))
                .build();

        mockMvc.perform(put(API_STRING + "update/" + employeeId)
                        .content(objectMapper.writeValueAsString(mockEmployeeCreation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("uuid", employeeId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();

        mockMvc.perform(delete(API_STRING + "delete/" + employeeId).param("uuid", employeeId.toString()))
                .andExpect(status().isOk());
    }
}
