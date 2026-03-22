package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.service.StoreService;
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

@WebMvcTest(StoreController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class StoreControllerTest {

    private static final String API_STRING = "/api/stores/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StoreService storeService;

    @Test
    void getStoreById() throws Exception {
        UUID storeId = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();

        StoreRetrievalDto mockStoreRetrieval = StoreRetrievalDto.builder()
                .id(storeId)
                .stationId(stationId)
                .build();

        Mockito.when(storeService.getStoreById(storeId)).thenReturn(Optional.of(mockStoreRetrieval));

        mockMvc.perform(get(API_STRING + storeId).param("uuid", storeId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(storeId.toString()))
                .andExpect(jsonPath("$.stationId").value(stationId.toString()));
    }

    @Test
    void getAllStores() throws Exception {
        UUID storeId = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();

        StoreRetrievalDto mockStoreRetrieval = StoreRetrievalDto.builder()
                .id(storeId)
                .stationId(stationId)
                .build();

        Mockito.when(storeService.getAll()).thenReturn(List.of(mockStoreRetrieval));

        mockMvc.perform(get(API_STRING + "all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(storeId.toString()))
                .andExpect(jsonPath("$[0].stationId").value(stationId.toString()));
    }

    @Test
    void getNonExistantStore() throws Exception {
        UUID nonExistantStoreId = UUID.randomUUID();

        mockMvc.perform(get(API_STRING + nonExistantStoreId).param("uuid", nonExistantStoreId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addStore() throws Exception {
        UUID storeId = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();

        StoreCreationDto mockStoreCreation = StoreCreationDto.builder()
                .stationId(stationId)
                .id(storeId)
                .build();

        mockMvc.perform(post(API_STRING + "add")
                        .content(objectMapper.writeValueAsString(mockStoreCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void bulkAddStores() throws Exception {
        StoreCreationDto mockStoreCreation = StoreCreationDto.builder()
                .build();

        mockMvc.perform(post(API_STRING + "bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockStoreCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateStore() throws Exception {
        UUID storeId = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();

        StoreCreationDto mockStoreCreation = StoreCreationDto.builder()
                .id(storeId)
                .stationId(stationId)
                .build();

        mockMvc.perform(put(API_STRING + "update/" + storeId)
                        .content(objectMapper.writeValueAsString(mockStoreCreation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("uuid", storeId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStore() throws Exception {
        UUID storeId = UUID.randomUUID();

        mockMvc.perform(delete(API_STRING + "delete/" + storeId).param("uuid", storeId.toString()))
                .andExpect(status().isNotFound());
    }
}