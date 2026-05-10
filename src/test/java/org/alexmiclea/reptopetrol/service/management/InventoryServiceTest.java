package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.composites.InventoryCreationDto;
import org.alexmiclea.reptopetrol.dto.management.keys.InventoryKeyDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.composites.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.InventoryCreationMapper;
import org.alexmiclea.reptopetrol.mapper.keys.InventoryKeyMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.InventoryRetreivalMapper;
import org.alexmiclea.reptopetrol.model.management.composites.Inventory;
import org.alexmiclea.reptopetrol.model.management.keys.InventoryKey;
import org.alexmiclea.reptopetrol.repository.management.InventoryRepository;
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
class InventoryServiceTest {

    @Mock private InventoryRepository inventoryRepository;
    @Mock private InventoryCreationMapper inventoryCreationMapper;
    @Mock private InventoryRetreivalMapper inventoryRetreivalMapper;
    @Mock private InventoryKeyMapper inventoryKeyMapper;

    @InjectMocks private InventoryService inventoryService;

    @Test
    void getAll_returnsMappedList() {
        Inventory inventory = new Inventory();
        InventoryRetrievalDto dto = InventoryRetrievalDto.builder().quantity(10).price(9.99f).build();
        when(inventoryRepository.findAll()).thenReturn(List.of(inventory));
        when(inventoryRetreivalMapper.toInventoryDtos(List.of(inventory))).thenReturn(List.of(dto));

        List<InventoryRetrievalDto> result = inventoryService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getInventoryById_whenExists_returnsDto() {
        InventoryKey key = new InventoryKey();
        key.setStoreId(UUID.randomUUID());
        key.setProductId(UUID.randomUUID());
        Inventory inventory = new Inventory();
        InventoryRetrievalDto dto = InventoryRetrievalDto.builder().quantity(5).price(4.50f).build();
        when(inventoryRepository.existsById(key)).thenReturn(true);
        when(inventoryRepository.findById(key)).thenReturn(Optional.of(inventory));
        when(inventoryRetreivalMapper.toInventoryDto(inventory)).thenReturn(dto);

        Optional<InventoryRetrievalDto> result = inventoryService.getInventoryById(key);

        assertThat(result).contains(dto);
    }

    @Test
    void getInventoryById_whenNotExists_returnsEmpty() {
        InventoryKey key = new InventoryKey();
        when(inventoryRepository.existsById(key)).thenReturn(false);

        Optional<InventoryRetrievalDto> result = inventoryService.getInventoryById(key);

        assertThat(result).isEmpty();
    }

    @Test
    void addInventory_mapsAndSaves() {
        InventoryKeyDto keyDto = new InventoryKeyDto(UUID.randomUUID(), UUID.randomUUID());
        InventoryCreationDto dto = InventoryCreationDto.builder().id(keyDto).quantity(20).price(5.99f).build();
        Inventory inventory = new Inventory();
        when(inventoryCreationMapper.toInventory(dto)).thenReturn(inventory);

        inventoryService.addInventory(dto);

        verify(inventoryRepository).save(inventory);
    }

    @Test
    void updateInventory_mapsKeyAndSaves() {
        UUID storeId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        InventoryKeyDto keyDto = new InventoryKeyDto(storeId, productId);
        InventoryCreationDto dto = InventoryCreationDto.builder()
                .id(keyDto).quantity(30).price(7.49f).build();
        InventoryKey inventoryKey = new InventoryKey();
        inventoryKey.setStoreId(storeId);
        inventoryKey.setProductId(productId);
        Inventory existing = new Inventory();

        when(inventoryKeyMapper.toInventoryKey(keyDto)).thenReturn(inventoryKey);
        when(inventoryRepository.getReferenceById(inventoryKey)).thenReturn(existing);

        inventoryService.updateInventory(dto, keyDto);

        assertThat(existing.getQuantity()).isEqualTo(30);
        assertThat(existing.getPrice()).isEqualTo(7.49f);
        verify(inventoryRepository).save(existing);
    }

    @Test
    void deleteInventory_whenExists_deletesAndReturnsKey() {
        InventoryKey key = new InventoryKey();
        key.setStoreId(UUID.randomUUID());
        key.setProductId(UUID.randomUUID());
        when(inventoryRepository.existsById(key)).thenReturn(true);

        Optional<InventoryKey> result = inventoryService.deleteInventory(key);

        assertThat(result).contains(key);
        verify(inventoryRepository).deleteById(key);
    }

    @Test
    void deleteInventory_whenNotExists_returnsEmpty() {
        InventoryKey key = new InventoryKey();
        when(inventoryRepository.existsById(key)).thenReturn(false);

        Optional<InventoryKey> result = inventoryService.deleteInventory(key);

        assertThat(result).isEmpty();
        verify(inventoryRepository, never()).deleteById(any());
    }
}
