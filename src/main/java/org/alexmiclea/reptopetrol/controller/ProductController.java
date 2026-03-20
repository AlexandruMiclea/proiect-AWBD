package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.ProductCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.ProductRetrievalDto;
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

    @GetMapping("/all")
    public ResponseEntity<List<ProductRetrievalDto>> getProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductRetrievalDto> getProduct(@RequestParam UUID uuid) {
        return ResponseEntity.ok(productService.getProductById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProduct(@RequestBody ProductCreationDto productDto) {
        productService.addProduct(productDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddProducts(@RequestBody List<ProductCreationDto> productDtos) {
        productService.bulkAddProducts(productDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductCreationDto productDto, @RequestParam UUID uuid) {
        productService.updateProduct(productDto, uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteProduct(@RequestParam UUID uuid) {
        productService.deleteProduct(uuid);
        return ResponseEntity.ok().build();
    }
}
