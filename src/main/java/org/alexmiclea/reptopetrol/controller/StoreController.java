package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/all")
    public ResponseEntity<List<StoreRetrievalDto>> getStores() {
        return ResponseEntity.ok(storeService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<StoreRetrievalDto> getStore(@RequestParam UUID uuid) {
        return ResponseEntity.ok(storeService.getStoreById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addStore(@RequestBody StoreCreationDto storeDto) {
        storeService.addStore(storeDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddStores(@RequestBody List<StoreCreationDto> storeDtos) {
        storeService.bulkAddStores(storeDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateStore(@RequestBody StoreCreationDto storeDto, @RequestParam UUID uuid) {
        storeService.updateStore(storeDto, uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteStore(@RequestParam UUID uuid) {
        storeService.deleteStore(uuid);
        return ResponseEntity.ok().build();
    }
}
