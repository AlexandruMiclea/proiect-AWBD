package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.InventoryCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.model.composites.keys.InventoryKey;
import org.alexmiclea.reptopetrol.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/all")
    public ResponseEntity<List<InventoryRetrievalDto>> getInventories() {
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @GetMapping("/get")
    public ResponseEntity<InventoryRetrievalDto> getInventory(@RequestBody InventoryKey key) {
        return ResponseEntity.ok(inventoryService.getInventoryById(key));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addInventory(@RequestBody InventoryCreationDto inventoryDto) {
        inventoryService.addInventory(inventoryDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddInventories(@RequestBody List<InventoryCreationDto> inventoryDtos) {
        inventoryService.bulkAddInventories(inventoryDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateInventory(@RequestBody InventoryCreationDto inventoryDto) {
        inventoryService.updateInventory(inventoryDto, inventoryDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteInventory(@RequestBody InventoryKey key) {
        inventoryService.deleteInventory(key);
        return ResponseEntity.ok().build();
    }
}
