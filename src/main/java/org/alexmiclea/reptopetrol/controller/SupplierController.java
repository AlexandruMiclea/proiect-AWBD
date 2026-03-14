package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.SupplierDto;
import org.alexmiclea.reptopetrol.mapper.SupplierMapper;
import org.alexmiclea.reptopetrol.model.Supplier;
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
    private final SupplierMapper supplierMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Supplier>> getSuppliers() {
        return ResponseEntity.ok(supplierService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Supplier> getSupplier(@RequestParam UUID uuid) {
        return ResponseEntity.ok(supplierService.getSupplierById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addSupplier(@RequestBody SupplierDto supplierDto) {
        supplierService.addSupplier(supplierDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddSuppliers(@RequestBody List<SupplierDto> supplierDtos) {
        supplierService.bulkAddSuppliers(supplierDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateSupplier(@RequestBody SupplierDto supplierDto) {
        supplierService.updateSupplier(supplierDto, supplierDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteSupplier(@RequestParam UUID uuid) {
        supplierService.deleteSupplier(uuid);
        return ResponseEntity.ok().build();
    }
}
