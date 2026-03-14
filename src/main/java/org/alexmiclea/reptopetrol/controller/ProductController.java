package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.ProductDto;
import org.alexmiclea.reptopetrol.mapper.ProductMapper;
import org.alexmiclea.reptopetrol.model.Product;
import org.alexmiclea.reptopetrol.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Product> getProduct(@RequestParam UUID uuid) {
        return ResponseEntity.ok(productService.getProductById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddProducts(@RequestBody List<ProductDto> productDtos) {
        productService.bulkAddProducts(productDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto) {
        productService.updateProduct(productDto, productDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteProduct(@RequestParam UUID uuid) {
        productService.deleteProduct(uuid);
        return ResponseEntity.ok().build();
    }
}
