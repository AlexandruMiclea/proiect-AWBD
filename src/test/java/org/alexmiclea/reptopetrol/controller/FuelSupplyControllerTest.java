package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.FuelSupplyCreationDto;
import org.alexmiclea.reptopetrol.dto.keys.FuelSupplyKeyDto;
import org.alexmiclea.reptopetrol.dto.retrieval.FuelSupplyRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.FuelSupplyKeyMapper;
import org.alexmiclea.reptopetrol.model.keys.FuelSupplyKey;
import org.alexmiclea.reptopetrol.service.FuelSupplyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FuelSupplyController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class FuelSupplyControllerTest {

    private static final String API_STRING = "/api/fuel-supplies";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FuelSupplyService fuelSupplyService;

    @MockitoBean
    private FuelSupplyKeyMapper fuelSupplyKeyMapper;

    @Test
    void getFuelSupplyByKey() throws Exception {
        UUID fuelId = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();

        FuelSupplyKeyDto fuelSupplyKeyDto = FuelSupplyKeyDto.builder()
                .fuelId(fuelId)
                .stationId(stationId)
                .build();

        FuelSupplyKey fuelSupplyKey = new FuelSupplyKey();
        fuelSupplyKey.setFuelId(fuelId);
        fuelSupplyKey.setStationId(stationId);

        FuelSupplyRetrievalDto mockRetrieval = FuelSupplyRetrievalDto.builder()
                .id(fuelSupplyKeyDto)
                .quantity(BigDecimal.valueOf(500))
                .price(BigDecimal.valueOf(7.45))
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        Mockito.when(fuelSupplyKeyMapper.toFuelSupplyKey(fuelSupplyKeyDto)).thenReturn(fuelSupplyKey);
        Mockito.when(fuelSupplyService.getFuelSupplyById(fuelSupplyKey)).thenReturn(Optional.of(mockRetrieval));

        mockMvc.perform(get(API_STRING)
                        .content(objectMapper.writeValueAsString(fuelSupplyKeyDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quantity").value(500))
                .andExpect(jsonPath("$.price").value(7.45));
    }

    @Test
    void getAllFuelSupplies() throws Exception {
        FuelSupplyRetrievalDto mockRetrieval = FuelSupplyRetrievalDto.builder()
                .quantity(BigDecimal.valueOf(500))
                .price(BigDecimal.valueOf(7.45))
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        Mockito.when(fuelSupplyService.getAll()).thenReturn(List.of(mockRetrieval));

        mockMvc.perform(get(API_STRING + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].quantity").value(500))
                .andExpect(jsonPath("$[0].price").value(7.45));
    }

    @Test
    void addFuelSupply() throws Exception {
        UUID fuelId = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();

        FuelSupplyKeyDto fuelSupplyKeyDto = FuelSupplyKeyDto.builder()
                .fuelId(fuelId)
                .stationId(stationId)
                .build();

        FuelSupplyCreationDto mockCreation = FuelSupplyCreationDto.builder()
                .id(fuelSupplyKeyDto)
                .quantity(BigDecimal.valueOf(500))
                .price(BigDecimal.valueOf(7.45))
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "/add")
                        .content(objectMapper.writeValueAsString(mockCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void bulkAddFuelSupplies() throws Exception {
        UUID fuelId = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();

        FuelSupplyKeyDto fuelSupplyKeyDto = FuelSupplyKeyDto.builder()
                .fuelId(fuelId)
                .stationId(stationId)
                .build();

        FuelSupplyCreationDto mockCreation = FuelSupplyCreationDto.builder()
                .id(fuelSupplyKeyDto)
                .quantity(BigDecimal.valueOf(500))
                .price(BigDecimal.valueOf(7.45))
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "/bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateFuelSupply() throws Exception {
        FuelSupplyCreationDto mockCreation = FuelSupplyCreationDto.builder()
                .quantity(BigDecimal.valueOf(500))
                .price(BigDecimal.valueOf(7.45))
                .priceChange(Instant.parse("2024-01-01T00:00:00Z"))
                .build();

        mockMvc.perform(put(API_STRING + "/update")
                        .content(objectMapper.writeValueAsString(mockCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFuelSupply() throws Exception {
        UUID fuelId = UUID.randomUUID();
        UUID stationId = UUID.randomUUID();

        FuelSupplyKeyDto fuelSupplyKeyDto = FuelSupplyKeyDto.builder()
                .fuelId(fuelId)
                .stationId(stationId)
                .build();

        FuelSupplyKey fuelSupplyKey = new FuelSupplyKey();
        fuelSupplyKey.setFuelId(fuelId);
        fuelSupplyKey.setStationId(stationId);

        Mockito.when(fuelSupplyKeyMapper.toFuelSupplyKey(fuelSupplyKeyDto)).thenReturn(fuelSupplyKey);

        mockMvc.perform(delete(API_STRING + "/delete")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}