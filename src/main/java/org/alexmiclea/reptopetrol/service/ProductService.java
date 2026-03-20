package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.ProductCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.ProductCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.ProductRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Product;
import org.alexmiclea.reptopetrol.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ProductRetrievalDto getProductById(UUID uuid) {
        return productRetrievalMapper.toProductDto(productRepository.findById(uuid).orElseThrow());
    }

    public void addProduct(ProductCreationDto productDto) {
        Product product = productCreationMapper.toProduct(productDto);
        productRepository.save(product);
    }

    public void bulkAddProducts(List<ProductCreationDto> productDtos) {
        List<Product> products = productCreationMapper.toProducts(productDtos);
        productRepository.saveAll(products);
    }

    public void updateProduct(ProductCreationDto productDto, UUID uuid) {
        Product currentProduct = productRepository.getReferenceById(uuid);
        currentProduct.setName(productDto.getName());
        currentProduct.setPrice(productDto.getPrice());
        productRepository.save(currentProduct);
    }

    public void deleteProduct(UUID uuid) {
        Product product = productRepository.findById(uuid).orElseThrow();
        productRepository.delete(product);
    }
}
