package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.ProductCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.ProductRetrievalDto;
import org.alexmiclea.reptopetrol.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductRetrievalDto>> getProducts() {
        log.info("GET /all called");

        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductRetrievalDto> getProduct(@PathVariable UUID uuid) {
        log.info("GET /{} called", uuid);
        Optional<ProductRetrievalDto> product = productService.getProductById(uuid);
        log.debug("Database response for GET: {}", product);

        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProduct(@RequestBody @Validated ProductCreationDto productDto) {
        log.info("POST /add called with payload {}", productDto);
        productService.addProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddProducts(@RequestBody @Validated List<ProductCreationDto> productDtos) {
        log.info("POST /bulkAdd called with payload {}", productDtos);
        productService.bulkAddProducts(productDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateProduct(@RequestBody @Validated ProductCreationDto productDto, @PathVariable UUID uuid) {
        log.info("PUT /update called with payload {} for UUID {}", productDto, uuid);
        productService.updateProduct(productDto, uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UUID> deleteProduct(@PathVariable UUID uuid) {
        log.info("DELETE /delete called for UUID {}", uuid);
        Optional<UUID> response = productService.deleteProduct(uuid);
        log.debug("Database response for DELETE: {}", response);

        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}