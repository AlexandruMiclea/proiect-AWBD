package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.ContractCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.ContractRetrievalDto;
import org.alexmiclea.reptopetrol.service.ContractService;
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

@WebMvcTest(ContractController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class ContractControllerTest {

    private static final String API_STRING = "/api/contracts/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ContractService contractService;

    @Test
    void getContractById() throws Exception {
        UUID contractId = UUID.randomUUID();

        ContractRetrievalDto mockContractRetrieval = ContractRetrievalDto.builder()
                .id(contractId)
                .supplierId(UUID.randomUUID())
                .beginDate(Instant.parse("2024-01-01T00:00:00Z"))
                .endDate(Instant.parse("2025-01-01T00:00:00Z"))
                .build();

        Mockito.when(contractService.getContractById(contractId)).thenReturn(Optional.of(mockContractRetrieval));

        mockMvc.perform(get(API_STRING + contractId).param("uuid", contractId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(contractId.toString()));
    }

    @Test
    void getAllContracts() throws Exception {
        UUID contractId = UUID.randomUUID();

        ContractRetrievalDto mockContractRetrieval = ContractRetrievalDto.builder()
                .id(contractId)
                .supplierId(UUID.randomUUID())
                .beginDate(Instant.parse("2024-01-01T00:00:00Z"))
                .endDate(Instant.parse("2025-01-01T00:00:00Z"))
                .build();

        Mockito.when(contractService.getAll()).thenReturn(List.of(mockContractRetrieval));

        mockMvc.perform(get(API_STRING + "all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(contractId.toString()));
    }

    @Test
    void getNonExistantContract() throws Exception {
        UUID nonExistantContractId = UUID.randomUUID();

        mockMvc.perform(get(API_STRING + nonExistantContractId).param("uuid", nonExistantContractId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addContract() throws Exception {
        ContractCreationDto mockContractCreation = ContractCreationDto.builder()
                .beginDate(Instant.parse("2024-01-01T00:00:00Z"))
                .endDate(Instant.parse("2025-01-01T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "add")
                        .content(objectMapper.writeValueAsString(mockContractCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void bulkAddContracts() throws Exception {
        ContractCreationDto mockContractCreation = ContractCreationDto.builder()
                .beginDate(Instant.parse("2024-01-01T00:00:00Z"))
                .endDate(Instant.parse("2025-01-01T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockContractCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateContract() throws Exception {
        UUID contractId = UUID.randomUUID();

        ContractCreationDto mockContractCreation = ContractCreationDto.builder()
                .beginDate(Instant.parse("2024-01-01T00:00:00Z"))
                .endDate(Instant.parse("2025-01-01T00:00:00Z"))
                .build();

        mockMvc.perform(put(API_STRING + "update/" + contractId)
                        .content(objectMapper.writeValueAsString(mockContractCreation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("uuid", contractId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteContract() throws Exception {
        UUID contractId = UUID.randomUUID();

        mockMvc.perform(delete(API_STRING + "delete/" + contractId).param("uuid", contractId.toString()))
                .andExpect(status().isOk());
    }
}
