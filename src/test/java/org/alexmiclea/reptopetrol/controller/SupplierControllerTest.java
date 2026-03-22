package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SupplierController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class SupplierControllerTest {

    private static final String API_STRING = "/api/suppliers/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SupplierService supplierService;

    @Test
    void getSupplierById() throws Exception {
        UUID supplierId = UUID.randomUUID();

        SupplierRetrievalDto mockSupplierRetrieval = SupplierRetrievalDto.builder()
                .id(supplierId)
                .address("Mock address")
                .name("Mock name")
                .homeCountry("RO")
                .build();

        Mockito.when(supplierService.getSupplierById(supplierId)).thenReturn(Optional.of(mockSupplierRetrieval));

        mockMvc.perform(get(API_STRING + supplierId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(supplierId.toString()))
                .andExpect(jsonPath("$.address").value("Mock address"))
                .andExpect(jsonPath("$.name").value("Mock name"))
                .andExpect(jsonPath("$.homeCountry").value("RO"));
    }

    @Test
    void getAllSuppliers() throws Exception {
        UUID supplierId = UUID.randomUUID();

        SupplierRetrievalDto mockSupplierRetrieval = SupplierRetrievalDto.builder()
                .id(supplierId)
                .address("Mock address")
                .name("Mock name")
                .homeCountry("RO")
                .build();

        Mockito.when(supplierService.getAll()).thenReturn(List.of(mockSupplierRetrieval));

        mockMvc.perform(get(API_STRING + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(jsonPath("$[0].id").value(supplierId.toString()))
                .andExpect(jsonPath("$[0].address").value("Mock address"))
                .andExpect(jsonPath("$[0].name").value("Mock name"))
                .andExpect(jsonPath("$[0].homeCountry").value("RO"));
    }

    @Test
    void getNonExistantSupplier() throws Exception {
        UUID nonExistantSupplierId = UUID.randomUUID();

        mockMvc.perform(get(API_STRING + nonExistantSupplierId))
            .andExpect(status().isNotFound());
    }

    @Test
    void addSupplier() throws Exception {

        SupplierCreationDto mockSupplierCreation = SupplierCreationDto.builder()
                .address("Mock address")
                .name("Mock name")
                .homeCountry("RO")
                .build();

        mockMvc.perform(post(API_STRING + "/add")
                        .content(objectMapper.writeValueAsString(mockSupplierCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void bulkAddSuppliers() throws Exception {

        SupplierCreationDto mockSupplierCreation = SupplierCreationDto.builder()
                .address("Mock address")
                .name("Mock name")
                .homeCountry("RO")
                .build();

        mockMvc.perform(post(API_STRING + "/bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockSupplierCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateSupplier() throws Exception {
        UUID supplierId = UUID.randomUUID();

        SupplierCreationDto mockSupplierCreation = SupplierCreationDto.builder()
                .address("Mock address")
                .name("Mock name")
                .homeCountry("RO")
                .build();

//        Mockito.when(supplierService.updateSupplier(mockSupplierCreation, supplierId);

        mockMvc.perform(put(API_STRING + "/update/" + supplierId)
                        .content(objectMapper.writeValueAsString(mockSupplierCreation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSupplier() throws Exception {
        UUID supplierId = UUID.randomUUID();

        mockMvc.perform(delete(API_STRING + "/delete/" + supplierId))
                .andExpect(status().isOk());
    }
}