package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/all")
    public ResponseEntity<List<SupplierRetrievalDto>> getSuppliers() {
        return ResponseEntity.ok(supplierService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SupplierRetrievalDto> getSupplier(@RequestParam UUID uuid) {
        return ResponseEntity.ok(supplierService.getSupplierById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addSupplier(@RequestBody SupplierCreationDto supplierDto) {
        supplierService.addSupplier(supplierDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddSuppliers(@RequestBody List<SupplierCreationDto> supplierDtos) {
        supplierService.bulkAddSuppliers(supplierDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateSupplier(@RequestBody SupplierCreationDto supplierDto, @RequestParam UUID uuid) {
        supplierService.updateSupplier(supplierDto, uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteSupplier(@RequestParam UUID uuid) {
        supplierService.deleteSupplier(uuid);
        return ResponseEntity.ok().build();
    }
}
