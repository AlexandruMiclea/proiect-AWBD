package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.ProductDto;
import org.alexmiclea.reptopetrol.mapper.ProductMapper;
import org.alexmiclea.reptopetrol.model.Product;
import org.alexmiclea.reptopetrol.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID uuid) {
        return productRepository.findById(uuid).orElseThrow();
    }

    public ResponseEntity<Void> addProduct(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddProducts(List<ProductDto> productDtos) {
        List<Product> products = productMapper.toProducts(productDtos);
        productRepository.saveAll(products);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateProduct(ProductDto productDto, UUID uuid) {
        Product currentProduct = productRepository.getReferenceById(uuid);
        currentProduct.setName(productDto.getName());
        currentProduct.setPrice(productDto.getPrice());
        productRepository.save(currentProduct);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteProduct(UUID uuid) {
        Product product = productRepository.findById(uuid).orElseThrow();
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }
}
