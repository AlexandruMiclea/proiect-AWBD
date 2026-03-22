package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Slf4j
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/all")
    public ResponseEntity<List<SupplierRetrievalDto>> getSuppliers() {
        log.info("GET /all called");

        return ResponseEntity.ok(supplierService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SupplierRetrievalDto> getSupplier(@PathVariable UUID uuid) {
        log.info("GET /{} called", uuid);
        Optional<SupplierRetrievalDto> supplier = supplierService.getSupplierById(uuid);
        log.debug("Database response for GET: {}", supplier);

        return supplier.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addSupplier(@RequestBody @Validated SupplierCreationDto supplierDto) {
        log.info("POST /add called with payload {}", supplierDto);
        supplierService.addSupplier(supplierDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddSuppliers(@RequestBody @Validated List<SupplierCreationDto> supplierDtos) {
        log.info("POST /bulkAdd called with payload {}", supplierDtos);
        supplierService.bulkAddSuppliers(supplierDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateSupplier(@RequestBody SupplierCreationDto supplierDto, @PathVariable UUID uuid) {
        log.info("PUT /update called with payload {} for UUID {}", supplierDto, uuid);
        supplierService.updateSupplier(supplierDto, uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UUID> deleteSupplier(@PathVariable UUID uuid) {
        log.info("DELETE /update called for UUID {}", uuid);
        Optional<UUID> response = supplierService.deleteSupplier(uuid);
        log.debug("Database response for DELETE: {}", response);

        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
