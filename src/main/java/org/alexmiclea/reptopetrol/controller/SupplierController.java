package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        log.info("GET /" + uuid + " called");
        return ResponseEntity.ok(supplierService.getSupplierById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addSupplier(@RequestBody SupplierCreationDto supplierDto) {
        supplierService.addSupplier(supplierDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddSuppliers(@RequestBody List<SupplierCreationDto> supplierDtos) {
        supplierService.bulkAddSuppliers(supplierDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateSupplier(@RequestBody SupplierCreationDto supplierDto, @PathVariable UUID uuid) {
        supplierService.updateSupplier(supplierDto, uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID uuid) {
        supplierService.deleteSupplier(uuid);
        return ResponseEntity.ok().build();
    }
}
