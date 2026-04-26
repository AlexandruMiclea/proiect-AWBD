package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.StoreCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StationRetrievalDto;
import org.alexmiclea.reptopetrol.dto.retrieval.StoreRetrievalDto;
import org.alexmiclea.reptopetrol.service.StationService;
import org.alexmiclea.reptopetrol.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO you need to add the station service and update the station to link the store id
// for the POST and PUT instructions

@Controller
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    private final StationService stationService;

    @GetMapping("/all")
    public String getStores(Model model) {
        log.info("GET /all called");
        model.addAttribute("stations", storeService.getAll());
        return "stores/index";
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

        // for the station in the storeDto, we need to add the storeId
        // TODO do some exception handling here in the future
        StationRetrievalDto retrievalDto = stationService.getStationById(storeDto.getStationId()).get();


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