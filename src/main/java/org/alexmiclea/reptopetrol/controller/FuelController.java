package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.creation.FuelCreationDto;
import org.alexmiclea.reptopetrol.dto.retrieval.FuelRetrievalDto;
import org.alexmiclea.reptopetrol.service.FuelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fuels")
@RequiredArgsConstructor
public class FuelController {

    private final FuelService fuelService;

    @GetMapping("/all")
    public ResponseEntity<List<FuelRetrievalDto>> getFuels() {
        return ResponseEntity.ok(fuelService.getAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FuelRetrievalDto> getFuel(@PathVariable UUID uuid) {
        return ResponseEntity.ok(fuelService.getFuelById(uuid));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addFuel(@RequestBody FuelCreationDto fuelDto) {
        fuelService.addFuel(fuelDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bulkAdd")
    public ResponseEntity<Void> bulkAddFuels(@RequestBody List<FuelCreationDto> fuelDtos) {
        fuelService.bulkAddFuels(fuelDtos);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<Void> updateFuel(@RequestBody FuelCreationDto fuelDto, @PathVariable UUID uuid) {
        fuelService.updateFuel(fuelDto, uuid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<Void> deleteFuel(@PathVariable UUID uuid) {
        fuelService.deleteFuel(uuid);
        return ResponseEntity.ok().build();
    }
}
