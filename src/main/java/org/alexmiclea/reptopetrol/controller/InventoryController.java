package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.InventoryDto;
import org.alexmiclea.reptopetrol.mapper.InventoryMapper;
import org.alexmiclea.reptopetrol.model.composites.Inventory;
import org.alexmiclea.reptopetrol.model.composites.keys.InventoryKey;
import org.alexmiclea.reptopetrol.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryMapper inventoryMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getInventories() {
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @GetMapping("/get")
    public ResponseEntity<Inventory> getInventory(@RequestBody InventoryKey key) {
        return ResponseEntity.ok(inventoryService.getInventoryById(key));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addInventory(@RequestBody InventoryDto inventoryDto) {
        inventoryService.addInventory(inventoryDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddInventories(@RequestBody List<InventoryDto> inventoryDtos) {
        inventoryService.bulkAddInventories(inventoryDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateInventory(@RequestBody InventoryDto inventoryDto) {
        inventoryService.updateInventory(inventoryDto, inventoryDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteInventory(@RequestBody InventoryKey key) {
        inventoryService.deleteInventory(key);
        return ResponseEntity.ok().build();
    }
}
