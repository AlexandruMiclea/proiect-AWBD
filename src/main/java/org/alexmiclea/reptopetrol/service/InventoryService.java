package org.alexmiclea.reptopetrol.service;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.InventoryDto;
import org.alexmiclea.reptopetrol.mapper.InventoryMapper;
import org.alexmiclea.reptopetrol.model.composites.Inventory;
import org.alexmiclea.reptopetrol.model.composites.keys.InventoryKey;
import org.alexmiclea.reptopetrol.repository.InventoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(InventoryKey key) {
        return inventoryRepository.findById(key).orElseThrow();
    }

    public ResponseEntity<Void> addInventory(InventoryDto inventoryDto) {
        Inventory inventory = inventoryMapper.toInventory(inventoryDto);
        inventoryRepository.save(inventory);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bulkAddInventories(List<InventoryDto> inventoryDtos) {
        List<Inventory> inventories = inventoryMapper.toInventories(inventoryDtos);
        inventoryRepository.saveAll(inventories);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> updateInventory(InventoryDto inventoryDto, InventoryKey key) {
        Inventory currentInventory = inventoryRepository.getReferenceById(key);
        currentInventory.setQuantity(inventoryDto.getQuantity());
        currentInventory.setPrice(inventoryDto.getPrice());
        inventoryRepository.save(currentInventory);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteInventory(InventoryKey key) {
        Inventory inventory = inventoryRepository.findById(key).orElseThrow();
        inventoryRepository.delete(inventory);
        return ResponseEntity.ok().build();
    }
}
