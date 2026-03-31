package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.InventoryCreationDto;
import org.alexmiclea.reptopetrol.dto.keys.InventoryKeyDto;
import org.alexmiclea.reptopetrol.dto.retrieval.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.InventoryKeyMapper;
import org.alexmiclea.reptopetrol.model.keys.InventoryKey;
import org.alexmiclea.reptopetrol.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    private final InventoryKeyMapper inventoryKeyMapper;

    @GetMapping("/all")
    public ResponseEntity<List<InventoryRetrievalDto>> getInventories() {
        log.info("GET /all called");

        return ResponseEntity.ok(inventoryService.getAll());
    }

    @GetMapping
    public ResponseEntity<InventoryRetrievalDto> getInventory(@RequestBody InventoryKeyDto key) {
        log.info("GET called with composed key {}", key);
        InventoryKey inventoryKey = inventoryKeyMapper.toInventoryKey(key);

        Optional<InventoryRetrievalDto> inventory = inventoryService.getInventoryById(inventoryKey);
        log.debug("Database response for GET: {}", inventory);

        return inventory.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addInventory(@RequestBody InventoryCreationDto inventoryDto) {
        log.info("POST /add called with payload {}", inventoryDto);
        inventoryService.addInventory(inventoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddInventories(@RequestBody List<InventoryCreationDto> inventoryDtos) {
        inventoryService.bulkAddInventories(inventoryDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateInventory(@RequestBody InventoryCreationDto inventoryDto) {
        log.info("PUT /update called with payload {}", inventoryDto);
        inventoryService.updateInventory(inventoryDto, inventoryDto.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<InventoryKey> deleteInventory(@RequestBody InventoryKeyDto key) {
        log.info("DELETE called with composed key {}", key);
        InventoryKey inventoryKey = inventoryKeyMapper.toInventoryKey(key);
        Optional<InventoryKey> inventory = inventoryService.deleteInventory(inventoryKey);
        log.debug("Database response for DELETE: {}", inventory);

        return inventory.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
