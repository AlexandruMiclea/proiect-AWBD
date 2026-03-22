package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.StationCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.service.StationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StationController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class StationControllerTest {

    private static final String API_STRING = "/api/stations/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StationService stationService;

    @Test
    void getStationById() throws Exception {
        UUID stationId = UUID.randomUUID();

        StationRetrievalDto mockStationRetrieval = StationRetrievalDto.builder()
                .id(stationId)
                .name("Station Alpha")
                .address("123 Main St")
                .pumpNo(4)
                .build();

        Mockito.when(stationService.getStationById(stationId)).thenReturn(mockStationRetrieval);

        mockMvc.perform(get(API_STRING + stationId).param("uuid", stationId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(stationId.toString()))
                .andExpect(jsonPath("$.name").value("Station Alpha"))
                .andExpect(jsonPath("$.address").value("123 Main St"))
                .andExpect(jsonPath("$.pumpNo").value(4));
    }

    @Test
    void getAllStations() throws Exception {
        UUID stationId = UUID.randomUUID();

        StationRetrievalDto mockStationRetrieval = StationRetrievalDto.builder()
                .id(stationId)
                .name("Station Alpha")
                .address("123 Main St")
                .pumpNo(4)
                .build();

        Mockito.when(stationService.getAll()).thenReturn(List.of(mockStationRetrieval));

        mockMvc.perform(get(API_STRING + "all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(stationId.toString()))
                .andExpect(jsonPath("$[0].name").value("Station Alpha"))
                .andExpect(jsonPath("$[0].address").value("123 Main St"))
                .andExpect(jsonPath("$[0].pumpNo").value(4));
    }

    @Test
    void getNonExistantStation() throws Exception {
        UUID nonExistantStationId = UUID.randomUUID();

        mockMvc.perform(get(API_STRING + nonExistantStationId).param("uuid", nonExistantStationId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addStation() throws Exception {
        StationCreationDto mockStationCreation = StationCreationDto.builder()
                .name("Station Alpha")
                .address("123 Main St")
                .pumpNo(4)
                .build();

        mockMvc.perform(post(API_STRING + "add")
                        .content(objectMapper.writeValueAsString(mockStationCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void bulkAddStations() throws Exception {
        StationCreationDto mockStationCreation = StationCreationDto.builder()
                .name("Station Alpha")
                .address("123 Main St")
                .pumpNo(4)
                .build();

        mockMvc.perform(post(API_STRING + "bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockStationCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateStation() throws Exception {
        UUID stationId = UUID.randomUUID();

        StationCreationDto mockStationCreation = StationCreationDto.builder()
                .name("Station Alpha")
                .address("123 Main St")
                .pumpNo(4)
                .build();

        mockMvc.perform(put(API_STRING + "update/" + stationId)
                        .content(objectMapper.writeValueAsString(mockStationCreation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("uuid", stationId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStation() throws Exception {
        UUID stationId = UUID.randomUUID();

        mockMvc.perform(delete(API_STRING + "delete/" + stationId).param("uuid", stationId.toString()))
                .andExpect(status().isOk());
    }
}