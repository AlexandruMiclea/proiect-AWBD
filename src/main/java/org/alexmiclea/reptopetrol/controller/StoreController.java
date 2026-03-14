package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.StoreDto;
import org.alexmiclea.reptopetrol.mapper.StoreMapper;
import org.alexmiclea.reptopetrol.model.Store;
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
    private final StoreMapper storeMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Store>> getStores() {
        return ResponseEntity.ok(storeService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Store> getStore(@RequestParam UUID uuid) {
        return ResponseEntity.ok(storeService.getStoreById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addStore(@RequestBody StoreDto storeDto) {
        storeService.addStore(storeDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddStores(@RequestBody List<StoreDto> storeDtos) {
        storeService.bulkAddStores(storeDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateStore(@RequestBody StoreDto storeDto) {
        storeService.updateStore(storeDto, storeDto.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteStore(@RequestParam UUID uuid) {
        storeService.deleteStore(uuid);
        return ResponseEntity.ok().build();
    }
}
