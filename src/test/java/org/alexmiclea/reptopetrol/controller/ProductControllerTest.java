package org.alexmiclea.reptopetrol.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.ProductCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.service.ProductService;
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

@WebMvcTest(ProductController.class)
@ActiveProfiles("test-unit")
@RequiredArgsConstructor
public class ProductControllerTest {

    private static final String API_STRING = "/api/products/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    void getProductById() throws Exception {
        UUID productId = UUID.randomUUID();

        ProductRetrievalDto mockProductRetrieval = ProductRetrievalDto.builder()
                .id(productId)
                .name("Motor Oil")
                .price(25.99f)
                .build();

        Mockito.when(productService.getProductById(productId)).thenReturn(mockProductRetrieval);

        mockMvc.perform(get(API_STRING + productId).param("uuid", productId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.name").value("Motor Oil"))
                .andExpect(jsonPath("$.price").value(25.99f));
    }

    @Test
    void getAllProducts() throws Exception {
        UUID productId = UUID.randomUUID();

        ProductRetrievalDto mockProductRetrieval = ProductRetrievalDto.builder()
                .id(productId)
                .name("Motor Oil")
                .price(25.99f)
                .build();

        Mockito.when(productService.getAll()).thenReturn(List.of(mockProductRetrieval));

        mockMvc.perform(get(API_STRING + "all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(productId.toString()))
                .andExpect(jsonPath("$[0].name").value("Motor Oil"))
                .andExpect(jsonPath("$[0].price").value(25.99f));
    }

    @Test
    void getNonExistantProduct() throws Exception {
        UUID nonExistantProductId = UUID.randomUUID();

        mockMvc.perform(get(API_STRING + nonExistantProductId).param("uuid", nonExistantProductId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void addProduct() throws Exception {
        ProductCreationDto mockProductCreation = ProductCreationDto.builder()
                .name("Motor Oil")
                .price(25.99f)
                .build();

        mockMvc.perform(post(API_STRING + "add")
                        .content(objectMapper.writeValueAsString(mockProductCreation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void bulkAddProducts() throws Exception {
        ProductCreationDto mockProductCreation = ProductCreationDto.builder()
                .name("Motor Oil")
                .price(25.99f)
                .build();

        mockMvc.perform(post(API_STRING + "bulkAdd")
                        .content(objectMapper.writeValueAsString(List.of(mockProductCreation)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct() throws Exception {
        UUID productId = UUID.randomUUID();

        ProductCreationDto mockProductCreation = ProductCreationDto.builder()
                .name("Motor Oil")
                .price(25.99f)
                .build();

        mockMvc.perform(put(API_STRING + "update/" + productId)
                        .content(objectMapper.writeValueAsString(mockProductCreation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("uuid", productId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        UUID productId = UUID.randomUUID();

        mockMvc.perform(delete(API_STRING + "delete/" + productId).param("uuid", productId.toString()))
                .andExpect(status().isOk());
    }
}