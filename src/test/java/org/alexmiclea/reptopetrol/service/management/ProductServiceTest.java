package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.ProductCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.ProductCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.ProductRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Product;
import org.alexmiclea.reptopetrol.repository.management.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test-unit")
class ProductServiceTest {

    @Mock private ProductRepository productRepository;
    @Mock private ProductCreationMapper productCreationMapper;
    @Mock private ProductRetrievalMapper productRetrievalMapper;

    @InjectMocks private ProductService productService;

    @Test
    void getAll_returnsMappedList() {
        Product product = new Product();
        ProductRetrievalDto dto = ProductRetrievalDto.builder().name("Oil").type("Lubricant").build();
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productRetrievalMapper.toProductDtos(List.of(product))).thenReturn(List.of(dto));

        List<ProductRetrievalDto> result = productService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getProductById_whenExists_returnsDto() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        ProductRetrievalDto dto = ProductRetrievalDto.builder().id(id).name("Oil").type("Lubricant").build();
        when(productRepository.existsById(id)).thenReturn(true);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRetrievalMapper.toProductDto(product)).thenReturn(dto);

        Optional<ProductRetrievalDto> result = productService.getProductById(id);

        assertThat(result).contains(dto);
    }

    @Test
    void getProductById_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(productRepository.existsById(id)).thenReturn(false);

        Optional<ProductRetrievalDto> result = productService.getProductById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void addProduct_mapsAndSaves() {
        ProductCreationDto dto = ProductCreationDto.builder().name("Oil").type("Lubricant").build();
        Product product = new Product();
        when(productCreationMapper.toProduct(dto)).thenReturn(product);

        productService.addProduct(dto);

        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_getsReferenceAndSaves() {
        UUID id = UUID.randomUUID();
        ProductCreationDto dto = ProductCreationDto.builder().name("Oil").type("Lubricant").build();
        Product existing = new Product();
        when(productRepository.getReferenceById(id)).thenReturn(existing);

        productService.updateProduct(dto, id);

        assertThat(existing.getName()).isEqualTo("Oil");
        assertThat(existing.getType()).isEqualTo("Lubricant");
        verify(productRepository).save(existing);
    }

    @Test
    void deleteProduct_whenExists_deletesAndReturnsId() {
        UUID id = UUID.randomUUID();
        when(productRepository.existsById(id)).thenReturn(true);

        Optional<UUID> result = productService.deleteProduct(id);

        assertThat(result).contains(id);
        verify(productRepository).deleteById(id);
    }

    @Test
    void deleteProduct_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(productRepository.existsById(id)).thenReturn(false);

        Optional<UUID> result = productService.deleteProduct(id);

        assertThat(result).isEmpty();
        verify(productRepository, never()).deleteById(any());
    }
}
