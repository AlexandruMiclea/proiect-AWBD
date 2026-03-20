package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.SupplierCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.SupplierRetrievalMapper;
import org.alexmiclea.reptopetrol.model.Supplier;
import org.alexmiclea.reptopetrol.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierCreationMapper supplierCreationMapper;
    private final SupplierRetrievalMapper supplierRetrievalMapper;

    public List<SupplierRetrievalDto> getAll() {
        return supplierRetrievalMapper.toSupplierDtos(supplierRepository.findAll());
    }

    public SupplierRetrievalDto getSupplierById(UUID uuid) {
        return supplierRetrievalMapper.toSupplierDto(supplierRepository.findById(uuid).orElseThrow());
    }

    public void addSupplier(SupplierCreationDto supplierDto) {
        Supplier supplier = supplierCreationMapper.toSupplier(supplierDto);
        supplierRepository.save(supplier);
    }

    public void bulkAddSuppliers(List<SupplierCreationDto> supplierDtos) {
        List<Supplier> suppliers = supplierCreationMapper.toSuppliers(supplierDtos);
        supplierRepository.saveAll(suppliers);
    }

    public void updateSupplier(SupplierCreationDto supplierDto, UUID uuid) {
        Supplier currentSupplier = supplierRepository.getReferenceById(uuid);
        currentSupplier.setName(supplierDto.getName());
        currentSupplier.setAddress(supplierDto.getAddress());
        currentSupplier.setHomeCountry(supplierDto.getHomeCountry());
        supplierRepository.save(currentSupplier);
    }

    public void deleteSupplier(UUID uuid) {
        Supplier supplier = supplierRepository.findById(uuid).orElseThrow();
        supplierRepository.delete(supplier);
    }
}
