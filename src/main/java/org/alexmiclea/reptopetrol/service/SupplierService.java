package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.SupplierDto;
import org.alexmiclea.reptopetrol.mapper.SupplierMapper;
import org.alexmiclea.reptopetrol.model.Supplier;
import org.alexmiclea.reptopetrol.repository.SupplierRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(UUID uuid) {
        return supplierRepository.findById(uuid).orElseThrow();
    }

    public ResponseEntity<Void> addSupplier(SupplierDto supplierDto) {
        Supplier supplier = supplierMapper.toSupplier(supplierDto);
        supplierRepository.save(supplier);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddSuppliers(List<SupplierDto> supplierDtos) {
        List<Supplier> suppliers = supplierMapper.toSuppliers(supplierDtos);
        supplierRepository.saveAll(suppliers);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateSupplier(SupplierDto supplierDto, UUID uuid) {
        Supplier currentSupplier = supplierRepository.getReferenceById(uuid);
        currentSupplier.setName(supplierDto.getName());
        currentSupplier.setAddress(supplierDto.getAddress());
        currentSupplier.setHomeCountry(supplierDto.getHomeCountry());
        supplierRepository.save(currentSupplier);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteSupplier(UUID uuid) {
        Supplier supplier = supplierRepository.findById(uuid).orElseThrow();
        supplierRepository.delete(supplier);
        return ResponseEntity.ok().build();
    }
}
