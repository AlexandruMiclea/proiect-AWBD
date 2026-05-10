package org.alexmiclea.reptopetrol.integration;

import org.alexmiclea.reptopetrol.dto.management.creation.SupplierCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.SupplierRetrievalDto;
import org.alexmiclea.reptopetrol.repository.management.SupplierRepository;
import org.alexmiclea.reptopetrol.service.management.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the full create → read → update → delete lifecycle for a Supplier.
 * This exercises the service layer, MapStruct mappers, JPA repositories, and H2
 * all together without touching the HTTP layer.
 *
 * The @Transactional annotation rolls back after each test so tests are isolated.
 */
@Transactional
class SupplierLifecycleIntegrationTest extends BaseIntegrationTest {

    @Autowired SupplierService supplierService;
    @Autowired SupplierRepository supplierRepository;

    private SupplierCreationDto petrom() {
        return SupplierCreationDto.builder()
                .name("Petrom SA").address("Bucharest, Romania").homeCountry("Romania").build();
    }

    @Test
    void createSupplier_isRetrievable() {
        supplierService.addSupplier(petrom());

        List<SupplierRetrievalDto> all = supplierService.getAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getName()).isEqualTo("Petrom SA");
        assertThat(all.get(0).getHomeCountry()).isEqualTo("Romania");
    }

    @Test
    void updateSupplier_changesFieldsInDatabase() {
        supplierService.addSupplier(petrom());
        UUID id = supplierRepository.findAll().get(0).getId();

        supplierService.updateSupplier(
                SupplierCreationDto.builder()
                        .name("Rompetrol SA").address("Cluj-Napoca").homeCountry("Romania").build(),
                id);

        Optional<SupplierRetrievalDto> updated = supplierService.getSupplierById(id);
        assertThat(updated).isPresent();
        assertThat(updated.get().getName()).isEqualTo("Rompetrol SA");
        assertThat(updated.get().getAddress()).isEqualTo("Cluj-Napoca");
    }

    @Test
    void deleteSupplier_removesRecordFromDatabase() {
        supplierService.addSupplier(petrom());
        UUID id = supplierRepository.findAll().get(0).getId();

        Optional<UUID> deleted = supplierService.deleteSupplier(id);

        assertThat(deleted).contains(id);
        assertThat(supplierRepository.count()).isZero();
        assertThat(supplierService.getSupplierById(id)).isEmpty();
    }

    @Test
    void deleteSupplier_withUnknownId_returnsEmpty() {
        Optional<UUID> result = supplierService.deleteSupplier(UUID.randomUUID());

        assertThat(result).isEmpty();
        assertThat(supplierRepository.count()).isZero();
    }

    @Test
    void getSupplierById_withUnknownId_returnsEmpty() {
        Optional<SupplierRetrievalDto> result = supplierService.getSupplierById(UUID.randomUUID());

        assertThat(result).isEmpty();
    }
}
