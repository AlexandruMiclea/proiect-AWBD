package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.FuelCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.FuelRetrievalDto;
import org.alexmiclea.reptopetrol.model.FuelType;
import org.alexmiclea.reptopetrol.service.FuelService;
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

@WebMvcTest(FuelController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class FuelControllerTest {

    private static final String API_STRING = "/api/fuels/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FuelService fuelService;

    @Test
    void getFuelById() throws Exception {
        UUID fuelId = UUID.randomUUID();

        FuelRetrievalDto mockFuelRetrieval = FuelRetrievalDto.builder()
                .id(fuelId)
                .name("Diesel")
                .type(FuelType.DIESEL)
                .build();

        Mockito.when(fuelService.getFuelById(fuelId)).thenReturn(Optional.of(mockFuelRetrieval));

        mockMvc.perform(get(API_STRING + fuelId).param("uuid", fuelId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(fuelId.toString()))
                .andExpect(jsonPath("$.name").value("Diesel"))
                .andExpect(jsonPath("$.type").value("DIESEL"));
    }

    @Test
    void getAllFuels() throws Exception {
        UUID fuelId = UUID.randomUUID();

        FuelRetrievalDto mockFuelRetrieval = FuelRetrievalDto.builder()
                .id(fuelId)
                .name("Diesel")
                .type(FuelType.DIESEL)
                .build();

        Mockito.when(fuelService.getAll()).thenReturn(List.of(mockFuelRetrieval));

        mockMvc.perform(get(API_STRING + "all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(fuelId.toString()))
                .andExpect(jsonPath("$[0].name").value("Diesel"))
                .andExpect(jsonPath("$[0].type").value("DIESEL"));
    }

    @Test
    void getNonExistantFuel() throws Exception {
        UUID nonExistantFuelId = UUID.randomUUID();

        mockMvc.perform(get(API_STRING + nonExistantFuelId).param("uuid", nonExistantFuelId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addFuel() throws Exception {
        FuelCreationDto mockFuelCreation = FuelCreationDto.builder()
                .name("Diesel")
                .type(FuelType.DIESEL)
                .build();

        mockMvc.perform(post(API_STRING + "add")
                        .content(objectMapper.writeValueAsString(mockFuelCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void bulkAddFuels() throws Exception {
        FuelCreationDto mockFuelCreation = FuelCreationDto.builder()
                .name("Diesel")
                .type(FuelType.DIESEL)
                .build();

        mockMvc.perform(post(API_STRING + "bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockFuelCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateFuel() throws Exception {
        UUID fuelId = UUID.randomUUID();

        FuelCreationDto mockFuelCreation = FuelCreationDto.builder()
                .name("Diesel")
                .type(FuelType.DIESEL)
                .build();

        mockMvc.perform(put(API_STRING + "update/" + fuelId)
                        .content(objectMapper.writeValueAsString(mockFuelCreation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("uuid", fuelId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFuel_notFound() throws Exception {
        UUID fuelId = UUID.randomUUID();

        mockMvc.perform(delete(API_STRING + "delete/" + fuelId).param("uuid", fuelId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFuel_found() throws Exception {
        UUID fuelId = UUID.randomUUID();

        Mockito.when(fuelService.deleteFuel(fuelId)).thenReturn(Optional.of(fuelId));

        mockMvc.perform(delete(API_STRING + "delete/" + fuelId).param("uuid", fuelId.toString()))
                .andExpect(status().isOk());
    }
}
