package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/stores")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/all")
    public ResponseEntity<List<StoreRetrievalDto>> getStores() {
        log.info("GET /all called");

        return ResponseEntity.ok(storeService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<StoreRetrievalDto> getStore(@PathVariable UUID uuid) {
        log.info("GET /{} called", uuid);
        Optional<StoreRetrievalDto> store = storeService.getStoreById(uuid);
        log.debug("Database response for GET: {}", store);

        return store.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addStore(@RequestBody @Validated StoreCreationDto storeDto) {
        log.info("POST /add called with payload {}", storeDto);
        storeService.addStore(storeDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddStores(@RequestBody @Validated List<StoreCreationDto> storeDtos) {
        log.info("POST /bulkAdd called with payload {}", storeDtos);
        storeService.bulkAddStores(storeDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateStore(@RequestBody @Validated StoreCreationDto storeDto, @PathVariable UUID uuid) {
        log.info("PUT /update called with payload {} for UUID {}", storeDto, uuid);
        storeService.updateStore(storeDto, uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UUID> deleteStore(@PathVariable UUID uuid) {
        log.info("DELETE /delete called for UUID {}", uuid);
        Optional<UUID> response = storeService.deleteStore(uuid);
        log.debug("Database response for DELETE: {}", response);

        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}