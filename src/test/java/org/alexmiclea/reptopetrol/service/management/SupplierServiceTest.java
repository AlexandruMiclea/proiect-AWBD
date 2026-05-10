package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.SupplierCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.SupplierRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Supplier;
import org.alexmiclea.reptopetrol.repository.management.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test-unit")
public class SupplierServiceTest {

    @Mock private SupplierRepository supplierRepository;
    @Mock private SupplierCreationMapper supplierCreationMapper;
    @Mock private SupplierRetrievalMapper supplierRetrievalMapper;

    @InjectMocks private SupplierService supplierService;

    @Test
    void getAll_returnsMappedList() {
        Supplier supplier = new Supplier();
        SupplierRetrievalDto dto = SupplierRetrievalDto.builder().name("Petrom").address("Bucharest").homeCountry("Romania").build();
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(supplierRetrievalMapper.toSupplierDtos(List.of(supplier))).thenReturn(List.of(dto));

        List<SupplierRetrievalDto> result = supplierService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getSupplierById_whenExists_returnsDto() {
        UUID id = UUID.randomUUID();
        Supplier supplier = new Supplier();
        SupplierRetrievalDto dto = SupplierRetrievalDto.builder().id(id).name("Petrom").build();
        when(supplierRepository.existsById(id)).thenReturn(true);
        when(supplierRepository.findById(id)).thenReturn(Optional.of(supplier));
        when(supplierRetrievalMapper.toSupplierDto(supplier)).thenReturn(dto);

        Optional<SupplierRetrievalDto> result = supplierService.getSupplierById(id);

        assertThat(result).contains(dto);
    }

    @Test
    void getSupplierById_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(supplierRepository.existsById(id)).thenReturn(false);

        Optional<SupplierRetrievalDto> result = supplierService.getSupplierById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void addSupplier_mapsAndSaves() {
        SupplierCreationDto dto = SupplierCreationDto.builder().name("Petrom").address("Bucharest").homeCountry("Romania").build();
        Supplier supplier = new Supplier();
        when(supplierCreationMapper.toSupplier(dto)).thenReturn(supplier);

        supplierService.addSupplier(dto);

        verify(supplierRepository).save(supplier);
    }

    @Test
    void updateSupplier_getsReferenceAndSaves() {
        UUID id = UUID.randomUUID();
        SupplierCreationDto dto = SupplierCreationDto.builder().name("Rompetrol").address("Cluj").homeCountry("Romania").build();
        Supplier existing = new Supplier();
        when(supplierRepository.getReferenceById(id)).thenReturn(existing);

        supplierService.updateSupplier(dto, id);

        assertThat(existing.getName()).isEqualTo("Rompetrol");
        assertThat(existing.getAddress()).isEqualTo("Cluj");
        assertThat(existing.getHomeCountry()).isEqualTo("Romania");
        verify(supplierRepository).save(existing);
    }

    @Test
    void deleteSupplier_whenExists_deletesAndReturnsId() {
        UUID id = UUID.randomUUID();
        when(supplierRepository.existsById(id)).thenReturn(true);

        Optional<UUID> result = supplierService.deleteSupplier(id);

        assertThat(result).contains(id);
        verify(supplierRepository).deleteById(id);
    }

    @Test
    void deleteSupplier_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(supplierRepository.existsById(id)).thenReturn(false);

        Optional<UUID> result = supplierService.deleteSupplier(id);

        assertThat(result).isEmpty();
        verify(supplierRepository, never()).deleteById(any());
    }
}
