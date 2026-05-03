package org.alexmiclea.reptopetrol.controller.management;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.management.creation.composites.InventoryCreationDto;
import org.alexmiclea.reptopetrol.dto.management.keys.InventoryKeyDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.composites.InventoryRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.keys.InventoryKeyMapper;
import org.alexmiclea.reptopetrol.model.management.keys.InventoryKey;
import org.alexmiclea.reptopetrol.service.management.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
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
    public String getInventories(Model model) {
        log.info("GET /all called");
        model.addAttribute("inventories", inventoryService.getAll());
        return "inventories/index";
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
    public ResponseEntity<Void> addInventory(@RequestBody @Validated InventoryCreationDto inventoryDto) {
        log.info("POST /add called with payload {}", inventoryDto);
        inventoryService.addInventory(inventoryDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateInventory(@RequestBody @Validated InventoryCreationDto inventoryDto) {
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
