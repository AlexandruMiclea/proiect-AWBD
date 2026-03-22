package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.InventoryCreationDto;
import org.alexmiclea.reptopetrol.dto.keys.InventoryKeyDto;
import org.alexmiclea.reptopetrol.dto.retrieval.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.InventoryKeyMapper;
import org.alexmiclea.reptopetrol.model.keys.InventoryKey;
import org.alexmiclea.reptopetrol.service.InventoryService;
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

@WebMvcTest(InventoryController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class InventoryControllerTest {

    private static final String API_STRING = "/api/inventory";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private InventoryService inventoryService;

    @MockitoBean
    private InventoryKeyMapper inventoryKeyMapper;

    @Test
    void getInventoryByKey() throws Exception {
        UUID storeId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        InventoryKeyDto inventoryKeyDto = InventoryKeyDto.builder()
                .storeId(storeId)
                .productId(productId)
                .build();

        InventoryRetrievalDto mockRetrieval = InventoryRetrievalDto.builder()
                .id(inventoryKeyDto)
                .quantity(100)
                .price(12.99f)
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        InventoryKey inventoryKey = new InventoryKey();
        inventoryKey.setStoreId(storeId);
        inventoryKey.setProductId(productId);

        Mockito.when(inventoryKeyMapper.toInventoryKey(inventoryKeyDto)).thenReturn(inventoryKey);
        Mockito.when(inventoryService.getInventoryById(inventoryKey)).thenReturn(Optional.of(mockRetrieval));

        mockMvc.perform(get(API_STRING)
                        .content(objectMapper.writeValueAsString(inventoryKeyDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quantity").value(100))
                .andExpect(jsonPath("$.price").value(12.99f));
    }

    @Test
    void getAllInventories() throws Exception {
        InventoryRetrievalDto mockRetrieval = InventoryRetrievalDto.builder()
                .quantity(100)
                .price(12.99f)
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        Mockito.when(inventoryService.getAll()).thenReturn(List.of(mockRetrieval));

        mockMvc.perform(get(API_STRING + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].quantity").value(100))
                .andExpect(jsonPath("$[0].price").value(12.99f));
    }

    @Test
    void addInventory() throws Exception {
        InventoryCreationDto mockCreation = InventoryCreationDto.builder()
                .quantity(100)
                .price(12.99f)
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "/add")
                        .content(objectMapper.writeValueAsString(mockCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void bulkAddInventories() throws Exception {
        InventoryCreationDto mockCreation = InventoryCreationDto.builder()
                .quantity(100)
                .price(12.99f)
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "/bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateInventory() throws Exception {
        InventoryCreationDto mockCreation = InventoryCreationDto.builder()
                .quantity(100)
                .price(12.99f)
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        mockMvc.perform(put(API_STRING + "/update")
                        .content(objectMapper.writeValueAsString(mockCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteInventory() throws Exception {
        UUID storeId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        InventoryKeyDto inventoryKeyDto = InventoryKeyDto.builder()
                .storeId(storeId)
                .productId(productId)
                .build();

        InventoryKey inventoryKey = new InventoryKey();
        inventoryKey.setStoreId(storeId);
        inventoryKey.setProductId(productId);

        Mockito.when(inventoryKeyMapper.toInventoryKey(inventoryKeyDto)).thenReturn(inventoryKey);

        mockMvc.perform(delete(API_STRING + "/delete")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}