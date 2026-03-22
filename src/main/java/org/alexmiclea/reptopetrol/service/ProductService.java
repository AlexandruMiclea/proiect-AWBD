package org.alexmiclea.reptopetrol.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.ProductCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.ProductCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.ProductRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Product;
import org.alexmiclea.reptopetrol.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCreationMapper productCreationMapper;
    private final ProductRetrievalMapper productRetrievalMapper;

    public List<ProductRetrievalDto> getAll() {
        return productRetrievalMapper.toProductDtos(productRepository.findAll());
    }

    public Optional<ProductRetrievalDto> getProductById(UUID uuid) {
        if (productRepository.existsById(uuid)) {
            return Optional.of(productRetrievalMapper.toProductDto(productRepository.findById(uuid).orElseThrow()));
        } else {
            return Optional.empty();
        }
    }

    public void addProduct(ProductCreationDto productDto) {
        Product product = productCreationMapper.toProduct(productDto);
        productRepository.save(product);
    }

    public void bulkAddProducts(List<ProductCreationDto> productDtos) {
        List<Product> products = productCreationMapper.toProducts(productDtos);
        productRepository.saveAll(products);
    }

    @Transactional
    public void updateProduct(ProductCreationDto productDto, UUID uuid) {
        Product currentProduct = productRepository.getReferenceById(uuid);
        currentProduct.setName(productDto.getName());
        currentProduct.setPrice(productDto.getPrice());
        productRepository.save(currentProduct);
    }

    public Optional<UUID> deleteProduct(UUID uuid) {
        if (productRepository.existsById(uuid)) {
            productRepository.deleteById(uuid);
            return Optional.of(uuid);
        } else {
            return Optional.empty();
        }
    }
}