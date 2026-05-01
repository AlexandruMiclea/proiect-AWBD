package org.alexmiclea.reptopetrol.service.management;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.management.creation.composites.InventoryCreationDto;
import org.alexmiclea.reptopetrol.dto.management.keys.InventoryKeyDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.composites.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.InventoryCreationMapper;
import org.alexmiclea.reptopetrol.mapper.keys.InventoryKeyMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.InventoryRetreivalMapper;
import org.alexmiclea.reptopetrol.model.management.composites.Inventory;
import org.alexmiclea.reptopetrol.model.management.keys.InventoryKey;
import org.alexmiclea.reptopetrol.repository.management.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryCreationMapper inventoryCreationMapper;
    private final InventoryRetreivalMapper inventoryRetreivalMapper;
    private final InventoryKeyMapper inventoryKeyMapper;

    public List<InventoryRetrievalDto> getAll() {
        return inventoryRetreivalMapper.toInventoryDtos(inventoryRepository.findAll());
    }

    public Optional<InventoryRetrievalDto> getInventoryById(InventoryKey key) {
        if (inventoryRepository.existsById(key)) {
            return Optional.of(
                    inventoryRetreivalMapper.toInventoryDto(
                            inventoryRepository.findById(key)
                                    .orElseThrow()));
        } else {
            return Optional.empty();
        }
    }

    public void addInventory(InventoryCreationDto inventoryDto) {
        Inventory inventory = inventoryCreationMapper.toInventory(inventoryDto);
        inventoryRepository.save(inventory);
    }

    public void bulkAddInventories(List<InventoryCreationDto> inventoryDtos) {
        List<Inventory> inventories = inventoryCreationMapper.toInventories(inventoryDtos);
        inventoryRepository.saveAll(inventories);
    }

    @Transactional
    public void updateInventory(InventoryCreationDto inventoryDto, InventoryKeyDto key) {
        InventoryKey inventoryKey = inventoryKeyMapper.toInventoryKey(key);
        Inventory currentInventory = inventoryRepository.getReferenceById(inventoryKey);
        currentInventory.setQuantity(inventoryDto.getQuantity());
        currentInventory.setPrice(inventoryDto.getPrice());
        inventoryRepository.save(currentInventory);
    }

    public Optional<InventoryKey> deleteInventory(InventoryKey key) {
        if (inventoryRepository.existsById(key)){
            inventoryRepository.deleteById(key);
            return Optional.of(key);
        } else {
            return Optional.empty();
        }
    }
}
