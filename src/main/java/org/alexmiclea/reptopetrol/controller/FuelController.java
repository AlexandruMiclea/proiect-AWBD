package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.creation.FuelCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.FuelRetrievalDto;
import org.alexmiclea.reptopetrol.service.FuelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/api/fuel")
@RequiredArgsConstructor
@Slf4j
public class FuelController {

    private final FuelService fuelService;

    @GetMapping("/all")
    public String getFuels(Model model) {
        log.info("GET /all called");
        model.addAttribute("fuels", fuelService.getAll());
        return "fuels/index";
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FuelRetrievalDto> getFuel(@PathVariable UUID uuid) {
        log.info("GET /{} called", uuid);
        Optional<FuelRetrievalDto> fuel = fuelService.getFuelById(uuid);
        log.debug("Database response for GET: {}", fuel);

        return fuel.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFuel(@RequestBody @Validated FuelCreationDto fuelDto) {
        log.info("POST /add called with payload {}", fuelDto);
        fuelService.addFuel(fuelDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddFuels(@RequestBody @Validated List<FuelCreationDto> fuelDtos) {
        log.info("POST /bulkAdd called with payload {}", fuelDtos);
        fuelService.bulkAddFuels(fuelDtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateFuel(@RequestBody @Validated FuelCreationDto fuelDto, @PathVariable UUID uuid) {
        log.info("PUT /update called with payload {} for UUID {}", fuelDto, uuid);
        fuelService.updateFuel(fuelDto, uuid);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<UUID> deleteFuel(@PathVariable UUID uuid) {
        log.info("DELETE /delete called for UUID {}", uuid);
        Optional<UUID> response = fuelService.deleteFuel(uuid);
        log.debug("Database response for DELETE: {}", response);

        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}