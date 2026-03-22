package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.TransportCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.TransportRetrievalDto;
import org.alexmiclea.reptopetrol.service.TransportService;
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

@WebMvcTest(TransportController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class TransportControllerTest {

    private static final String API_STRING = "/api/transports/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransportService transportService;

    @Test
    void getTransportById() throws Exception {
        UUID transportId = UUID.randomUUID();

        TransportRetrievalDto mockTransportRetrieval = TransportRetrievalDto.builder()
                .id(transportId)
                .companyName("FastFreight SRL")
                .creationDate(Instant.parse("2024-03-01T00:00:00Z"))
                .completionDate(Instant.parse("2024-03-05T00:00:00Z"))
                .build();

        Mockito.when(transportService.getTransportById(transportId)).thenReturn(Optional.of(mockTransportRetrieval));

        mockMvc.perform(get(API_STRING + transportId).param("uuid", transportId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(transportId.toString()))
                .andExpect(jsonPath("$.companyName").value("FastFreight SRL"));
    }

    @Test
    void getAllTransports() throws Exception {
        UUID transportId = UUID.randomUUID();

        TransportRetrievalDto mockTransportRetrieval = TransportRetrievalDto.builder()
                .id(transportId)
                .companyName("FastFreight SRL")
                .creationDate(Instant.parse("2024-03-01T00:00:00Z"))
                .completionDate(Instant.parse("2024-03-05T00:00:00Z"))
                .build();

        Mockito.when(transportService.getAll()).thenReturn(List.of(mockTransportRetrieval));

        mockMvc.perform(get(API_STRING + "all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(transportId.toString()))
                .andExpect(jsonPath("$[0].companyName").value("FastFreight SRL"));
    }

    @Test
    void getNonExistantTransport() throws Exception {
        UUID nonExistantTransportId = UUID.randomUUID();

        mockMvc.perform(get(API_STRING + nonExistantTransportId).param("uuid", nonExistantTransportId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addTransport() throws Exception {
        TransportCreationDto mockTransportCreation = TransportCreationDto.builder()
                .companyName("FastFreight SRL")
                .creationDate(Instant.parse("2024-03-01T00:00:00Z"))
                .completionDate(Instant.parse("2024-03-05T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "add")
                        .content(objectMapper.writeValueAsString(mockTransportCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void bulkAddTransports() throws Exception {
        TransportCreationDto mockTransportCreation = TransportCreationDto.builder()
                .companyName("FastFreight SRL")
                .creationDate(Instant.parse("2024-03-01T00:00:00Z"))
                .completionDate(Instant.parse("2024-03-05T00:00:00Z"))
                .build();

        mockMvc.perform(post(API_STRING + "bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockTransportCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateTransport() throws Exception {
        UUID transportId = UUID.randomUUID();

        TransportCreationDto mockTransportCreation = TransportCreationDto.builder()
                .companyName("FastFreight SRL")
                .creationDate(Instant.parse("2024-03-01T00:00:00Z"))
                .completionDate(Instant.parse("2024-03-05T00:00:00Z"))
                .build();

        mockMvc.perform(put(API_STRING + "update/" + transportId)
                        .content(objectMapper.writeValueAsString(mockTransportCreation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("uuid", transportId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTransport() throws Exception {
        UUID transportId = UUID.randomUUID();

        mockMvc.perform(delete(API_STRING + "delete/" + transportId).param("uuid", transportId.toString()))
                .andExpect(status().isOk());
    }
}