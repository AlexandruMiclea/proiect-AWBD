package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.InventoryCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.InventoryCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.InventoryRetreivalMapper;
import org.alexmiclea.reptopetrol.model.composites.Inventory;
import org.alexmiclea.reptopetrol.model.composites.keys.InventoryKey;
import org.alexmiclea.reptopetrol.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryCreationMapper inventoryCreationMapper;
    private final InventoryRetreivalMapper inventoryRetreivalMapper;

    public List<InventoryRetrievalDto> getAll() {
        return inventoryRetreivalMapper.toInventoryDtos(inventoryRepository.findAll());
    }

    public InventoryRetrievalDto getInventoryById(InventoryKey key) {
        return inventoryRetreivalMapper.toInventoryDto(inventoryRepository.findById(key).orElseThrow());
    }

    public void addInventory(InventoryCreationDto inventoryDto) {
        Inventory inventory = inventoryCreationMapper.toInventory(inventoryDto);
        inventoryRepository.save(inventory);
    }

    public void bulkAddInventories(List<InventoryCreationDto> inventoryDtos) {
        List<Inventory> inventories = inventoryCreationMapper.toInventories(inventoryDtos);
        inventoryRepository.saveAll(inventories);
    }

    public void updateInventory(InventoryCreationDto inventoryDto, InventoryKey key) {
        Inventory currentInventory = inventoryRepository.getReferenceById(key);
        currentInventory.setQuantity(inventoryDto.getQuantity());
        currentInventory.setPrice(inventoryDto.getPrice());
        inventoryRepository.save(currentInventory);
    }

    public void deleteInventory(InventoryKey key) {
        Inventory inventory = inventoryRepository.findById(key).orElseThrow();
        inventoryRepository.delete(inventory);
    }
}
